package ch.awae.trektech.entities;

import ch.awae.trektech.EnumPlasmaTypes;

public interface IPlasmaConnection {

	/**
	 * indicates if the entity accepts a certain kind of plasma. If a type of
	 * plasma is accepted, the <tt>max</tt> must be greater than 0
	 * 
	 * @param plasma
	 *            the type of plasma to check for
	 * @return true if the plasma type is accepted, false otherwise
	 */
	public boolean acceptsPlasma(EnumPlasmaTypes plasma);

	/**
	 * indicates if the entity provides a certain kind of plasma.
	 * 
	 * @param plasma
	 *            the type of plasma to check for
	 * @return true if the plasma type is accepted, false otherwise
	 */
	public boolean providesPlasma(EnumPlasmaTypes plasma);

	/**
	 * get the maximum amount of plasma. For any plasma type that is not
	 * accepted, this must evaluate to 0, for any accepted plasma type it must
	 * be greater than 0.
	 * 
	 * @param plasma
	 * @return
	 */
	public short getMaxPlasmaAmount(EnumPlasmaTypes plasma);

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
	 * @return the amount of plasma stored
	 */
	public short getCurrentPlasmaAmount(EnumPlasmaTypes plasma);

	/**
	 * try to fill with given amount of plasma
	 * 
	 * @param plasma
	 *            the type of plasma to fill
	 * @param amount
	 *            the amount of plasma to fill
	 * @return the amount that was actually filled
	 */
	public short fillPlasma(EnumPlasmaTypes plasma, short amount);
}
