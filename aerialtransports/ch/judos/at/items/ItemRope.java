package ch.judos.at.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import ch.judos.at.ATMain;
import ch.judos.at.entity.EntityRope;
import ch.judos.at.lib.ATNames;
import ch.judos.at.te.TEStation;

public class ItemRope extends Item {

	public static final String	nbtStationCoordinates	= "station_coordinates";

	public ItemRope() {
		super();

		this.setUnlocalizedName(ATNames.ropeToStation);
		this.setTextureName(ATMain.MOD_ID + ":" + ATNames.ropeToStation);
		this.setCreativeTab(ATMain.modTab);
		this.setMaxStackSize(1);
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}

	@Override
	public Entity createEntity(World world, Entity ent, ItemStack itemstack) {
		ent.motionX = 0;
		ent.motionY = 0;
		ent.motionZ = 0;
		return new EntityRope(world, ent, itemstack);
	}

	@Override
	public int getEntityLifespan(ItemStack itemStack, World world) {
		// TODO Auto-generated method stub
		return super.getEntityLifespan(itemStack, world);
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List infoList, boolean unknown1) {
		StringBuffer s = new StringBuffer("Connected to station: ");
		if (item.stackTagCompound != null) {
			int[] coords = item.stackTagCompound.getIntArray(nbtStationCoordinates);
			for (int i = 0; i < 3; i++)
				s.append(coords[i] + "/");
			infoList.add(s.substring(0, s.length() - 1));
		} else
			infoList.add("invalid item");
	}

	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int slot, boolean heldInHand) {
		if (!heldInHand) {
			if (entity instanceof EntityPlayer) {
				EntityPlayer e2 = (EntityPlayer) entity;
				e2.inventory.setInventorySlotContents(slot, null);
				if (!world.isRemote) {
					EntityRope spawnIn = new EntityRope(world, entity, item);
					world.spawnEntityInWorld(spawnIn);
				}
			}
		}
		//

		// TODO:
	}

	public void onCreated(ItemStack rope, TEStation te2) {
		rope.stackTagCompound = new NBTTagCompound();
		rope.stackTagCompound.setIntArray(nbtStationCoordinates,
			new int[] { te2.xCoord, te2.yCoord, te2.zCoord });
	}

}
