package ch.modjam.generic.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Vec3P extends Vec3 {

	public Vec3P(double x, double y, double z) {
		super(x, y, z);
	}

	public Vec3P(Vec3 vec) {
		super(vec.xCoord, vec.yCoord, vec.zCoord);
	}

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
}
