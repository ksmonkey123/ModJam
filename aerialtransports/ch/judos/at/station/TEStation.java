package ch.judos.at.station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import org.apache.commons.lang3.ArrayUtils;

import ch.judos.at.ATMain;
import ch.judos.at.gondola.EntityGondola;
import ch.judos.at.lib.ATNames;
import ch.judos.at.station.gui.ContainerStation;
import ch.judos.at.station.gui.GuiContainerStation;
import ch.modjam.generic.blocks.BlockCoordinates;
import ch.modjam.generic.blocks.Vec3C;
import ch.modjam.generic.gui.IHasGui;
import ch.modjam.generic.helper.ItemUtils;
import ch.modjam.generic.helper.NBTUtils;
import ch.modjam.generic.inventory.GenericInventory;
import ch.modjam.generic.inventory.GenericTileEntityWithInventory;

public class TEStation extends GenericTileEntityWithInventory implements IHasGui {

	/**
	 * int array with x,y,z
	 */
	public static final String			nbtConnectedToCoords		= "connectedToCoords";
	public static final String			nbtBuildConnectPlayerName	= "buildConnectPlayerName";
	public static final String			nbtIsSender					= "isSender";
	public static final String			nbtGondolaArr				= "gondolaIdArr";
	public static final String			nbtCollisionBlocks			= "collisionBlocks";

	public static final String			netClientReqBindRope		= "requestBindRope";
	private static final String			netClientReqChangeSender	= "requestSenderChange";

	/**
	 * player currently wearing a rope to connect this station with another station
	 */
	public EntityPlayer					buildConnectTo;

	protected String					buildConnectToName;

	/**
	 * block coordinates of receiver/sender station<br>
	 * is null if not connected
	 */
	private int[]						connectedToCoords;
	private boolean						isSender;
	private int							counter;
	public HashSet<Integer>				gondolaIdsSent;
	private ArrayList<BlockCoordinates>	collisionBlocks;
	public Set<BlockCoordinates>		blockingBlocks;
	public int							showBlockingBlocksTimer;

	// coordinates

	public TEStation() {
		super(new GenericInventory(2, ATNames.station));
		this.inventory.addWhiteListFilter(0, ATMain.gondola);
		this.buildConnectTo = null;
		this.connectedToCoords = null;
		this.counter = 0;
		this.gondolaIdsSent = new HashSet<Integer>();
	}

	private void cleanSentGondolaIdSet() {
		Iterator<Integer> it = this.gondolaIdsSent.iterator();
		while (it.hasNext()) {
			int id = it.next();
			if (this.worldObj.getEntityByID(id) == null)
				it.remove();
		}
	}

	@Override
	public void tick() {
		if (this.buildConnectToName != null) {
			this.buildConnectTo = this.worldObj.getPlayerEntityByName(this.buildConnectToName);
			if (this.buildConnectTo != null)
				this.buildConnectToName = null;
		}

		if (this.worldObj.isRemote)
			return;
		if (!this.isSender)
			return;

		this.counter++;
		if (this.counter % 10000 == 0)
			cleanSentGondolaIdSet();

		if (this.counter % 100 == 0) {
			if (canGondolaBeSent())
				sendGondola();
		}
	}

	private void sendGondola() {
		TEStation target = this.getTarget();

		// capacity of gondola: 5 items
		ItemStack transportGoods = this.inventory.decrStackSize(1, 5);
		// remove one gondola:
		this.inventory.decrStackSize(0, 1);

		Vec3 start = Vec3.createVectorHelper(this.xCoord + 0.5, this.yCoord + 0.5,
			this.zCoord + 0.5);
		Vec3C end = new Vec3C(target.xCoord + 0.5, target.yCoord + 0.5, target.zCoord + 0.5);
		EntityGondola eGondola = new EntityGondola(this.worldObj, start, end, transportGoods);
		eGondola.setPosition(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5);
		this.worldObj.spawnEntityInWorld(eGondola);

		this.gondolaIdsSent.add(eGondola.getEntityId());
	}

	private boolean canGondolaBeSent() {
		ItemStack gondolas = this.inventory.getStackInSlot(0);
		ItemStack goods = this.inventory.getStackInSlot(1);

		if (gondolas != null && gondolas.stackSize > 0 && goods != null && goods.stackSize > 0) {
			TEStation target = this.getTarget();
			return target != null;
		}

		return false;
	}

	public TEStation getTarget() {
		if (this.connectedToCoords == null)
			return null;
		TileEntity e = this.worldObj.getTileEntity(this.connectedToCoords[0],
			this.connectedToCoords[1], this.connectedToCoords[2]);
		if (e instanceof TEStation)
			return (TEStation) e;
		return null;
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		this.inventory.writeNBT(tag);
		if (this.connectedToCoords != null)
			tag.setIntArray(nbtConnectedToCoords, this.connectedToCoords);
		if (this.buildConnectTo != null)
			tag.setString(nbtBuildConnectPlayerName, this.buildConnectTo.getCommandSenderName());
		else if (this.buildConnectToName != null)
			tag.setString(nbtBuildConnectPlayerName, this.buildConnectToName);

		tag.setBoolean(nbtIsSender, this.isSender);

		int[] gondolaIdArr = ArrayUtils.toPrimitive(this.gondolaIdsSent.toArray(new Integer[] {}));
		tag.setIntArray(nbtGondolaArr, gondolaIdArr);

		if (this.collisionBlocks != null)
			NBTUtils.writeListToNBT(tag, this.collisionBlocks, nbtCollisionBlocks);
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		this.inventory.readNBT(tag);
		if (tag.hasKey(nbtConnectedToCoords))
			this.connectedToCoords = tag.getIntArray(nbtConnectedToCoords);
		else
			this.connectedToCoords = null;
		if (tag.hasKey(nbtBuildConnectPlayerName) && this.worldObj != null) {
			String name = tag.getString(nbtBuildConnectPlayerName);
			this.buildConnectTo = this.worldObj.getPlayerEntityByName(name);
		} else if (tag.hasKey(nbtBuildConnectPlayerName)) {
			this.buildConnectToName = tag.getString(nbtBuildConnectPlayerName);
		} else {
			this.buildConnectTo = null;
		}

		if (tag.hasKey(nbtIsSender))
			this.isSender = tag.getBoolean(nbtIsSender);

		int[] gondolaIdArr = tag.getIntArray(nbtGondolaArr);
		this.gondolaIdsSent = new HashSet<Integer>(Arrays.asList(ArrayUtils.toObject(gondolaIdArr)));

		if (tag.hasKey(nbtCollisionBlocks))
			this.collisionBlocks = NBTUtils.readListFromNBT(tag, nbtCollisionBlocks,
				BlockCoordinates.class);
		else
			this.collisionBlocks = new ArrayList<BlockCoordinates>();
	}

	public static String getTextureName() {
		return ATMain.MOD_ID + ":textures/blocks/" + ATNames.station + ".png";
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if (!this.isConnectedToSomethingAndSender())
			return getStandardRenderBB();
		Vec3 other = getConnectedEndCoordinatesOrSelf();
		int lx = MathHelper.floor_double(Math.min(this.xCoord, other.xCoord));
		int ly = MathHelper.floor_double(Math.min(this.yCoord, other.yCoord));
		int lz = MathHelper.floor_double(Math.min(this.zCoord, other.zCoord));
		int hx = MathHelper.ceiling_double_int(Math.max(this.xCoord, other.xCoord)) + 1;
		int hy = MathHelper.ceiling_double_int(Math.max(this.yCoord, other.yCoord)) + 1;
		int hz = MathHelper.ceiling_double_int(Math.max(this.zCoord, other.zCoord)) + 1;
		return AxisAlignedBB.getBoundingBox(lx, ly, lz, hx, hy, hz);
	}

	private AxisAlignedBB getStandardRenderBB() {
		return AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1,
			this.yCoord + 1, this.zCoord + 1);
	}

	public void bindRopeConnection(EntityPlayer player) {
		int currentSlotHeld = player.inventory.currentItem;
		ItemStack rope = new ItemStack(ATMain.ropeOfStation);
		ATMain.ropeOfStation.onCreated(rope, this);
		player.inventory.setInventorySlotContents(currentSlotHeld, rope);

		this.disconnectStation();
		this.buildConnectTo = player;
		this.forceServerPush();

		// if (this.worldObj.isRemote)
		player.addChatMessage(new ChatComponentText("Connected rope to player " + player
			.getCommandSenderName()));
	}

	public void disconnectOtherStation() {
		TEStation other = getTarget();
		if (other == null)
			return;

		other.connectedToCoords = null;
		other.letAllGondolasFallOutOfRope();
		if (other.collisionBlocks != null)
			for (BlockCoordinates b : other.collisionBlocks)
				this.worldObj.setBlockToAir(b.x, b.y, b.z);
		other.collisionBlocks = new ArrayList<BlockCoordinates>();
		other.forceServerPush();
	}

	public void disconnectStation() {
		disconnectOtherStation();
		this.buildConnectTo = null;
		this.connectedToCoords = null;
		if (this.collisionBlocks != null)
			for (BlockCoordinates b : this.collisionBlocks)
				this.worldObj.setBlockToAir(b.x, b.y, b.z);
		this.collisionBlocks = new ArrayList<BlockCoordinates>();

		letAllGondolasFallOutOfRope();
	}

	private void letAllGondolasFallOutOfRope() {
		HashSet<Integer> x = new HashSet<Integer>(this.gondolaIdsSent);
		for (int entityId : x) {
			Entity entity = this.worldObj.getEntityByID(entityId);
			if (entity instanceof EntityGondola) {
				EntityGondola gondola = (EntityGondola) entity;
				gondola.gondolaFallsOutOfRope();
			}
		}
	}

	public void finishRopeConnection(TEStation otherStation, EntityPlayer player,
			ArrayList<BlockCoordinates> newCollisionBlocks) {
		disconnectStation();

		this.blockingBlocks = null;
		this.showBlockingBlocksTimer = 0;

		this.collisionBlocks = newCollisionBlocks;
		this.connectedToCoords = new int[] { otherStation.xCoord, otherStation.yCoord, otherStation.zCoord };
		int[] stationCoords = new int[] { this.xCoord, this.yCoord, this.zCoord };
		this.isSender = true;
		this.forceServerPush();

		otherStation.disconnectOtherStation();
		otherStation.blockingBlocks = null;
		otherStation.showBlockingBlocksTimer = 0;

		otherStation.buildConnectTo = null;
		otherStation.connectedToCoords = stationCoords;
		otherStation.isSender = false;
		otherStation.forceServerPush();

		if (this.worldObj.isRemote)
			player.addChatMessage(new ChatComponentText("Connected rope to station."));
	}

	public Vec3 getConnectedEndCoordinatesOrSelf() {
		return getConnectedEndCoordinatesOrSelf(1);
	}

	public Vec3 getConnectedEndCoordinatesOrSelf(float partialTickTime) {
		if (this.buildConnectTo != null) {
			Vec3 pos = this.buildConnectTo.getPosition(partialTickTime);
			pos.yCoord -= 1;
			return pos;
		}
		if (this.connectedToCoords != null)
			return Vec3.createVectorHelper(this.connectedToCoords[0] + 0.5,
				this.connectedToCoords[1] + 0.9, this.connectedToCoords[2] + 0.5);
		return Vec3.createVectorHelper(this.xCoord, this.yCoord, this.zCoord);
	}

	/**
	 * @return true if it is connected to player or if it is a sender station connected to some
	 *         other station
	 */
	public boolean isConnectedToSomethingAndSender() {
		return this.buildConnectTo != null || (this.connectedToCoords != null && this.isSender);
	}

	/**
	 * @return true if it is connected to player or if it connected to some other station
	 */
	public boolean isConnectedToSomething() {
		return this.buildConnectTo != null || this.connectedToCoords != null;
	}

	@Override
	public GuiContainer getGuiClient(InventoryPlayer playerInventory) {
		return new GuiContainerStation(playerInventory, this);
	}

	@Override
	public Container getGuiServer(InventoryPlayer playerInventory) {
		return new ContainerStation(playerInventory, this);
	}

	public void clientRequestBindRopeConnection(EntityPlayer player) {
		this.sendNetworkCommand(netClientReqBindRope, player.getCommandSenderName().getBytes());
	}

	@Override
	public void onNetworkCommand(String command, byte[] data) {
		if (netClientReqBindRope.equals(command)) {
			String playerName = new String(data);
			EntityPlayer player = this.worldObj.getPlayerEntityByName(playerName);
			this.bindRopeConnection(player);
		}
		if (netClientReqChangeSender.equals(command)) {
			TEStation te = getTarget();
			if (te != null) {
				this.isSender = !this.isSender;
				te.isSender = !te.isSender;
				te.forceServerPush();
			}
		}
		super.onNetworkCommand(command, data);
	}

	public boolean isSender() {
		return this.isSender;
	}

	public void clientRequestSenderChange() {
		this.sendNetworkCommand(netClientReqChangeSender);
	}

	public void receiveGondola(EntityGondola entityGondola) {
		if (!this.inventory.addItemStackToInventory(entityGondola.transportGoods))
			ItemUtils.spawnItemEntityAbove(this, entityGondola.transportGoods);
		if (!this.inventory.addItemStackToInventory(new ItemStack(ATMain.gondola)))
			ItemUtils.spawnItemEntityAbove(this, new ItemStack(ATMain.gondola));
	}

	/**
	 * sets the blocks that are currently in the way and prevent a connection between this station
	 * and some other station
	 * 
	 * @param intermediate
	 */
	public void setBlockingBlocks(Set<BlockCoordinates> intermediate) {
		this.blockingBlocks = intermediate;
		this.showBlockingBlocksTimer = 100000 * 20;
	}

}
