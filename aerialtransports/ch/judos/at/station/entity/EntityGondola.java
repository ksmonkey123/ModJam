package ch.judos.at.station.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import ch.modjam.generic.helper.NBTHelper;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityGondola extends Entity implements IEntityAdditionalSpawnData {

	public static final double	TRAVEL_SPEED	= 0.03;

	public Vec3					end;
	public Vec3					start;

	public ItemStack			transportGoods;

	public EntityGondola(World world) {
		super(world);
		this.setSize(1f, 1f);
		this.noClip = true;
		this.setAir(1);
		this.isAirBorne = true;
		this.setVelocity(0.1, 0, 0);

	}

	public EntityGondola(World world, Vec3 start, Vec3 end, ItemStack transportGoods) {
		this(world);
		this.start = start;
		this.end = end;
		this.transportGoods = transportGoods;

		initializeGondola();
	}

	private void initializeGondola() {
		if (this.start == null || this.end == null)
			return;
		Vec3 direction = this.start.subtract(this.end);
		direction = direction.normalize();
		this.motionX = TRAVEL_SPEED * direction.xCoord;
		this.motionY = TRAVEL_SPEED * direction.yCoord;
		this.motionZ = TRAVEL_SPEED * direction.zCoord;

		this.rotationYaw = (float) Math.atan2(direction.xCoord, direction.zCoord);
	}

	@Override
	public void setPositionAndRotation2(double x, double y, double z, float xz, float ay,
			int unknown) {
		// prevents flickering because too many updates from server are received
		Vec3 current = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		Vec3 target = Vec3.createVectorHelper(x, y, z);
		if (current.subtract(target).lengthVector() < 0.1)
			return;
		this.setPosition(x, y, z);
		this.setRotation(xz, ay);
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean handleWaterMovement() {
		return false;
	}

	@Override
	public boolean handleLavaMovement() {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	public boolean isEntityInvulnerable() {
		return true;
	}

	@Override
	public boolean isEntityInsideOpaqueBlock() {
		return super.isEntityInsideOpaqueBlock();
		// return false;
	}

	@Override
	public boolean doesEntityNotTriggerPressurePlate() {
		return true;
	}

	@Override
	protected void entityInit() {
		// do nothing
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer player) {
		// ATMain.logger.error("collision " + player);
	}

	@Override
	public void onEntityUpdate() {

		Vec3 before = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;

		this.boundingBox.setBounds(this.posX - 0.5, this.posY - 0.5, this.posZ - 0.5,
			this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5);

		if (this.worldObj.isRemote) {
			// this.serverPosX = (int) this.posX;
			// this.serverPosY = (int) this.posY;
			// this.serverPosZ = (int) this.posZ;
		}

		if (this.end == null)
			return;
		Vec3 current = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		// if distance to target station is bigger than before, entity has reached station
		if (current.subtract(this.end).lengthVector() > before.subtract(this.end).lengthVector())
			this.setDead();
	}

	@Override
	public boolean isPushedByWater() {
		return false;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		this.start = NBTHelper.readVecFromNBT(tag, "start");
		this.end = NBTHelper.readVecFromNBT(tag, "end");
		this.transportGoods = NBTHelper.readItemStackFromNBT(tag, "transportedGoods");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		NBTHelper.writeVecToNBT(tag, this.start, "start");
		NBTHelper.writeVecToNBT(tag, this.end, "end");
		NBTHelper.writeItemStackToNBT(this.transportGoods, tag, "transportedGoods");
	}

	public void setStartAndTarget(double xs, double ys, double zs, double xe, double ye, double ze) {
		this.start = Vec3.createVectorHelper(xs, ys, zs);
		this.end = Vec3.createVectorHelper(xe, ye, ze);
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		NBTTagCompound data = new NBTTagCompound();
		if (this.start != null)
			NBTHelper.writeVecToNBT(data, this.start, "start");
		else
			new RuntimeException("couldn't write start : " + this.start).printStackTrace();
		if (this.end != null)
			NBTHelper.writeVecToNBT(data, this.end, "end");
		else
			new RuntimeException("couldn't write end : " + this.start).printStackTrace();
		ByteBufUtils.writeTag(buffer, data);
		ByteBufUtils.writeItemStack(buffer, this.transportGoods);
	}

	@Override
	public void readSpawnData(ByteBuf buffer) {
		NBTTagCompound data = ByteBufUtils.readTag(buffer);
		this.start = NBTHelper.readVecFromNBT(data, "start");
		this.end = NBTHelper.readVecFromNBT(data, "end");
		this.transportGoods = ByteBufUtils.readItemStack(buffer);

		initializeGondola();
	}

}
