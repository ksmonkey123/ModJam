package ch.modjam.generic.helper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemUtils {
	public static void spawnItemEntity(Entity origin, ItemStack stack) {
		spawnItemEntity(origin.worldObj, origin.posX, origin.posY, origin.posZ, stack);
	}

	public static void spawnItemEntity(TileEntity origin, ItemStack stack) {
		spawnItemEntity(origin.getWorld(), origin.xCoord + 0.5, origin.yCoord + 0.5,
			origin.zCoord + 0.5, stack);
	}

	public static void spawnItemEntityAbove(TileEntity origin, ItemStack stack) {
		spawnItemEntity(origin.getWorld(), origin.xCoord + 0.5, origin.yCoord + 1.5,
			origin.zCoord + 0.5, stack);
	}

	public static void spawnItemEntity(World world, double x, double y, double z, ItemStack stack) {
		if (world.isRemote)
			return;
		EntityItem items = new EntityItem(world, x, y, z, stack);
		items.delayBeforeCanPickup = 15;
		items.motionX = 0;
		items.motionY = 0.2f;
		items.motionZ = 0;
		world.spawnEntityInWorld(items);
	}
}
