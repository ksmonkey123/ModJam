package ch.awae.trektech.entities;

import net.minecraftforge.common.util.ForgeDirection;
import ch.awae.trektech.EnumPlasmaTypes;

/**
 * This Interface allows for the interaction with the plasma system. It does
 * support separated interactions for the different faces
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 * @version 2 - incompatible to version 1
 */
public interface IPlasmaConnection {

	/**
	 * indicates if the entity accepts a certain kind of plasma. If a type of
	 * plasma is accepted, the <tt>max</tt> must be greater than 0
	 * 
	 * @param plasma
	 *            the type of plasma to check for
	 * @param direction
	 * @return true if the plasma type is accepted, false otherwise
	 * @deprecated use connectsToPlasmaConnection()
	 */
	public boolean acceptsPlasma(EnumPlasmaTypes plasma,
			ForgeDirection direction);

	/**
	 * indicates if the entity provides a certain kind of plasma.
	 * 
	 * @param plasma
	 *            the type of plasma to check for
	 * @param direction
	 * @return true if the plasma type is accepted, false otherwise
	 * @deprecated use connectsToPlasmaConnection()
	 */
	public boolean providesPlasma(EnumPlasmaTypes plasma,
			ForgeDirection direction);

	/**
	 * get the maximum amount of plasma. For any plasma type that is not
	 * accepted, this must evaluate to 0, for any accepted plasma type it must
	 * be greater than 0.
	 * 
	 * @param plasma
	 * @param direction
	 * @return
	 * @deprecated
	 */
	public short getMaxPlasmaAmount(EnumPlasmaTypes plasma,
			ForgeDirection direction);

	/**
	 * get the currently present amount of plasma. This number does not have to
	 * represent the actual value, however at least <tt>(max - current)</tt>
	 * units of plasma must be able to be accepted at any time and <tt>max</tt>
	 * must always be greater than or equal to <tt>current</tt>. Of course
	 * providing the actual value is recommended. If a plasma type is not
	 * accepted, this must return 0.
	 * 
	 * @param plasma
	 *            the type of plasma to retrieve the values for
	 * @param direction
	 *            the block face to check the plasma for
	 * @return the amount of plasma stored
	 * @deprecated use getParticleCount()
	 */
	public short getCurrentPlasmaAmount(EnumPlasmaTypes plasma,
			ForgeDirection direction);

	/**
	 * try to fill with given amount of plasma
	 * 
	 * @param plasma
	 *            the type of plasma to fill
	 * @param amount
	 *            the amount of plasma to fill
	 * @param direction
	 * @return the amount that was actually filled
	 * @deprecated use applyParticleFlow()
	 */
	public float fillPlasma(EnumPlasmaTypes plasma, short amount,
			ForgeDirection direction);

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
	 * 
	 * @param plasma
	 * @param direction
	 * @param particleCount
	 */
	public void applyParticleFlow(EnumPlasmaTypes plasma,
			ForgeDirection direction, int particleCount);

	public boolean connectsToPlasmaConnection(EnumPlasmaTypes plasma,
			ForgeDirection direction);
}
