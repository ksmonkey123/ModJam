package ch.modjam.generic.blocks.customRenderer;

/**
 * @author j
 *
 */
public class Quad {

	protected Vertex[]	points;

	/**
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 */
	public Quad(Vertex p1, Vertex p2, Vertex p3, Vertex p4) {
		this.points = new Vertex[] { p1, p2, p3, p4 };
	}
}
