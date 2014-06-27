package ch.awae.trektech.entities;

import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.Properties;
import ch.modjam.generic.tileEntity.GenericTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Generic Tile Entity for the plasma system
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public abstract class ATileEntityPlasmaSystem extends GenericTileEntity
        implements IPlasmaConnection, IWrenchable {
    
    @Override
    public final void tick() {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS)
            this.transferPlasma(direction);
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
            int ownCount = Math.min(this.getParticleCount(plasma, direction),
                    this.getMaxOutput(plasma, direction));
            if (direction == ForgeDirection.UP)
                ownCount = applyVerticalPressureCalculation(ownCount,
                        (int) this.getParticlesPerBar(plasma, direction));
            int othCount = other.getParticleCount(plasma, opposite);
            if (direction == ForgeDirection.DOWN)
                applyVerticalPressureCalculation(othCount,
                        (int) other.getParticlesPerBar(plasma, opposite));
            float ownPpB = this.getParticlesPerBar(plasma, direction);
            float othPpB = other.getParticlesPerBar(plasma, opposite);
            // calculate transfer amount for balancing the system
            int dCount = (int) ((othPpB * ownCount - ownPpB * othCount) / (ownPpB + othPpB));
            // over-fill tanks
            if (dCount > Properties.PLASMA_TRANSFER_SPEED)
                dCount = Properties.PLASMA_TRANSFER_SPEED;
            if (dCount > ownCount)
                dCount = ownCount;
            if (dCount > other.getMaxInput(plasma, opposite))
                dCount = other.getMaxInput(plasma, opposite);
            // only perform "push" operation (outbound transfers)
            if (dCount < 0)
                return;
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
    
    @Override
    public int getMaxInput(EnumPlasmaTypes plasma, ForgeDirection direction) {
        return Integer.MAX_VALUE;
    }
    
    @Override
    public int getMaxOutput(EnumPlasmaTypes plasma, ForgeDirection direction) {
        return Integer.MAX_VALUE;
    }
    
    /**
     * custom tick operations. This method is invoked at the end of each tick.
     */
    public abstract void customTick();
}
