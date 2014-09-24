package ch.modjam.generic.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import ch.modjam.generic.helper.NBTStorable;

public class BlockCoordinates implements NBTStorable {
	public int	x;
	public int	y;
	public int	z;

	public BlockCoordinates() {
		// needed for generic instantiation
	}

	public BlockCoordinates(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public BlockCoordinates copy() {
		return new BlockCoordinates(this.x, this.y, this.z);
	}

	/**
	 * creates a coordinate object based on the given tileentities coordinates
	 * 
	 * @param t
	 */
	public BlockCoordinates(TileEntity t) {
		this.x = t.xCoord;
		this.y = t.yCoord;
		this.z = t.zCoord;
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

	public boolean equalCoordinates(DestroyBlockProgress dbp) {
		return this.x == dbp.getPartialBlockX() && this.y == dbp.getPartialBlockY() && this.z == dbp
			.getPartialBlockZ();
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		this.x = tag.getInteger("x");
		this.y = tag.getInteger("y");
		this.z = tag.getInteger("z");
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		tag.setInteger("x", this.x);
		tag.setInteger("y", this.y);
		tag.setInteger("z", this.z);
	}
}
