package ch.modjam.generic.blocks;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Vec3P extends Vec3 {

	public Vec3P(double x, double y, double z) {
		super(x, y, z);
	}

	public BlockCoordinates getBlockCoords() {
		return new BlockCoordinates(MathHelper.floor_double(this.xCoord), MathHelper
			.floor_double(this.yCoord), MathHelper.floor_double(this.zCoord));
	}
}
