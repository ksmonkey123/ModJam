package ch.modjam.generic.rendering;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

/**
 * @author judos
 *
 */
public class SpriteRenderer {
	protected String	tex;

	/**
	 * @param textureNameAndPath
	 */
	public SpriteRenderer(String textureNameAndPath) {
		this.tex = textureNameAndPath;
	}

	/**
	 * used before rendering in order to draw sprites correctly
	 */
	public void begin() {
		TextureManager render = Minecraft.getMinecraft().renderEngine;
		render.bindTexture(new ResourceLocation(this.tex));
		// Turn on blending for alpha values
		GL11.glEnable(GL11.GL_BLEND);
		// set the correct blending function to overlay alpha
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glDepthMask(false);
	}

	/**
	 * used after rendering sprites, turns off alpha rendering
	 */
	public void end() {
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND); // Turn off blending
	}

	/**
	 * @param x world coordinates
	 * @param y world coordinates
	 * @param z world coordinates
	 * @param w width of the sprite (don't use >1 otherwise it will display into other blocks)
	 * @param h width of the sprite
	 */
	public void drawSprite(float x, float y, float z, float w, float h) {
		drawSprite(x, y, z, w, h, 0, 0, 1, 1);
	}

	/**
	 * @param x world coordinates
	 * @param y world coordinates
	 * @param z world coordinates
	 * @param w width of the sprite (don't use >1 otherwise it will display into other blocks)
	 * @param h width of the sprite
	 * @param u1 start u-coordinate of texture rectangle to use
	 * @param v1 start v-coordinate of texture rectangle to use
	 * @param u2 end u-coordinate
	 * @param v2 end v-coordinate
	 */
	public void drawSprite(float x, float y, float z, float w, float h, float u1, float v1,
			float u2, float v2) {
		Minecraft mc = Minecraft.getMinecraft();
		Vec3 pos = mc.thePlayer.getPosition(1);
		Vec3 diff = pos.subtract(Vec3.createVectorHelper(x + 0.5, y + 0.5, z + 0.5));

		// needed in degree for openGL
		double axz = Math.atan2(diff.xCoord, diff.zCoord) / Math.PI * 180;
		// needed in radian for own calculation
		double ay = Math.atan2(diff.yCoord, Math.hypot(diff.xCoord, diff.zCoord));

		GL11.glPushMatrix();
		GL11.glRotated(axz + 180, 0, 1, 0);

		double xP = w / 2;
		double yP = Math.cos(ay) * h / 2;
		double zP = Math.sin(ay) * h / 2;

		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.addVertexWithUV(-xP, yP, zP, u1, v1);
		t.addVertexWithUV(-xP, -yP, -zP, u1, v2);
		t.addVertexWithUV(xP, -yP, -zP, u2, v2);
		t.addVertexWithUV(xP, yP, zP, u2, v1);
		t.draw();

		GL11.glPopMatrix();
	}
}
