package ch.judos.mcmod.gas;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import ch.judos.mcmod.customrender.IConnecting;
import ch.modjam.generic.tileEntity.GenericTileEntity;

/**
 * @author judos
 *
 */
public class GasCO2TileEntity extends GenericTileEntity implements IConnecting {

	/**
	 * 
	 */
	public GasCO2TileEntity() {}

	/**
	 * @param offsetX
	 * @param offsetY
	 * @param offsetZ
	 * @return true if the tileEntity with the given offset coordinates has the same base block
	 */
	public boolean connectsTo(int offsetX, int offsetY, int offsetZ) {
		int x = this.xCoord + offsetX;
		int y = this.yCoord + offsetY;
		int z = this.zCoord + offsetZ;
		TileEntity t = this.worldObj.getTileEntity(x, y, z);
		if (t == null)
			return false;

		return t.getBlockType() == this.getBlockType();
	}

	@Override
	public boolean connectsTo(ForgeDirection dir) {
		return connectsTo(dir.offsetX, dir.offsetY, dir.offsetZ);
	}

	@Override
	public boolean canUpdate() {
		return true;
	}

	@Override
	public void tick() {
		// nothing to do
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		// nothing to do
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		// nothing to do
	}

}
