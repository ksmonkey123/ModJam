package ch.modjam.generic.blocks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.lwjgl.util.vector.Vector4f;

public class Collision {

	protected World						world;
	private boolean						traceAllBlocks;
	private boolean						collideWithLiquids;
	private ArrayList<BlockCoordinates>	allBlocks;

	public Collision(World w, boolean collideWithLiquids, boolean traceAll) {
		this.collideWithLiquids = collideWithLiquids;
		this.allBlocks = new ArrayList<BlockCoordinates>();
		this.traceAllBlocks = traceAll;
		this.world = w;
	}

	public ArrayList<BlockCoordinates> getAllBlocks() {
		return this.allBlocks;
	}

	/**
	 * @param startVec
	 * @param endVec
	 * @param excludeStart
	 * @param excludeEnd
	 * @param sqRadius radius of collision (4 rays on the edge of the square with radius r will be
	 *            used)
	 * @return a set of block coordinates that collide with the given area
	 */
	public Set<BlockCoordinates> detectCollisionsInVolume(Vec3C startVec, Vec3C endVec,
			boolean excludeStart, boolean excludeEnd, float sqRadius) {
		HashSet<BlockCoordinates> exclude = getExclusionSetFor(startVec, endVec, excludeStart,
			excludeEnd);

		Vec3C relPos = endVec.subtract(startVec);
		Vector4f[] cornersA = relPos.toPolarCoordinates().getRectangleCornersInOrigin(sqRadius,
			startVec.toVector3f());
		Vector4f[] cornersB = relPos.toPolarCoordinates().getRectangleCornersInOrigin(sqRadius,
			endVec.toVector3f());

		Set<BlockCoordinates> result = new HashSet<BlockCoordinates>();
		for (int i = 0; i < cornersA.length; i++) {
			Vec3C start = new Vec3C(cornersA[i]);
			Vec3C end = new Vec3C(cornersB[i]);
			Set<BlockCoordinates> s1 = detectCollisions(start, end, exclude);
			result.addAll(s1);
		}

		return result;
	}

	public HashSet<BlockCoordinates> getExclusionSetFor(Vec3C startVec, Vec3C endVec,
			boolean excludeStart, boolean excludeEnd) {
		HashSet<BlockCoordinates> exclude = new HashSet<BlockCoordinates>();
		if (excludeStart)
			exclude.add(startVec.getBlockCoords());
		if (excludeEnd)
			exclude.add(endVec.getBlockCoords());
		return exclude;
	}

	/**
	 * @param startVec
	 * @param endVec
	 * @param excludeStart
	 * @param excludeEnd
	 * @return an empty set if any value of start or endVec is NaN<br>
	 */
	public Set<BlockCoordinates> detectCollisions(Vec3C startVec, Vec3C endVec,
			boolean excludeStart, boolean excludeEnd) {
		HashSet<BlockCoordinates> exclude = getExclusionSetFor(startVec, endVec, excludeStart,
			excludeEnd);
		return detectCollisions(startVec, endVec, exclude);
	}

	public Set<BlockCoordinates> detectCollisions(Vec3C currentPoint, Vec3C endVec,
			Set<BlockCoordinates> exclude) {

		HashSet<BlockCoordinates> result = new HashSet<BlockCoordinates>();
		if (currentPoint.isNaN() || endVec.isNaN())
			return result;

		// block end coordinates
		BlockCoordinates end = endVec.getBlockCoords();
		// block starting coordinates
		BlockCoordinates current = currentPoint.getBlockCoords();

		Block block = this.world.getBlock(current.x, current.y, current.z);
		int blockMeta = this.world.getBlockMetadata(current.x, current.y, current.z);

		if (this.traceAllBlocks)
			if (!exclude.contains(current))
				this.allBlocks.add(current.copy());
		if (block.getCollisionBoundingBoxFromPool(this.world, current.x, current.y, current.z) != null && block
			.canStopRayTrace(blockMeta, this.collideWithLiquids)) {
			MovingObjectPosition mop = block.collisionRayTrace(this.world, current.x, current.y,
				current.z, currentPoint, endVec);

			if (mop != null) {
				BlockCoordinates blockC = new BlockCoordinates(mop.blockX, mop.blockY, mop.blockZ);
				if (!exclude.contains(blockC))
					result.add(blockC);
			}
		}

		int blockRemainingForCollision = 200;

		while (blockRemainingForCollision-- >= 0) {

			if (current.x == end.x && current.y == end.y && current.z == end.z)
				return result;

			boolean hasXDiff = true;
			boolean hasYDiff = true;
			boolean hasZDiff = true;
			double nextCheckX = 999.0D;
			double nextCheckY = 999.0D;
			double nextCheckZ = 999.0D;

			if (end.x > current.x) {
				nextCheckX = current.x + 1.0D;
			} else if (end.x < current.x) {
				nextCheckX = current.x + 0.0D;
			} else {
				hasXDiff = false;
			}

			if (end.y > current.y) {
				nextCheckY = current.y + 1.0D;
			} else if (end.y < current.y) {
				nextCheckY = current.y + 0.0D;
			} else {
				hasYDiff = false;
			}

			if (end.z > current.z) {
				nextCheckZ = current.z + 1.0D;
			} else if (end.z < current.z) {
				nextCheckZ = current.z + 0.0D;
			} else {
				hasZDiff = false;
			}

			double d3 = 999.0D;
			double d4 = 999.0D;
			double d5 = 999.0D;
			double diffX = endVec.xCoord - currentPoint.xCoord;
			double diffY = endVec.yCoord - currentPoint.yCoord;
			double diffZ = endVec.zCoord - currentPoint.zCoord;

			if (hasXDiff) {
				d3 = (nextCheckX - currentPoint.xCoord) / diffX;
			}

			if (hasYDiff) {
				d4 = (nextCheckY - currentPoint.yCoord) / diffY;
			}

			if (hasZDiff) {
				d5 = (nextCheckZ - currentPoint.zCoord) / diffZ;
			}

			byte sideNr;

			if (d3 < d4 && d3 < d5) {
				if (end.x > current.x) {
					sideNr = 4;
				} else {
					sideNr = 5;
				}

				currentPoint.xCoord = nextCheckX;
				currentPoint.yCoord += diffY * d3;
				currentPoint.zCoord += diffZ * d3;
			} else if (d4 < d5) {
				if (end.y > current.y) {
					sideNr = 0;
				} else {
					sideNr = 1;
				}

				currentPoint.xCoord += diffX * d4;
				currentPoint.yCoord = nextCheckY;
				currentPoint.zCoord += diffZ * d4;
			} else {
				if (end.z > current.z) {
					sideNr = 2;
				} else {
					sideNr = 3;
				}

				currentPoint.xCoord += diffX * d5;
				currentPoint.yCoord += diffY * d5;
				currentPoint.zCoord = nextCheckZ;
			}

			Vec3 vec32 = Vec3.createVectorHelper(currentPoint.xCoord, currentPoint.yCoord,
				currentPoint.zCoord);
			current.x = (int) (vec32.xCoord = MathHelper.floor_double(currentPoint.xCoord));

			if (sideNr == 5) {
				current.x--;
				++vec32.xCoord;
			}

			current.y = (int) (vec32.yCoord = MathHelper.floor_double(currentPoint.yCoord));

			if (sideNr == 1) {
				current.y--;
				++vec32.yCoord;
			}

			current.z = (int) (vec32.zCoord = MathHelper.floor_double(currentPoint.zCoord));

			if (sideNr == 3) {
				current.z--;
				++vec32.zCoord;
			}

			Block block1 = this.world.getBlock(current.x, current.y, current.z);
			int block1meta = this.world.getBlockMetadata(current.x, current.y, current.z);

			if (this.traceAllBlocks)
				if (!exclude.contains(current))
					this.allBlocks.add(current.copy());
			if (block1.getCollisionBoundingBoxFromPool(this.world, current.x, current.y, current.z) != null) {
				if (block1.canStopRayTrace(block1meta, this.collideWithLiquids)) {
					MovingObjectPosition mop = block1.collisionRayTrace(this.world, current.x,
						current.y, current.z, currentPoint, endVec);

					if (mop != null) {
						BlockCoordinates blockC = new BlockCoordinates(mop.blockX, mop.blockY,
							mop.blockZ);
						if (!exclude.contains(blockC))
							result.add(blockC);
					}
				}
			}
		}
		return result;
	}

	/**
	 * a somewhat deobfuscated version of the world.rayTrace() method
	 * 
	 * @param startVec
	 * @param endVec
	 * @param collideWithLiquid
	 * @param detectStart
	 * @param returnMiss
	 * @return null if any value of start or endVec is NaN<br>
	 */
	public MovingObjectPosition detectCollissionsOld(Vec3 startVec, Vec3 endVec,
			boolean collideWithLiquid, boolean detectStart, boolean returnMiss) {
		if (Double.isNaN(startVec.xCoord) || Double.isNaN(startVec.yCoord) || Double
			.isNaN(startVec.zCoord))
			return null;

		if (Double.isNaN(endVec.xCoord) || Double.isNaN(endVec.yCoord) || Double
			.isNaN(endVec.zCoord))
			return null;

		// block end coordinates
		int endX = MathHelper.floor_double(endVec.xCoord);
		int endY = MathHelper.floor_double(endVec.yCoord);
		int endZ = MathHelper.floor_double(endVec.zCoord);
		// block starting coordinates
		int startX = MathHelper.floor_double(startVec.xCoord);
		int startY = MathHelper.floor_double(startVec.yCoord);
		int startZ = MathHelper.floor_double(startVec.zCoord);
		Block block = this.world.getBlock(startX, startY, startZ);
		int blockMeta = this.world.getBlockMetadata(startX, startY, startZ);

		if (detectStart && block
			.getCollisionBoundingBoxFromPool(this.world, startX, startY, startZ) != null && block
			.canStopRayTrace(blockMeta, collideWithLiquid)) {
			MovingObjectPosition movingobjectposition = block.collisionRayTrace(this.world, startX,
				startY, startZ, startVec, endVec);

			if (movingobjectposition != null)
				return movingobjectposition;
		}

		MovingObjectPosition movingobjectposition2 = null;
		int blockRemainingForCollision = 200;

		while (blockRemainingForCollision-- >= 0) {

			if (startX == endX && startY == endY && startZ == endZ) {
				return returnMiss ? movingobjectposition2 : null;
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

			if (block1.getCollisionBoundingBoxFromPool(this.world, startX, startY, startZ) != null) {
				if (block1.canStopRayTrace(block1meta, collideWithLiquid)) {
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
		return returnMiss ? movingobjectposition2 : null;
	}
}
