package ch.modjam.generic.blocks;

import java.util.ArrayList;
import java.util.List;

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
		return "(" + this.x + "," + this.y + "," + this.z + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.x;
		result = prime * result + this.y;
		result = prime * result + this.z;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlockCoordinates other = (BlockCoordinates) obj;
		if (this.x != other.x)
			return false;
		if (this.y != other.y)
			return false;
		if (this.z != other.z)
			return false;
		return true;
	}

	public List<BlockCoordinates> neighbors() {
		ArrayList<BlockCoordinates> result = new ArrayList<BlockCoordinates>();
		result.add(new BlockCoordinates(this.x, this.y + 1, this.z));
		result.add(new BlockCoordinates(this.x, this.y - 1, this.z));
		result.add(new BlockCoordinates(this.x + 1, this.y, this.z));
		result.add(new BlockCoordinates(this.x - 1, this.y, this.z));
		result.add(new BlockCoordinates(this.x, this.y, this.z + 1));
		result.add(new BlockCoordinates(this.x, this.y, this.z - 1));
		return result;
	}

}
