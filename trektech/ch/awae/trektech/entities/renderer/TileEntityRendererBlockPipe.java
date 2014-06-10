package ch.awae.trektech.entities.renderer;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import ch.awae.trektech.entities.IPlasmaPipe;

public class TileEntityRendererBlockPipe extends TileEntitySpecialRenderer {

	int texturesPerRow = 4;

	float pixel = 1F / 16F;
	float textp = 1F / 16F;

	@Override
	public void renderTileEntityAt(TileEntity ent, double transX,
			double transY, double transZ, float f) {
		if (!(ent instanceof IPlasmaPipe))
			return;
		IPlasmaPipe t = (IPlasmaPipe) ent;
		float texX = 0;
		float texY = 0;
		float rad = t.getPipeRadius();
		if (rad == 0)
			return;
		// SETUP
		GL11.glTranslated(transX + 0.5, transY + 0.5, transZ + 0.5);
		this.bindTexture(new ResourceLocation(t.getTexture()));
		RenderHelper.disableStandardItemLighting();
		// RENDER
		renderCore(texX, texY, rad);
		if (rad < 8) {
			for (ForgeDirection dir : ForgeDirection.values()) {
				if (dir == ForgeDirection.UNKNOWN)
					continue;
				if (t.connectsTo(dir)) {
					this.renderConnection(texX, texY, rad, dir);
				}
			}
		}
		// CLEANUP
		RenderHelper.enableStandardItemLighting();
		GL11.glTranslated(-transX - 0.5, -transY - 0.5, -transZ - 0.5);
	}

	private void renderCore(float texX, float texY, float rad) {
		Tessellator t = Tessellator.instance;
		// SIDES
		for (int i = 0; i < 4; i++) {
			this.renderCap(t, texX, texY, rad, rad);
			GL11.glRotated(90, 0, 1, 0);
		}
		GL11.glRotated(90, 1, 0, 0);
		this.renderCap(t, texX, texY, rad, rad);
		GL11.glRotated(180, 1, 0, 0);
		this.renderCap(t, texX, texY, rad, rad);
		GL11.glRotated(90, 1, 0, 0);
		// FINISH
	}

	private void renderConnection(float texX, float texY, float rad,
			ForgeDirection dir) {
		
		GL11.glPushMatrix();
		// rotate in place
		switch (dir) {
		case DOWN:
			GL11.glRotated(90, 0, 0, 1);
			break;
		case EAST:
			GL11.glRotated(180, 0, 1, 0);
			break;
		case NORTH:
			GL11.glRotated(90, 0, -1, 0);
			break;
		case SOUTH:
			GL11.glRotated(90, 0, 1, 0);
			break;
		case UP:
			GL11.glRotated(90, 0, 0, -1);
			break;
		default:
			break;
		}
		Tessellator t = Tessellator.instance;
		// draw end cap
		GL11.glRotated(90, 0, 1, 0); // rotate to north
		this.renderCap(t, texX, texY, rad, 8);
		GL11.glRotated(90, 0, -1, 0); // rotate to west
		// draw beams
		for (int i = 0; i < 4; i++) {
			this.renderBeam(t, texX, texY, rad);
			GL11.glRotated(90, 1, 0, 0);
		}
		
		GL11.glPopMatrix();// finish: rotate back
		
	}

	/**
	 * renders a cap offset by <tt>-rad</tt> on the z axis facing (0, 0, 1)
	 * 
	 * @param t
	 * @param texX
	 * @param texY
	 * @param rad
	 */
	private void renderCap(Tessellator t, float texX, float texY, float rad,
			float offset) {
		t.startDrawingQuads();
		t.addVertexWithUV(-rad * pixel, -rad * pixel, -offset * pixel, texX
				+ (8 + rad) * textp, texY + (8 + rad) * textp);
		t.addVertexWithUV(-rad * pixel, rad * pixel, -offset * pixel, texX
				+ (8 + rad) * textp, texY + (8 - rad) * textp);
		t.addVertexWithUV(rad * pixel, rad * pixel, -offset * pixel, texX
				+ (8 - rad) * textp, texY + (8 - rad) * textp);
		t.addVertexWithUV(rad * pixel, -rad * pixel, -offset * pixel, texX
				+ (8 - rad) * textp, texY + (8 + rad) * textp);
		t.draw();
	}

	/**
	 * renders a beam facing (0, 0, 1)
	 * 
	 * @param t
	 * @param texX
	 * @param texY
	 * @param rad
	 */
	private void renderBeam(Tessellator t, float texX, float texY, float rad) {
		t.startDrawingQuads();
		t.addVertexWithUV(-8 * pixel, -rad * pixel, -rad * pixel, texX + 16
				* textp, texY + (8 + rad) * textp);
		t.addVertexWithUV(-8 * pixel, rad * pixel, -rad * pixel, texX + 16
				* textp, texY + (8 - rad) * textp);
		t.addVertexWithUV(-rad * pixel, rad * pixel, -rad * pixel, texX
				+ (8 + rad) * textp, texY + (8 - rad) * textp);
		t.addVertexWithUV(-rad * pixel, -rad * pixel, -rad * pixel, texX
				+ (8 + rad) * textp, texY + (8 + rad) * textp);
		t.draw();
	}

}
