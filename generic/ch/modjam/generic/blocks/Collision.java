package ch.modjam.generic.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Collision {

	protected World	world;

	public Collision(World w) {
		this.world = w;
	}

	public MovingObjectPosition detectCollissions(Vec3 startVec, Vec3 endVec,
			boolean collideWithLiquid, boolean p_147447_4_, boolean p_147447_5_) {
		if (Double.isNaN(startVec.xCoord) || Double.isNaN(startVec.yCoord) || Double
			.isNaN(startVec.zCoord))
			return null;

		if (Double.isNaN(endVec.xCoord) || Double.isNaN(endVec.yCoord) || Double
			.isNaN(endVec.zCoord))
			return null;

		int endX = MathHelper.floor_double(endVec.xCoord);
		int endY = MathHelper.floor_double(endVec.yCoord);
		int endZ = MathHelper.floor_double(endVec.zCoord);
		int startX = MathHelper.floor_double(startVec.xCoord);
		int startY = MathHelper.floor_double(startVec.yCoord);
		int startZ = MathHelper.floor_double(startVec.zCoord);
		Block block = this.world.getBlock(startX, startY, startZ);
		int blockMeta = this.world.getBlockMetadata(startX, startY, startZ);

		if ((!p_147447_4_ || block.getCollisionBoundingBoxFromPool(this.world, startX, startY,
			startZ) != null) && block.canCollideCheck(blockMeta, collideWithLiquid)) {
			MovingObjectPosition movingobjectposition = block.collisionRayTrace(this.world, startX,
				startY, startZ, startVec, endVec);

			if (movingobjectposition != null)
				return movingobjectposition;
		}

		MovingObjectPosition movingobjectposition2 = null;
		int blockRemainingForCollision = 200;

		while (blockRemainingForCollision-- >= 0) {

			if (startX == endX && startY == endY && startZ == endZ) {
				return p_147447_5_ ? movingobjectposition2 : null;
			}

			boolean hasXDiff = true;
			boolean hasYDiff = true;
			boolean hasZDiff = true;
			double nextCheckX = 999.0D;
			double nextCheckY = 999.0D;
			double nextCheckZ = 999.0D;

			if (endX > startX) {
				nextCheckX = startX + 1.0D;
			} else if (endX < startX) {
				nextCheckX = startX + 0.0D;
			} else {
				hasXDiff = false;
			}

			if (endY > startY) {
				nextCheckY = startY + 1.0D;
			} else if (endY < startY) {
				nextCheckY = startY + 0.0D;
			} else {
				hasYDiff = false;
			}

			if (endZ > startZ) {
				nextCheckZ = startZ + 1.0D;
			} else if (endZ < startZ) {
				nextCheckZ = startZ + 0.0D;
			} else {
				hasZDiff = false;
			}

			double d3 = 999.0D;
			double d4 = 999.0D;
			double d5 = 999.0D;
			double diffX = endVec.xCoord - startVec.xCoord;
			double diffY = endVec.yCoord - startVec.yCoord;
			double diffZ = endVec.zCoord - startVec.zCoord;

			if (hasXDiff) {
				d3 = (nextCheckX - startVec.xCoord) / diffX;
			}

			if (hasYDiff) {
				d4 = (nextCheckY - startVec.yCoord) / diffY;
			}

			if (hasZDiff) {
				d5 = (nextCheckZ - startVec.zCoord) / diffZ;
			}

			byte sideNr;

			if (d3 < d4 && d3 < d5) {
				if (endX > startX) {
					sideNr = 4;
				} else {
					sideNr = 5;
				}

				startVec.xCoord = nextCheckX;
				startVec.yCoord += diffY * d3;
				startVec.zCoord += diffZ * d3;
			} else if (d4 < d5) {
				if (endY > startY) {
					sideNr = 0;
				} else {
					sideNr = 1;
				}

				startVec.xCoord += diffX * d4;
				startVec.yCoord = nextCheckY;
				startVec.zCoord += diffZ * d4;
			} else {
				if (endZ > startZ) {
					sideNr = 2;
				} else {
					sideNr = 3;
				}

				startVec.xCoord += diffX * d5;
				startVec.yCoord += diffY * d5;
				startVec.zCoord = nextCheckZ;
			}

			Vec3 vec32 = Vec3.createVectorHelper(startVec.xCoord, startVec.yCoord, startVec.zCoord);
			startX = (int) (vec32.xCoord = MathHelper.floor_double(startVec.xCoord));

			if (sideNr == 5) {
				--startX;
				++vec32.xCoord;
			}

			startY = (int) (vec32.yCoord = MathHelper.floor_double(startVec.yCoord));

			if (sideNr == 1) {
				--startY;
				++vec32.yCoord;
			}

			startZ = (int) (vec32.zCoord = MathHelper.floor_double(startVec.zCoord));

			if (sideNr == 3) {
				--startZ;
				++vec32.zCoord;
			}

			Block block1 = this.world.getBlock(startX, startY, startZ);
			int block1meta = this.world.getBlockMetadata(startX, startY, startZ);

			if (!p_147447_4_ || block1.getCollisionBoundingBoxFromPool(this.world, startX, startY,
				startZ) != null) {
				if (block1.canCollideCheck(block1meta, collideWithLiquid)) {
					MovingObjectPosition movingobjectposition1 = block1.collisionRayTrace(
						this.world, startX, startY, startZ, startVec, endVec);

					if (movingobjectposition1 != null) {
						return movingobjectposition1;
					}
				} else {
					movingobjectposition2 = new MovingObjectPosition(startX, startY, startZ,
						sideNr, startVec, false);
				}
			}
		}
		return p_147447_5_ ? movingobjectposition2 : null;
	}

}