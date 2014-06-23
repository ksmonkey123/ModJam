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
			// calculate virtual particle numbers
			int ownCount = direction == ForgeDirection.UP ? applyVerticalPressureCalculation(
					this.getParticleCount(plasma, direction),
					(int) this.getParticlesPerBar(plasma, direction)) : this
					.getParticleCount(plasma, direction);
			int othCount = direction == ForgeDirection.DOWN ? applyVerticalPressureCalculation(
					other.getParticleCount(plasma, opposite),
					(int) other.getParticlesPerBar(plasma,
							direction.getOpposite())) : other.getParticleCount(
					plasma, opposite);
			float ownPpB = this.getParticlesPerBar(plasma, direction);
			float othPpB = other.getParticlesPerBar(plasma, opposite);
			// calculate transfer amount for balancing the system
			int dCount = (int) ((othPpB * ownCount - ownPpB * othCount) / (ownPpB + othPpB));
			// only perform "push" operation (outbound transfers)
			if (dCount < 0)
				return;
			// limit transfer to transfer speed and ensure to not over-drain or
			// over-fill tanks
			if (dCount > Properties.PLASMA_TRANSFER_SPEED)
				dCount = Properties.PLASMA_TRANSFER_SPEED;
			if (dCount > ownCount)
				dCount = ownCount;
			if (dCount > other.getMaxAcceptance(plasma, opposite))
				dCount = other.getMaxAcceptance(plasma, opposite);
			// apply particle flow
			this.applyParticleFlow(plasma, direction,
					-other.applyParticleFlow(plasma, opposite, dCount));
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
