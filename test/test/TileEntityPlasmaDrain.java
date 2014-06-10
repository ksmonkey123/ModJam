package test;

import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.entities.IPlasmaConnection;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPlasmaDrain extends TileEntity implements
		IPlasmaConnection {

	short plasma = 0;

	@Override
	public boolean acceptsPlasma(EnumPlasmaTypes plasma) {
		return plasma == EnumPlasmaTypes.NEUTRAL;
	}

	@Override
	public boolean providesPlasma(EnumPlasmaTypes plasma) {
		return false;
	}

	@Override
	public short getMaxPlasmaAmount(EnumPlasmaTypes plasma) {
		return 1000;
	}

	@Override
	public short getCurrentPlasmaAmount(EnumPlasmaTypes plasma) {
		return this.plasma;
	}

	@Override
	public short fillPlasma(EnumPlasmaTypes plasma, short amount) {
		this.plasma += amount;
		return amount;
	}

	@Override
	public void updateEntity() {
		System.out.println(this.plasma);
	}

}
