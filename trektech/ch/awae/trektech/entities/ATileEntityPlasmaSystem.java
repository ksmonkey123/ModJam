package ch.awae.trektech.entities;

import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.Properties;
import ch.modjam.generic.GenericTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Generic Tile Entity for the plasma system
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
public abstract class ATileEntityPlasmaSystem extends GenericTileEntity
		implements IPlasmaConnection, IWrenchable {

	@Override
	public final void tick() {
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			this.transferPlasma(direction);
		}
		this.customTick();
	}

	private void transferPlasma(ForgeDirection direction) {
		TileEntity entity = this.worldObj.getTileEntity(this.xCoord
				+ direction.offsetX, this.yCoord + direction.offsetY,
				this.zCoord + direction.offsetZ);
		if (!(entity instanceof IPlasmaConnection))
			return;
		IPlasmaConnection other = (IPlasmaConnection) entity;
		ForgeDirection opposite = direction.getOpposite();
		for (EnumPlasmaTypes plasma : EnumPlasmaTypes.VALID) {
			if (!(this.connectsToPlasmaConnection(plasma, direction) && other
					.connectsToPlasmaConnection(plasma, opposite)))
				continue;
			// calculate particle flow
			int ownCount = this.getParticleCount(plasma, direction);
			if (direction == ForgeDirection.UP)
				ownCount = applyVerticalPressureCalculation(ownCount,
						(int) this.getParticlesPerBar(plasma, direction));
			int othCount = other.getParticleCount(plasma, opposite);
			if (direction == ForgeDirection.DOWN)
				othCount = applyVerticalPressureCalculation(
						othCount,
						(int) other.getParticlesPerBar(plasma,
								direction.getOpposite()));
			float ownPpB = this.getParticlesPerBar(plasma, direction);
			float othPpB = other.getParticlesPerBar(plasma, opposite);
			int dCount = (int) ((ownPpB * othCount - othPpB * ownCount) / (ownPpB + othPpB));
			// crop transfer rate
			if (dCount > 0)
				return;
			if (dCount < -Properties.PLASMA_TRANSFER_SPEED)
				dCount = -Properties.PLASMA_TRANSFER_SPEED;
			if (dCount < 0 && dCount < -ownCount)
				dCount = -ownCount;
			// apply particle flow
			if (dCount < 0)
				this.applyParticleFlow(plasma, opposite,
						-other.applyParticleFlow(plasma, direction, -dCount));
		}
	}

	private static int applyVerticalPressureCalculation(int particleCount,
			int ppb) {
		return Math
				.max((int) (particleCount
						* Properties.VERTICAL_PRESSURE_GRADIENT - Properties.VERTICAL_PRESSURE_ARGUMENT
						* ppb), 0);
	}

	/**
	 * custom tick operations. This method is invoked at the end of each tick.
	 */
	public abstract void customTick();
}
