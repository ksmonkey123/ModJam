package ch.modjam.generic.blocks;

public class BlockCoordinates {
	public int	x;
	public int	y;
	public int	z;

	public BlockCoordinates(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}
}
