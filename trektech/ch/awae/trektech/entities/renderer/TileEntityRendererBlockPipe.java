package ch.awae.trektech.entities.renderer;

import org.lwjgl.opengl.GL11;

import ch.awae.trektech.TrekTech;
import ch.awae.trektech.entities.TileEntityPlasmaPipe;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRendererBlockPipe extends TileEntitySpecialRenderer {

	ResourceLocation texture = new ResourceLocation(TrekTech.MODID
			+ ":textures/blocks/conduits.png");

	int texturesPerRow = 4;

	float pixel = 1F / 16F;
	float textp = 1F / 64F;

	float texX;
	float texY;

	float rad;

	public TileEntityRendererBlockPipe(int textureID, float width) {
		this.texX = (textureID / texturesPerRow) * textp;
		this.texY = (textureID % texturesPerRow) * textp;
		this.rad = width / 2F;
	}

	@Override
	public void renderTileEntityAt(TileEntity ent, double transX,
			double transY, double transZ, float f) {
		TileEntityPlasmaPipe t = (TileEntityPlasmaPipe) ent;
		// SETUP
		GL11.glTranslated(transX + 0.5, transY + 0.5, transZ + 0.5);
		this.bindTexture(this.texture);
		RenderHelper.disableStandardItemLighting();
		// RENDER
		renderCore();
		for (ForgeDirection dir : ForgeDirection.values()) {
			if (dir == ForgeDirection.UNKNOWN)
				continue;
			if (t.connectsTo(dir)) {
				this.connect(dir);
			}
		}
		// CLEANUP
		RenderHelper.enableStandardItemLighting();
		GL11.glTranslated(-transX - 0.5, -transY - 0.5, -transZ - 0.5);
	}

	private void renderCore() {
		Tessellator t = Tessellator.instance;
		// SIDES
		for (int i = 0; i < 4; i++) {
			t.startDrawingQuads();
			t.addVertexWithUV(-4 * pixel, -4 * pixel, -4 * pixel, texX + 12
					* textp, texY + 12 * textp);
			t.addVertexWithUV(-4 * pixel, 4 * pixel, -4 * pixel, texX + 12
					* textp, texY + 4 * textp);
			t.addVertexWithUV(4 * pixel, 4 * pixel, -4 * pixel, texX + 4
					* textp, texY + 4 * textp);
			t.addVertexWithUV(4 * pixel, -4 * pixel, -4 * pixel, texX + 4
					* textp, texY + 12 * textp);
			t.draw();
			GL11.glRotated(90, 0, 1, 0);
		}
		t.startDrawingQuads();
		// TOP
		t.addVertexWithUV(-4 * pixel, 4 * pixel, -4 * pixel, texX + 12 * textp,
				texY + 12 * textp);
		t.addVertexWithUV(-4 * pixel, 4 * pixel, 4 * pixel, texX + 12 * textp,
				texY + 4 * textp);
		t.addVertexWithUV(4 * pixel, 4 * pixel, 4 * pixel, texX + 4 * textp,
				texY + 4 * textp);
		t.addVertexWithUV(4 * pixel, 4 * pixel, -4 * pixel, texX + 4 * textp,
				texY + 12 * textp);
		// BOTTOM
		t.addVertexWithUV(-4 * pixel, -4 * pixel, -4 * pixel,
				texX + 12 * textp, texY + 12 * textp);
		t.addVertexWithUV(4 * pixel, -4 * pixel, -4 * pixel, texX + 12 * textp,
				texY + 4 * textp);
		t.addVertexWithUV(4 * pixel, -4 * pixel, 4 * pixel, texX + 4 * textp,
				texY + 4 * textp);
		t.addVertexWithUV(-4 * pixel, -4 * pixel, 4 * pixel, texX + 4 * textp,
				texY + 12 * textp);
		// FINISH
		t.draw();
	}

	private void connect(ForgeDirection dir) {
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
		t.startDrawingQuads();
		// draw end cap
		t.addVertexWithUV(-8 * pixel, -4 * pixel, -4 * pixel,
				texX + 12 * textp, texY + 12 * textp);
		t.addVertexWithUV(-8 * pixel, -4 * pixel, 4 * pixel, texX + 12 * textp,
				texY + 4 * textp);
		t.addVertexWithUV(-8 * pixel, 4 * pixel, 4 * pixel, texX + 4 * textp,
				texY + 4 * textp);
		t.addVertexWithUV(-8 * pixel, 4 * pixel, -4 * pixel, texX + 4 * textp,
				texY + 12 * textp);
		t.draw();
		// draw beams
		for (int i = 0; i < 4; i++) {
			t.startDrawingQuads();
			t.addVertexWithUV(-8 * pixel, -4 * pixel, -4 * pixel, texX + 16
					* textp, texY + 12 * textp);
			t.addVertexWithUV(-8 * pixel, 4 * pixel, -4 * pixel, texX + 16
					* textp, texY + 4 * textp);
			t.addVertexWithUV(-4 * pixel, 4 * pixel, -4 * pixel, texX + 12
					* textp, texY + 4 * textp);
			t.addVertexWithUV(-4 * pixel, -4 * pixel, -4 * pixel, texX + 12
					* textp, texY + 12 * textp);
			t.draw();
			GL11.glRotated(90, 1, 0, 0);
		}
		// finish: rotate back
		switch (dir) {
		case DOWN:
			GL11.glRotated(90, 0, 0, -1);
			break;
		case EAST:
			GL11.glRotated(180, 0, 1, 0);
			break;
		case NORTH:
			GL11.glRotated(90, 0, 1, 0);
			break;
		case SOUTH:
			GL11.glRotated(90, 0, -1, 0);
			break;
		case UP:
			GL11.glRotated(90, 0, 0, 1);
			break;
		default:
			break;
		}
	}
}
