package test;

import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.Properties;
import ch.awae.trektech.entities.IPlasmaConnection;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPlasmaSource extends TileEntity implements
		IPlasmaConnection {

	@Override
	public boolean acceptsPlasma(EnumPlasmaTypes plasma) {
		return false;
	}

	@Override
	public boolean providesPlasma(EnumPlasmaTypes plasma) {
		return plasma == EnumPlasmaTypes.NEUTRAL;
	}

	@Override
	public short getMaxPlasmaAmount(EnumPlasmaTypes plasma) {
		return 0;
	}

	@Override
	public short getCurrentPlasmaAmount(EnumPlasmaTypes plasma) {
		return 0;
	}

	@Override
	public short fillPlasma(EnumPlasmaTypes plasma, short amount) {
		return 0;
	}

	@Override
	public void updateEntity() {
		for (ForgeDirection d : ForgeDirection.values()) {
			if (d == ForgeDirection.UNKNOWN)
				continue;
			TileEntity entity = worldObj.getTileEntity(this.xCoord + d.offsetX,
					this.yCoord + d.offsetY, this.zCoord + d.offsetZ);
			if (entity == null || !(entity instanceof IPlasmaConnection))
				continue;
			IPlasmaConnection t = (IPlasmaConnection) entity;
			// make actual transfer
			if (t.acceptsPlasma(EnumPlasmaTypes.NEUTRAL)) {
				short halfDiff = (short) Math
						.min(Properties.PLASMA_TRANSFER_SPEED,
								(t.getMaxPlasmaAmount(EnumPlasmaTypes.NEUTRAL) - t
										.getCurrentPlasmaAmount(EnumPlasmaTypes.NEUTRAL)) / 2);
				if (halfDiff <= 0)
					continue;
				t.fillPlasma(EnumPlasmaTypes.NEUTRAL, halfDiff);
			}
		}
	}

}
