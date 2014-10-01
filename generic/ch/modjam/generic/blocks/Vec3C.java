package ch.modjam.generic.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

/**
 * 
 * a point represented by Cartesian coordinates
 * 
 * @author j
 *
 */
public class Vec3C extends Vec3 {

	public Vec3C(double x, double y, double z) {
		super(x, y, z);
	}

	public Vec3C(Vec3 vec) {
		super(vec.xCoord, vec.yCoord, vec.zCoord);
	}

	public Vec3C(Vector4f vec) {
		super(vec.x, vec.y, vec.z);
	}

	@Override
	public Vec3C subtract(Vec3 vec) {
		return new Vec3C(vec.xCoord - this.xCoord, vec.yCoord - this.yCoord,
			vec.zCoord - this.zCoord);
	}

	/**
	 * @return the block where this point is located in
	 */
	public BlockCoordinates getBlockCoords() {
		return new BlockCoordinates(MathHelper.floor_double(this.xCoord), MathHelper
			.floor_double(this.yCoord), MathHelper.floor_double(this.zCoord));
	}

	public TileEntity getTileEntityThere(World w) {
		return w.getTileEntity(MathHelper.floor_double(this.xCoord), MathHelper
			.floor_double(this.yCoord), MathHelper.floor_double(this.zCoord));
	}

	public boolean isNaN() {
		return Double.isNaN(this.xCoord) || Double.isNaN(this.yCoord) || Double.isNaN(this.zCoord);
	}

	public Vector3f toVector3f() {
		return new Vector3f((float) this.xCoord, (float) this.yCoord, (float) this.zCoord);
	}

	public Vec3Polar toPolarCoordinates() {
		double angleYaw = Math.atan2(this.xCoord, this.zCoord);
		double anglePitch = Math.atan2(this.yCoord, Math.hypot(this.xCoord, this.zCoord));
		double radius = Math
			.sqrt(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
		return new Vec3Polar(angleYaw, anglePitch, radius);
	}
}
