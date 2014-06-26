package ch.awae.trektech.entities;

import net.minecraftforge.common.util.ForgeDirection;
import ch.awae.trektech.EnumPlasmaTypes;

/**
 * This Interface allows for the interaction with the plasma system. It does
 * support separated interactions for the different faces
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 * @version 2 - incompatible to version 1
 */
public interface IPlasmaConnection {
    
    /**
     * indicates the amount of plasma the entity holds per bar of pressure.
     * 
     * @param plasma
     *            the plasma type to check for
     * @param direction
     *            the side to check on
     * @return the particle count per bar
     */
    public float getParticlesPerBar(EnumPlasmaTypes plasma,
            ForgeDirection direction);
    
    /**
     * indicates the amount of plasma particles currently held.
     * 
     * @param plasma
     *            the plasma type to check for
     * @param direction
     *            the side to check on
     * @return the plasma particle count
     */
    public int getParticleCount(EnumPlasmaTypes plasma, ForgeDirection direction);
    
    /**
     * changes the particle count by the given amount
     * 
     * @param plasma
     *            the plasma type to apply the change to
     * @param direction
     *            the side to apply the change to
     * @param particleCount
     *            the amount to change by
     * @return the actual change
     */
    public int applyParticleFlow(EnumPlasmaTypes plasma,
            ForgeDirection direction, int particleCount);
    
    /**
     * indicates whether or not a connection link can be established for a given
     * plasma type in a given direction
     * 
     * @param plasma
     *            the plasma type to check for
     * @param direction
     *            the side to check on
     * @return <tt>true</tt> if the connection is valid, <tt>false</tt>
     *         otherwise
     */
    public boolean connectsToPlasmaConnection(EnumPlasmaTypes plasma,
            ForgeDirection direction);
    
    /**
     * sets the particle count of a given plasma type on a given side to a given
     * value.
     * 
     * @param plasma
     *            the plasma type to apply the change to
     * @param direction
     *            the side to apply the change to
     * @param count
     *            the plasma particle count to apply
     * @return <tt>true</tt> if the operation has been performed, <tt>false</tt>
     *         otherwise
     */
    public boolean setParticleCount(EnumPlasmaTypes plasma,
            ForgeDirection direction, int count);
    
    /**
     * Indicates the maximum amount of plasma the entity can accept In case no
     * limit exists, this method should return <tt>Integer.MAX_VALUE</tt>
     * 
     * @param plasma
     * @param direction
     * @return the maximim amount of plasma to be accepted
     */
    public int getMaxAcceptance(EnumPlasmaTypes plasma, ForgeDirection direction);
    
}
