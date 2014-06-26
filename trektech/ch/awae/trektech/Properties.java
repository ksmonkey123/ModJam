package ch.awae.trektech;

import net.minecraft.item.Item;

/**
 * This class contains several global parameters. Some of them may be extracted
 * into config-files later.
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class Properties {

	/**
	 * The Plasma Transfer Speed for the system.
	 */
	public static final int PLASMA_TRANSFER_SPEED = 200;

	/**
	 * This Item takes the function of the TrekTech wrench. It can be used for
	 * several functions.
	 */
	public static Item WRENCH;

	/**
	 * The Pressure drop-off in ascending pipes depending on the lower pipe
	 * pressure
	 */
	public static final float VERTICAL_PRESSURE_GRADIENT = 0.95f;
	
	/**
	 * The flat pressure drop-off in ascending pipes
	 */
	public static final float VERTICAL_PRESSURE_ARGUMENT = 0.01f;
}
