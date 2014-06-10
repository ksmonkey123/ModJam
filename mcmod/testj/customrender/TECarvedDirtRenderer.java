package testj.customrender;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class TECarvedDirtRenderer extends TileEntitySpecialRenderer {

	public TECarvedDirtRenderer() {
		
	}

	@Override
	public void renderTileEntityAt(TileEntity ent, double transX,
			double transY, double transZ, float f) {
		if (!(ent instanceof TECarvedDirt))
			return;
		TECarvedDirt t = (TECarvedDirt) ent;
		// SETUP
		GL11.glTranslated(transX + 0.5, transY + 0.5, transZ + 0.5);
		// draw
		renderBlock(t);
		GL11.glTranslated(-transX - 0.5, -transY - 0.5, -transZ - 0.5);
	}

	public static void renderBlock(IConnecting con) {
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		
		float texX = 0;
		float texY = 0;
		TextureManager render = Minecraft.getMinecraft().renderEngine;
		render.bindTexture(new ResourceLocation(TECarvedDirt.getTexture()));
		RenderHelper.disableStandardItemLighting();
		// RENDER
		renderCore(texX, texY);
		for (ForgeDirection dir : ForgeDirection.values()) {
			if (dir == ForgeDirection.UNKNOWN)
				continue;
			if (con.connectsTo(dir)) {
				renderConnection(texX, texY, dir);
			}
		}
		// CLEANUP
		RenderHelper.enableStandardItemLighting();
		
		t.draw();
	}

	private static void renderConnection(float texX, float texY, ForgeDirection dir) {
		// TODO Auto-generated method stub

	}

	private static void renderCore(float texX, float texY) {
		Tessellator t = Tessellator.instance;
		t.addVertexWithUV(-0.5, 0.5, -0.5, 0, 0);
		t.addVertexWithUV(-0.5, 0.5, 0.5, 1, 0);
		t.addVertexWithUV(0.5, 0.5, 0.5, 1, 1);
		t.addVertexWithUV(0.5, 0.5, -0.5, 0, 1);
	}
	
	
	
	
	

	
	
}
