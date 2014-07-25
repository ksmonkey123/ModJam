package ch.modjam.generic.blocks.customRenderer;

import org.lwjgl.util.vector.Vector4f;

/**
 * @author j
 *
 */
public class Vertex {
	/**
	 * position
	 */
	public Vector4f	position;
	/**
	 * u- texture coordinate
	 */
	public float	u;
	/**
	 * v- texture coordinate
	 */
	public float	v;

	/**
	 * @param x
	 * @param y
	 * @param z
	 * @param u
	 * @param v
	 */
	public Vertex(float x, float y, float z, float u, float v) {
		this.position = new Vector4f(x, y, z, 1);
		this.u = u;
		this.v = v;
	}

}
