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

import testj.customrender.CustomRenderer.Side;

public class TECarvedDirtRenderer extends TileEntitySpecialRenderer {

	private static CustomRenderer renderer;

	public TECarvedDirtRenderer() {
		renderer = new CustomRenderer(TECarvedDirt.getTexture());
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
//		GL11.glEnable(GL11.GL_BLEND);
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
		
		t.draw();
		
		RenderHelper.enableStandardItemLighting();
		
	}

	private static void renderConnection(float texX, float texY, ForgeDirection dir) {
		// TODO Auto-generated method stub

	}

	private static void renderCore(float texX, float texY) {
		
		
//		renderer.cubeOfRadius(0.5);
		
		
		renderer.quad(-0.5, 0.5, -0.5, 1, 0.3, Side.TOP,3);
		renderer.quad(0.2, 0.5, -0.5, 1, 0.3, Side.TOP,3);
		renderer.quad(-0.2, 0.5, -0.5, 0.3, 0.4, Side.TOP,3);
		renderer.quad(-0.2, 0.3, -0.2, 0.7, 0.4, Side.TOP,3);
		
		renderer.quad(-0.5,-0.5,-0.5,1,1,Side.FRONT);
		renderer.quad(0.5,-0.5,-0.5,1,1,Side.LEFT);
		
		renderer.quad(-0.5,-0.5,-0.5,1,1,Side.RIGHT);
		
		renderer.quad(0.2,0.3,-0.2,0.7,0.2,Side.RIGHT,13);
		renderer.quad(-0.2,0.3,-0.2,0.7,0.2,Side.LEFT,13);
		renderer.quad(-0.2,0.3,-0.2,0.4,0.2,Side.BACK,13);
		
		renderer.quad(-0.5,-0.5,0.5,1,0.8,Side.BACK,8);
		renderer.quad(-0.5,0.3,0.5,0.3,0.2,Side.BACK,8);
		renderer.quad(0.2,0.3,0.5,0.3,0.2,Side.BACK,8);
		
		
		renderer.quad(-0.5,-0.5,-0.5,1,1,Side.BOTTOM);
		
	}
	
	
	
	
	

	
	
}
