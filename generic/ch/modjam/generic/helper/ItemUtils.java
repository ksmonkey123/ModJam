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
		spawnItemEntity(origin.getWorldObj(), origin.xCoord, origin.yCoord, origin.zCoord, stack);
	}

	public static void spawnItemEntity(World world, double x, double y, double z, ItemStack stack) {
		EntityItem items = new EntityItem(world, x, y, z, stack);
		items.delayBeforeCanPickup = 10;
		items.motionX *= 0.1;
		items.motionY = 0.2f;
		items.motionZ *= 0.1;
		world.spawnEntityInWorld(items);
	}
}
