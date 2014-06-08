package test;

import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.entities.IPlasmaConnection;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPlasma extends TileEntity implements IPlasmaConnection {

	@Override
	public boolean acceptsPlasma(EnumPlasmaTypes plasma) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean providesPlasma(EnumPlasmaTypes plasma) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public short getMaxPlasmaAmount(EnumPlasmaTypes plasma) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getCurrentPlasmaAmount(EnumPlasmaTypes plasma) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short fillPlasma(EnumPlasmaTypes plasma, short amount) {
		// TODO Auto-generated method stub
		return 0;
	}

}
