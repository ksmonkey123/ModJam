package ch.judos.at.te;

import net.minecraft.nbt.NBTTagCompound;
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

}