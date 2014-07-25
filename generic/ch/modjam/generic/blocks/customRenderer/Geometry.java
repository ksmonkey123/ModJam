package ch.modjam.generic.blocks.customRenderer;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

/**
 * a helper class which can handle geometry translations and rotations
 * 
 * @author j
 *
 */
public class Geometry {
	/**
	 * the transformation that is used, you can apply for example a rotation by modifying this
	 * object
	 */
	public Matrix4f				transform;
	private ArrayList<Vertex>	points;
	private ArrayList<Quad>		quads;
	private final String		texture;

	/**
	 * @param texture
	 */
	public Geometry(String texture) {
		this.transform = new Matrix4f();
		this.points = new ArrayList<Vertex>();
		this.quads = new ArrayList<Quad>();
		this.texture = texture;
	}

	/**
	 * removes all quads and resets the transformation matrix
	 */
	public void clear() {
		this.points.clear();
		this.quads.clear();
		this.transform.setIdentity();
	}

	/**
	 * adds a point to this geometry. always call this function 4 times, otherwise it can't form a
	 * valid quad
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param u
	 * @param v
	 */
	public void addPoint(float x, float y, float z, float u, float v) {
		this.addPoint(new Vertex(x, y, z, u, v));
	}

	/**
	 * @param vertex
	 */
	public void addPoint(Vertex vertex) {
		this.points.add(vertex);
		if (this.points.size() == 4)
			formQuad();
	}

	private void formQuad() {
		this.quads.add(new Quad(this.points.get(0), this.points.get(1), this.points.get(2),
			this.points.get(3)));
		this.points.clear();
	}

	/**
	 * draws the whole content of this geometry object
	 * 
	 * @param t
	 */
	public void draw(Tessellator t) {
		TextureManager render = Minecraft.getMinecraft().renderEngine;
		render.bindTexture(new ResourceLocation(this.texture));
		// GL11.glPushMatrix();
		t.startDrawingQuads();

		Vector4f pos = new Vector4f(); // optimization, only one object is created for all the
										// calculation
		for (Quad q : this.quads) {
			for (Vertex vertex : q.points) {
				pos = Matrix4f.transform(this.transform, vertex.position, pos); // reuse pos vector
																				// object
				t.addVertexWithUV(pos.x, pos.y, pos.z, vertex.u, vertex.v);
			}
		}

		t.draw();
		// GL11.glPopMatrix();
	}

}
