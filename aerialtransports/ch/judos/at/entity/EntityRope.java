package ch.judos.at.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityRope extends EntityItem {

	public EntityRope(World world, Entity location, ItemStack itemstack) {
		super(world);
		this.lifespan = 6000;
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;

		this.lastTickPosX = location.posX;
		this.lastTickPosY = location.posY;
		this.lastTickPosZ = location.posZ;
		this.prevPosX = location.posX;
		this.prevPosY = location.posY;
		this.prevPosZ = location.posZ;

		this.delayBeforeCanPickup = 6000;

		this.setPosition(location.posX, location.posY, location.posZ);
		// this.rotationYaw = (float) (Math.random() * 360.0D);
		this.setEntityItemStack(itemstack);

	}

}
