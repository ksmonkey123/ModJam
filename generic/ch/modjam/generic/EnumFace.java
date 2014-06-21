package ch.modjam.generic;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * This enum describes the different faces of a block.
 * 
 * @author Andreas Waelchli <andreas.waelchl@me.com>
 */
@SuppressWarnings("javadoc")
public enum EnumFace {

	BOTTOM, TOP, FRONT, BACK, RIGHT, LEFT;

	private final static int[][] directionMatrix = { { 0, 0, 0, 0 },
			{ 1, 1, 1, 1 }, { 2, 3, 4, 5 }, { 3, 2, 5, 4 }, { 4, 5, 2, 3 },
			{ 5, 4, 3, 2 } };

	/**
	 * Retrieve the absolute direction of a face for a given direction for the
	 * front face
	 * 
	 * @param face
	 *            the face to get the direction for
	 * @param frontDirection
	 *            the front face direction of the block
	 * @return the direction of the given face
	 */
	public static ForgeDirection getDirectionOfFace(EnumFace face,
			ForgeDirection frontDirection) {
		try {
			return ForgeDirection.values()[directionMatrix[face.ordinal()][frontDirection
					.ordinal() - 2]];
		} catch (IndexOutOfBoundsException e) {
			System.err.println("IndexOutOfBound in getDirectionOfFace:"
					+ frontDirection.ordinal()
					+ "\n Taking recovery measure (NORTH)");
			return ForgeDirection.NORTH;
		}
	}
}
