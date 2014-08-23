package ch.judos.at.station.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import ch.judos.at.ATMain;
import ch.modjam.generic.Vec3Helper;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityGondola extends Entity implements IEntityAdditionalSpawnData {

	public static final double	TRAVEL_SPEED	= 0.03;

	private double				travelDistance;
	private Vec3				end;
	private Vec3				start;

	public EntityGondola(World world) {
		super(world);
		this.setSize(0.1f, 0.1f);
		this.travelDistance = 0;
		this.noClip = true;
		this.setAir(1);
		this.isAirBorne = true;
		this.setVelocity(0.1, 0, 0);

	}

	public EntityGondola(World world, Vec3 start, Vec3 end) {
		this(world);
		this.start = start;
		this.end = end;

		Vec3 direction = this.start.subtract(this.end);
		direction = direction.normalize();
		this.motionX = TRAVEL_SPEED * direction.xCoord;
		this.motionY = TRAVEL_SPEED * direction.yCoord;
		this.motionZ = TRAVEL_SPEED * direction.zCoord;
	}

	@Override
	public void setPositionAndRotation2(double x, double y, double z, float xz, float ay,
			int unknown) {
		if (this.start != null)
			return;
		this.setPosition(x, y, z);
		this.setRotation(xz, ay);

		System.out.println("set Position and rot " + this.getEntityId());
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
		return false;
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

		this.travelDistance += TRAVEL_SPEED;

		if (this.end == null)
			return;

		Vec3 before = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;

		Vec3 current = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
		if (current.subtract(this.end).lengthVector() > before.subtract(this.end).lengthVector())
			this.setDead();
	}

	@Override
	public boolean isPushedByWater() {
		return false;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		this.start = Vec3Helper.readVecFromNBT(tag, "start");
		this.end = Vec3Helper.readVecFromNBT(tag, "end");
		ATMain.logger.error("read: " + this.end);
		this.travelDistance = tag.getDouble("travelDistance");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		ATMain.logger.error("write: " + this.end);
		Vec3Helper.writeVecToNBT(tag, this.start, "start");
		Vec3Helper.writeVecToNBT(tag, this.end, "end");
		tag.setDouble("travelDistance", this.travelDistance);
	}

	public void setStartAndTarget(double xs, double ys, double zs, double xe, double ye, double ze) {
		this.start = Vec3.createVectorHelper(xs, ys, zs);
		this.end = Vec3.createVectorHelper(xe, ye, ze);
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		NBTTagCompound data = new NBTTagCompound();
		Vec3Helper.writeVecToNBT(data, this.start, "start");
		Vec3Helper.writeVecToNBT(data, this.end, "end");
		ByteBufUtils.writeTag(buffer, data);
	}

	@Override
	public void readSpawnData(ByteBuf buffer) {
		NBTTagCompound data = ByteBufUtils.readTag(buffer);
		this.start = Vec3Helper.readVecFromNBT(data, "start");
		this.end = Vec3Helper.readVecFromNBT(data, "end");

		Vec3 direction = this.start.subtract(this.end);
		direction = direction.normalize();
		this.motionX = TRAVEL_SPEED * direction.xCoord;
		this.motionY = TRAVEL_SPEED * direction.yCoord;
		this.motionZ = TRAVEL_SPEED * direction.zCoord;
	}

}
