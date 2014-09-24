package ch.modjam.generic.rendering.customRenderer;

import java.util.ArrayList;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

public class GroupGeometry {

	/**
	 * the transformation that is used, you can apply for example a rotation by modifying this
	 * object
	 */
	public Matrix4f					transform;
	public ArrayList<GroupGeometry>	subParts;

	public GroupGeometry() {
		this.subParts = new ArrayList<GroupGeometry>();
		this.transform = new Matrix4f();
	}

	public GroupGeometry(GroupGeometry g) {
		this();
		g.subParts.add(this);
	}

	/**
	 * removes all quads and resets the transformation matrix
	 */
	public void clear() {
		this.transform.setIdentity();
	}

	public void addSubGeometry(GroupGeometry g) {
		this.subParts.add(g);
	}

	/**
	 * draws the whole content of this geometry object
	 * 
	 * @param t
	 */
	public void draw(Tessellator t) {

		// GL11.glEnable(GL11.GL_BLEND);
		// set the correct blending function to overlay alpha
		// GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// GL11.glAlphaFunc(GL11.GL_GREATER, 0); // allow to draw pixels will alpha>0

		RenderHelper.disableStandardItemLighting();
		t.startDrawingQuads();
		draw(t, new Matrix4f());
		t.draw();

		RenderHelper.enableStandardItemLighting();

		GL11.glDisable(GL11.GL_BLEND); // Turn off blending
	}

	public void drawDamaged(Tessellator t) {
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_SRC_COLOR, GL11.GL_ONE, GL11.GL_ZERO);

		// set the correct blending function to overlay alpha
		// GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		// GL11.glAlphaFunc(GL11.GL_GREATER, 0);

		RenderHelper.disableStandardItemLighting();
		t.startDrawingQuads();
		draw(t, new Matrix4f());
		t.draw();

		RenderHelper.enableStandardItemLighting();

		GL11.glDisable(GL11.GL_BLEND); // Turn off blending
	}

	protected void draw(Tessellator t, Matrix4f transformMat) {
		Matrix4f transformComplete = Matrix4f.mul(transformMat, this.transform, null);
		for (GroupGeometry g : this.subParts)
			g.draw(t, transformComplete);
	}

}
