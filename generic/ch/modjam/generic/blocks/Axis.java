package ch.modjam.generic.blocks;

/**
 * 
 * 
 * @author j
 *
 */
public enum Axis {
	X(0), Y(1), Z(2);
	public final int	index;

	private Axis(int i) {
		this.index = i;
	}
}