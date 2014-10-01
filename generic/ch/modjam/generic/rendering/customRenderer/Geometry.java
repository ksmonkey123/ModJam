package ch.modjam.generic.rendering.customRenderer;

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
public class Geometry extends GroupGeometry {
	protected ArrayList<Vertex>	points;
	protected ArrayList<Quad>	quads;
	/**
	 * if texture is null, everything is drawn texture-less
	 */
	protected String			texture;

	/**
	 * @param texture
	 */
	public Geometry(String texture) {
		this.transform = new Matrix4f();
		this.points = new ArrayList<Vertex>();
		this.quads = new ArrayList<Quad>();
		this.texture = texture;
	}

	public void setTexture(String textureNew) {
		this.texture = textureNew;
	}

	public String getTexture() {
		return this.texture;
	}

	// instanciates a geometry part with the same texture as it's parent, and adds it to the list of
	// subParts
	public Geometry(Geometry parentPart) {
		this(parentPart.texture);
		parentPart.subParts.add(this);
	}

	@Override
	public void clear() {
		super.clear();
		this.points.clear();
		this.quads.clear();
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

	public void addPoint(double x, double y, double z, double u, double v) {
		this.addPoint(new Vertex((float) x, (float) y, (float) z, (float) u, (float) v));
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

	public void shallowCopyQuadsTo(Geometry g) {
		g.quads.addAll(this.quads);
	}

	@Override
	protected void draw(Tessellator t, Matrix4f transformMat) {
		if (this.texture != null) {
			TextureManager render = Minecraft.getMinecraft().renderEngine;
			render.bindTexture(new ResourceLocation(this.texture));
		}
		Vector4f pos = new Vector4f(); // optimization, only one object is
		// created for all the
		// calculation

		Matrix4f transformComplete = Matrix4f.mul(transformMat, this.transform, null);
		for (Quad q : this.quads) {
			for (Vertex vertex : q.points) {
				pos = Matrix4f.transform(transformComplete, vertex.position, pos);
				// reuse pos vector object
				if (this.texture != null)
					t.addVertexWithUV(pos.x, pos.y, pos.z, vertex.u, vertex.v);
				else
					t.addVertex(pos.x, pos.y, pos.z);
			}
		}

		for (GroupGeometry g : this.subParts)
			g.draw(t, transformComplete);
	}

	public void overWriteUV(int uStart, int vStart, int uEnd, int vEnd) {
		for (Quad q : this.quads) {
			q.points[0].u = uStart;
			q.points[0].v = vStart;
			q.points[1].u = uEnd;
			q.points[1].v = vStart;
			q.points[2].u = uEnd;
			q.points[2].v = vEnd;
			q.points[3].u = uStart;
			q.points[3].v = vEnd;
		}

	}

}
