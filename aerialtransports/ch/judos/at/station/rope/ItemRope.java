package ch.judos.at.station.rope;

import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import ch.judos.at.ATMain;
import ch.judos.at.lib.ATNames;
import ch.judos.at.station.TEStation;
import ch.modjam.generic.blocks.BlockCoordinates;
import ch.modjam.generic.blocks.Collision;
import ch.modjam.generic.blocks.Vec3P;

public class ItemRope extends Item {

	public static final String	nbtStationCoordinates	= "station_coordinates";
	private IIcon				normal;
	private IIcon				blocked;

	public ItemRope() {
		super();

		this.setUnlocalizedName(ATNames.ropeToStation);
		this.setTextureName(ATMain.MOD_ID + ":" + ATNames.ropeToStation);
		this.setCreativeTab(ATMain.modTab);
		this.setMaxStackSize(1);
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return false;
	}

	@Override
	public Entity createEntity(World world, Entity ent, ItemStack itemstack) {
		// ent.motionX = 0;
		// ent.motionY = 0;
		// ent.motionZ = 0;
		// return new EntityRope(world, ent, itemstack);
		return null;
	}

	@Override
	public int getEntityLifespan(ItemStack itemStack, World world) {
		return 100;
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List infoList, boolean unknown1) {
		StringBuffer s = new StringBuffer("Connected to station: ");
		if (item.stackTagCompound != null) {
			int[] coords = item.stackTagCompound.getIntArray(nbtStationCoordinates);
			for (int i = 0; i < 3; i++)
				s.append(coords[i] + "/");
			infoList.add(s.substring(0, s.length() - 1));
		} else {
			infoList.add("invalid item");
		}
	}

	@Override
	public void registerIcons(IIconRegister register) {
		this.normal = register.registerIcon(ATMain.MOD_ID + ":" + ATNames.ropeToStation);
		this.blocked = register
			.registerIcon(ATMain.MOD_ID + ":" + ATNames.ropeToStation + "_collision");
	}

	@Override
	public IIcon getIconIndex(ItemStack stack) {
		if (stack.stackTagCompound == null)
			return this.normal;
		int[] coords = stack.stackTagCompound.getIntArray(nbtStationCoordinates);
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		World world = player.worldObj;
		Collision c = new Collision(world);

		Vec3 ppos = player.getPosition(1);
		if (!world.isRemote)
			ppos.yCoord += 1.62;
		ppos.yCoord -= 1; // height of arm

		Vec3P start = new Vec3P(coords[0] + 0.5, coords[1] + 0.9, coords[2] + 0.5);
		Vec3P end = new Vec3P(ppos.xCoord, ppos.yCoord, ppos.zCoord);

		// detects liquids and excludes the start (station itself)
		Set<BlockCoordinates> mop = c.detectCollisions(start, end, true, true);

		if (mop.isEmpty())
			return this.normal;
		return this.blocked;
	}

	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int slot, boolean heldInHand) {

		if (true)
			return;
		// XXX: eventually drop item when not in hand
		if (!heldInHand) {
			if (entity instanceof EntityPlayer) {
				ATMain.logger.error(entity + " - " + item);
				EntityPlayer e2 = (EntityPlayer) entity;
				e2.inventory.setInventorySlotContents(slot, null);

				EntityItem e = new EntityItem(world, entity.posX, entity.posY, entity.posZ,
					new ItemStack(ATMain.ropeOfStation));
				e.motionX = 0;
				e.motionY = 0;
				e.motionZ = 0;
				world.spawnEntityInWorld(e);
			}
		}
	}

	public void onCreated(ItemStack rope, TEStation te2) {
		rope.stackTagCompound = new NBTTagCompound();
		rope.stackTagCompound.setIntArray(nbtStationCoordinates,
			new int[] { te2.xCoord, te2.yCoord, te2.zCoord });
	}

}
