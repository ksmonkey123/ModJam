package ch.judos.at.station;

import net.minecraft.client.gui.inventory.GuiContainer;
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
import ch.judos.at.ATMain;
import ch.judos.at.lib.ATNames;
import ch.judos.at.station.entity.EntityGondola;
import ch.judos.at.station.gui.ContainerStation;
import ch.judos.at.station.gui.GuiContainerStation;
import ch.modjam.generic.gui.IHasGui;
import ch.modjam.generic.inventory.GenericInventory;
import ch.modjam.generic.inventory.GenericTileEntityWithInventory;

public class TEStation extends GenericTileEntityWithInventory implements IHasGui {

	public static final String	nbtConnectedToCoords		= "connectedToCoords";		// int array
																						// with
																						// x,y,z
	public static final String	nbtBuildConnectPlayerName	= "buildConnectPlayerName";

	public static final String	netcmdClientRequestBindRope	= "requestBindRope";

	public EntityPlayer			buildConnectTo;
	private int[]				connectedTo;											// block
	private int					counter;

	// coordinates

	public TEStation() {
		super(new GenericInventory(2, ATNames.station));
		this.inventory.addWhiteListFilter(0, ATMain.gondola);
		this.buildConnectTo = null;
		this.connectedTo = null;
		this.counter = 0;
	}

	@Override
	public void tick() {
		if (this.worldObj.isRemote)
			return;

		this.counter++;
		if (this.counter >= 200) {
			this.counter = 0;

			ItemStack gondolas = this.inventory.getStackInSlot(0);
			ItemStack goods = this.inventory.getStackInSlot(1);

			if (gondolas != null && gondolas.stackSize > 0 && goods != null) {

				TEStation target = this.getTarget();
				if (target != null) {
					// ATMain.logger.error("sending items: " + this.inventory.decrStackSize(1, 1));
					ATMain.logger.error("sending items: " + this.worldObj.getTotalWorldTime());
					Vec3 start = Vec3.createVectorHelper(this.xCoord + 0.5, this.yCoord + 0.5,
						this.zCoord + 0.5);
					Vec3 end = Vec3.createVectorHelper(target.xCoord + 0.5, target.yCoord + 0.5,
						target.zCoord + 0.5);
					EntityGondola eGondola = new EntityGondola(this.worldObj, start, end);
					eGondola.setPosition(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5);
					this.worldObj.spawnEntityInWorld(eGondola);
				}
			}
		}
	}

	public TEStation getTarget() {
		if (this.connectedTo == null)
			return null;
		TileEntity e = this.worldObj.getTileEntity(this.connectedTo[0], this.connectedTo[1],
			this.connectedTo[2]);
		if (e instanceof TEStation)
			return (TEStation) e;
		return null;
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		this.inventory.writeNBT(tag);
		if (this.connectedTo != null)
			tag.setIntArray(nbtConnectedToCoords, this.connectedTo);
		if (this.buildConnectTo != null)
			tag.setString(nbtBuildConnectPlayerName, this.buildConnectTo.getCommandSenderName());
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		this.inventory.readNBT(tag);
		if (tag.hasKey(nbtConnectedToCoords))
			this.connectedTo = tag.getIntArray(nbtConnectedToCoords);
		if (tag.hasKey(nbtBuildConnectPlayerName) && this.worldObj != null) {
			String name = tag.getString(nbtBuildConnectPlayerName);
			ATMain.logger.error("loading: " + this.worldObj);
			this.buildConnectTo = this.worldObj.getPlayerEntityByName(name);
		}
	}

	public static String getTextureName() {
		return ATMain.MOD_ID + ":textures/blocks/" + ATNames.station + ".png";
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		Vec3 other = getConnectedEndCoordinatesOrSelf();
		int lx = MathHelper.floor_double(Math.min(this.xCoord, other.xCoord));
		int ly = MathHelper.floor_double(Math.min(this.yCoord, other.yCoord));
		int lz = MathHelper.floor_double(Math.min(this.zCoord, other.zCoord));
		int hx = MathHelper.ceiling_double_int(Math.max(this.xCoord, other.xCoord)) + 1;
		int hy = MathHelper.ceiling_double_int(Math.max(this.yCoord, other.yCoord)) + 1;
		int hz = MathHelper.ceiling_double_int(Math.max(this.zCoord, other.zCoord)) + 1;
		return AxisAlignedBB.getBoundingBox(lx, ly, lz, hx, hy, hz);
	}

	public void bindRopeConnection(EntityPlayer player) {
		int currentSlotHeld = player.inventory.currentItem;
		ItemStack rope = new ItemStack(ATMain.ropeOfStation);
		ATMain.ropeOfStation.onCreated(rope, this);
		player.inventory.setInventorySlotContents(currentSlotHeld, rope);

		ATMain.logger.error("bindRopeConnecction");
		this.buildConnectTo = player;
		// if (this.worldObj.isRemote)
		player.addChatMessage(new ChatComponentText("Connected rope to player " + player
			.getCommandSenderName()));
	}

	public void finishRopeConnection(TEStation otherStation, EntityPlayer player) {
		this.buildConnectTo = null;
		this.connectedTo = new int[] { otherStation.xCoord, otherStation.yCoord, otherStation.zCoord };
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
		if (this.connectedTo != null)
			return Vec3.createVectorHelper(this.connectedTo[0] + 0.5, this.connectedTo[1] + 0.9,
				this.connectedTo[2] + 0.5);
		return Vec3.createVectorHelper(this.xCoord, this.yCoord, this.zCoord);
	}

	public boolean isConnectedToSomething() {
		return this.buildConnectTo != null || this.connectedTo != null;
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
		this.sendNetworkCommand(netcmdClientRequestBindRope, player.getCommandSenderName()
			.getBytes());
	}

	@Override
	public void onNetworkCommand(String command, byte[] data) {
		if (netcmdClientRequestBindRope.equals(command)) {
			String playerName = new String(data);
			EntityPlayer player = this.worldObj.getPlayerEntityByName(playerName);
			this.bindRopeConnection(player);
		}
	}

}
