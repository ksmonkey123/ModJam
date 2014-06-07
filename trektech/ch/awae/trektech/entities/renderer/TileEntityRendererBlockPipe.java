package ch.awae.trektech.entities.renderer;

import org.lwjgl.opengl.GL11;

import ch.awae.trektech.TrekTech;
import ch.awae.trektech.entities.TileEntityPlasmaPipe;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRendererBlockPipe extends TileEntitySpecialRenderer {

	ResourceLocation texture = new ResourceLocation(TrekTech.MODID
			+ ":textures/blocks/conduits.png");
	float pixel = 1F / 16F;
	float textp = 1F / 64F;

	@Override
	public void renderTileEntityAt(TileEntity ent, double transX,
			double transY, double transZ, float f) {
		TileEntityPlasmaPipe t = (TileEntityPlasmaPipe) ent;
		// SETUP
		GL11.glTranslated(transX, transY, transZ);
		this.bindTexture(this.texture);
		// RENDER
		renderCore();
		if (t.connectsTo(ForgeDirection.WEST))
			connectWest();
		// CLEANUP
		GL11.glTranslated(-transX, -transY, -transZ);
	}

	private void renderCore() {
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		// NORTH
		t.addVertexWithUV(4 * pixel, 4 * pixel, 4 * pixel, 12 * textp,
				12 * textp);
		t.addVertexWithUV(4 * pixel, 12 * pixel, 4 * pixel, 12 * textp,
				4 * textp);
		t.addVertexWithUV(12 * pixel, 12 * pixel, 4 * pixel, 4 * textp,
				4 * textp);
		t.addVertexWithUV(12 * pixel, 4 * pixel, 4 * pixel, 4 * textp,
				12 * textp);
		// SOUTH
		t.addVertexWithUV(4 * pixel, 4 * pixel, 12 * pixel, 12 * textp,
				12 * textp);
		t.addVertexWithUV(12 * pixel, 4 * pixel, 12 * pixel, 12 * textp,
				4 * textp);
		t.addVertexWithUV(12 * pixel, 12 * pixel, 12 * pixel, 4 * textp,
				4 * textp);
		t.addVertexWithUV(4 * pixel, 12 * pixel, 12 * pixel, 4 * textp,
				12 * textp);
		// WEST
		t.addVertexWithUV(4 * pixel, 4 * pixel, 4 * pixel, 12 * textp,
				12 * textp);
		t.addVertexWithUV(4 * pixel, 4 * pixel, 12 * pixel, 12 * textp,
				4 * textp);
		t.addVertexWithUV(4 * pixel, 12 * pixel, 12 * pixel, 4 * textp,
				4 * textp);
		t.addVertexWithUV(4 * pixel, 12 * pixel, 4 * pixel, 4 * textp,
				12 * textp);
		// EAST
		t.addVertexWithUV(12 * pixel, 4 * pixel, 4 * pixel, 12 * textp,
				12 * textp);
		t.addVertexWithUV(12 * pixel, 12 * pixel, 4 * pixel, 12 * textp,
				4 * textp);
		t.addVertexWithUV(12 * pixel, 12 * pixel, 12 * pixel, 4 * textp,
				4 * textp);
		t.addVertexWithUV(12 * pixel, 4 * pixel, 12 * pixel, 4 * textp,
				12 * textp);
		// TOP
		t.addVertexWithUV(4 * pixel, 12 * pixel, 4 * pixel, 12 * textp,
				12 * textp);
		t.addVertexWithUV(4 * pixel, 12 * pixel, 12 * pixel, 12 * textp,
				4 * textp);
		t.addVertexWithUV(12 * pixel, 12 * pixel, 12 * pixel, 4 * textp,
				4 * textp);
		t.addVertexWithUV(12 * pixel, 12 * pixel, 4 * pixel, 4 * textp,
				12 * textp);
		// BOTTOM
		t.addVertexWithUV(4 * pixel, 4 * pixel, 4 * pixel, 12 * textp,
				12 * textp);
		t.addVertexWithUV(12 * pixel, 4 * pixel, 4 * pixel, 12 * textp,
				4 * textp);
		t.addVertexWithUV(12 * pixel, 4 * pixel, 12 * pixel, 4 * textp,
				4 * textp);
		t.addVertexWithUV(4 * pixel, 4 * pixel, 12 * pixel, 4 * textp,
				12 * textp);
		// FINISH
		t.draw();
	}

	private void connectWest() {
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		// end cap
		t.addVertexWithUV(0 * pixel, 4 * pixel, 4 * pixel, 12 * textp,
				12 * textp);
		t.addVertexWithUV(0 * pixel, 4 * pixel, 12 * pixel, 12 * textp,
				4 * textp);
		t.addVertexWithUV(0 * pixel, 12 * pixel, 12 * pixel, 4 * textp,
				4 * textp);
		t.addVertexWithUV(0 * pixel, 12 * pixel, 4 * pixel, 4 * textp,
				12 * textp);
		// north beam
		t.addVertexWithUV(0 * pixel, 4 * pixel, 4 * pixel, 16 * textp,
				12 * textp);
		t.addVertexWithUV(0 * pixel, 12 * pixel, 4 * pixel, 16 * textp,
				4 * textp);
		t.addVertexWithUV(4 * pixel, 12 * pixel, 4 * pixel, 12 * textp,
				4 * textp);
		t.addVertexWithUV(4 * pixel, 4 * pixel, 4 * pixel, 12 * textp,
				12 * textp);
		// south beam
		t.addVertexWithUV(0 * pixel, 4 * pixel, 12 * pixel, 16 * textp,
				12 * textp);
		t.addVertexWithUV(4 * pixel, 4 * pixel, 12 * pixel, 12 * textp,
				12 * textp);
		t.addVertexWithUV(4 * pixel, 12 * pixel, 12 * pixel, 12 * textp,
				4 * textp);
		t.addVertexWithUV(0 * pixel, 12 * pixel, 12 * pixel, 16 * textp,
				4 * textp);
		// finish
		t.draw();

	}
}
