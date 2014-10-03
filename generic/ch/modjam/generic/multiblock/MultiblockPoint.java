package ch.modjam.generic.multiblock;

/**
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class MultiblockPoint {

	// XXX: I assume these are relative coordinates to the multiblock origin point?
	private short	x;
	private short	y;
	private short	z;

	MultiblockPoint(short x, short y, short z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MultiblockPoint))
			return false;
		MultiblockPoint other = (MultiblockPoint) obj;
		return this.x == other.x && this.y == other.y && this.z == other.z;
	}

	@Override
	public int hashCode() {
		return this.x + 1000 * this.y + 1000000 * this.z;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getZ() {
		return this.z;
	}

	public int getX(int rootX) {
		return this.x + rootX;
	}

	public int getY(int rootY) {
		return this.y + rootY;
	}

	public int getZ(int rootZ) {
		return this.z + rootZ;
	}
}
