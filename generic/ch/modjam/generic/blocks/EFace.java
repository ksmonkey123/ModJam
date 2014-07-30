package ch.modjam.generic.blocks;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * This enum describes the different faces of a block.
 * 
 * @author Andreas Waelchli (andreas.waelchl@me.com)
 */
public enum EFace {
	TOP(1, 0), BOTTOM(9, 1), LEFT(4, 2), FRONT(5, 3), RIGHT(6, 4), BACK(10, 5);

	private final static int[][]	directionMatrix	= { { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 2, 3, 4, 5 }, { 3, 2, 5, 4 }, { 4, 5, 2, 3 }, { 5, 4, 3, 2 } };

	private int						tileIndex;
	private int						index;

	EFace(int tileIndex, int index) {
		// the index of the tile in the texture for this side
		this.tileIndex = tileIndex;
		// a unique index for each side, starting at 0
		this.index = index;
	}

	public int getTileIndex() {
		return this.tileIndex;
	}

	public int getIndex() {
		return this.index;
	}

	/**
	 * Retrieve the absolute direction of a face for a given direction for the front face
	 * 
	 * @param relativeFace the face to get the direction for
	 * @param frontDirection the front face direction of the block
	 * @return the direction of the given face
	 */
	public static ForgeDirection getDirectionOfFace(EFace relativeFace,
			ForgeDirection frontDirection) {
		try {
			return ForgeDirection.values()[directionMatrix[relativeFace.ordinal()][frontDirection
				.ordinal() - 2]];
		} catch (IndexOutOfBoundsException e) {
			System.err
				.println("IndexOutOfBound in getDirectionOfFace:" + frontDirection.ordinal() + "\n Taking recovery measure (NORTH)");
			return ForgeDirection.NORTH;
		}
	}
}
