package ch.judos.at.te;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import ch.judos.at.lib.ATNames;
import ch.judos.at.lib.ATReferences;
import ch.modjam.generic.tileEntity.GenericTileEntity;

public class TEStation extends GenericTileEntity {

	@Override
	public void tick() {
		// TODO Auto-generated method stub
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		// TODO Auto-generated method stub
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		// TODO Auto-generated method stub
	}

	public static String getTextureName() {
		return ATReferences.MOD_ID + ":textures/blocks/" + ATNames.station + ".png";
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1,
			this.yCoord + 2, this.zCoord + 1);
	}

}
