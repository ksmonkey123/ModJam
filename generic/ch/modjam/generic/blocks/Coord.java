package ch.modjam.generic.blocks;

/**
 * 
 * 
 * @author j
 *
 */
public enum Coord {
	X(0), Y(1), Z(2);
	public final int	index;

	private Coord(int i) {
		this.index = i;
	}
}