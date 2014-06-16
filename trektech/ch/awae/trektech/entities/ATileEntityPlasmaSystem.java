package ch.awae.trektech.entities;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import ch.awae.trektech.EnumPlasmaTypes;
import ch.modjam.generic.GenericTileEntity;

public abstract class ATileEntityPlasmaSystem extends GenericTileEntity
		implements IPlasmaConnection {

	@Override
	public void tick() {
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			this.transferPlasma(direction);
		}
		this.customTick();
	}

	public abstract void customTick();

	private void transferPlasma(ForgeDirection direction) {
		TileEntity entity = this.worldObj.getTileEntity(this.xCoord
				+ direction.offsetX, this.yCoord + direction.offsetY,
				this.zCoord + direction.offsetZ);
		if (!(entity instanceof IPlasmaConnection))
			return;
		IPlasmaConnection other = (IPlasmaConnection) entity;
		ForgeDirection opposite = direction.getOpposite();
		for (EnumPlasmaTypes plasma : EnumPlasmaTypes.values()) {
			if (!(this.connectsToPlasmaConnection(plasma, direction) && other
					.connectsToPlasmaConnection(plasma, opposite)))
				continue;
			// TODO: plasma transfer
			
		}
	}
}
