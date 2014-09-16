package ch.judos.at.station.gondola;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import ch.judos.at.ATMain;
import ch.judos.at.station.TEStation;
import ch.modjam.generic.blocks.Vec3P;
import ch.modjam.generic.helper.ItemUtils;
import ch.modjam.generic.helper.NBTUtils;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityGondola extends Entity implements IEntityAdditionalSpawnData {

	public static final double	TRAVEL_SPEED	= 0.03;

	public Vec3P				end;
	public Vec3					start;
	public Vec3					currentPos;

	public ItemStack			transportGoods;

	public EntityGondola(World world) {
		super(world);
		this.setSize(1f, 1f);
		this.noClip = true;
		this.setAir(1);
		this.isAirBorne = true;
		this.setVelocity(0.1, 0, 0);
		this.currentPos = Vec3.createVectorHelper(0, 0, 0);

	}

	public EntityGondola(World world, Vec3 start, Vec3P end, ItemStack transportGoods) {
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

		this.rotationYaw = (float) -Math.atan2(direction.zCoord, direction.xCoord);
		this.rotationPitch = (float) Math.atan2(direction.yCoord, Math.hypot(direction.xCoord,
			direction.zCoord));

		direction = direction.normalize();
		this.motionX = TRAVEL_SPEED * direction.xCoord;
		this.motionY = TRAVEL_SPEED * direction.yCoord;
		this.motionZ = TRAVEL_SPEED * direction.zCoord;
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
		// XXX: mc1.8 fixed the coordinate offset, also Vec3 is immutable
		Vec3 ppos = player.getPosition(1);
		if (!this.worldObj.isRemote)
			ppos.yCoord += 1.62;

		double dist = this.currentPos.subtract(ppos).lengthVector();
		if (dist < 0.7)
			gondolaFallsOutOfRope();
	}

	/**
	 * drops items and itself as EntityItem on the floor
	 */
	public void gondolaFallsOutOfRope() {
		if (!this.worldObj.isRemote) {
			this.disposeGondola();
			ItemUtils.spawnItemEntity(this, this.transportGoods);
			ItemUtils.spawnItemEntity(this, new ItemStack(ATMain.gondola));
		}
	}

	private void disposeGondola() {
		this.removeFromListInStation();
		this.worldObj.removeEntity(this);
		this.setDead();
	}

	private void deliverItemsToTargetStation() {
		TileEntity targetTE = this.end.getTileEntityThere(this.worldObj);
		if (targetTE instanceof TEStation) {
			TEStation targetStation = (TEStation) targetTE;
			targetStation.receiveGondola(this);
			this.disposeGondola();

		} else
			this.gondolaFallsOutOfRope();
	}

	@Override
	public void onEntityUpdate() {

		Vec3 before = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		this.currentPos.xCoord = this.posX;
		this.currentPos.yCoord = this.posY;
		this.currentPos.zCoord = this.posZ;

		double r = 0.5;
		this.boundingBox.setBounds(this.posX - r, this.posY - r, this.posZ - r, this.posX + r,
			this.posY + r, this.posZ + r);

		if (this.end == null)
			return;
		// if distance to target station is bigger than before, entity has reached station
		if (this.currentPos.subtract(this.end).lengthVector() > before.subtract(this.end)
			.lengthVector()) {
			this.deliverItemsToTargetStation();
			this.removeFromListInStation();
			this.setDead();
		}
	}

	private void removeFromListInStation() {
		TileEntity startEntity = this.worldObj.getTileEntity(MathHelper
			.floor_double(this.start.xCoord), MathHelper.floor_double(this.start.yCoord),
			MathHelper.floor_double(this.start.zCoord));
		if (startEntity instanceof TEStation) {
			TEStation startStation = (TEStation) startEntity;
			startStation.gondolaIdsSent.remove(this.getEntityId());
		}
	}

	@Override
	public boolean isPushedByWater() {
		return false;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		this.start = NBTUtils.readVecFromNBT(tag, "start");
		this.end = NBTUtils.readVecFromNBT(tag, "end");
		this.transportGoods = NBTUtils.readItemStackFromNBT(tag, "transportedGoods");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		NBTUtils.writeVecToNBT(tag, this.start, "start");
		NBTUtils.writeVecToNBT(tag, this.end, "end");
		NBTUtils.writeItemStackToNBT(this.transportGoods, tag, "transportedGoods");
	}

	public void setStartAndTarget(double xs, double ys, double zs, double xe, double ye, double ze) {
		this.start = Vec3.createVectorHelper(xs, ys, zs);
		this.end = new Vec3P(xe, ye, ze);
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		NBTTagCompound data = new NBTTagCompound();
		if (this.start != null)
			NBTUtils.writeVecToNBT(data, this.start, "start");
		else
			new RuntimeException("couldn't write start : " + this.start).printStackTrace();
		if (this.end != null)
			NBTUtils.writeVecToNBT(data, this.end, "end");
		else
			new RuntimeException("couldn't write end : " + this.start).printStackTrace();
		ByteBufUtils.writeTag(buffer, data);
		ByteBufUtils.writeItemStack(buffer, this.transportGoods);
	}

	@Override
	public void readSpawnData(ByteBuf buffer) {
		NBTTagCompound data = ByteBufUtils.readTag(buffer);
		this.start = NBTUtils.readVecFromNBT(data, "start");
		this.end = NBTUtils.readVecFromNBT(data, "end");
		this.transportGoods = ByteBufUtils.readItemStack(buffer);

		initializeGondola();
	}

}
