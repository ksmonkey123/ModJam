package ch.judos.at.station.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import ch.judos.at.ATMain;
import ch.modjam.generic.Vec3Helper;

public class EntityGondola extends Entity {

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

	@Override
	public void setPositionAndRotation2(double x, double y, double z, float xz, float ay,
			int unknown) {
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
		// System.out.println(this.posX + "," + this.posY + "," + this.posZ);
		// super.onEntityUpdate();

		this.travelDistance += TRAVEL_SPEED;

		if (this.end == null)
			return;
		Vec3 direction = this.start.subtract(this.end);
		direction = direction.normalize();

		this.posX = this.start.xCoord + direction.xCoord * this.travelDistance;
		this.posY = this.start.yCoord + direction.yCoord * this.travelDistance;
		this.posZ = this.start.zCoord + direction.zCoord * this.travelDistance;

		if (this.travelDistance > 10) {
			this.setDead();
		}
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

}
