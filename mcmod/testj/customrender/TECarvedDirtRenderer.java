package testj.customrender;

import org.lwjgl.opengl.GL11;

import testj.TutorialMod;
import testj.lib.References;

import ch.awae.trektech.TrekTech;
import ch.awae.trektech.entities.IPlasmaPipe;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

public class TECarvedDirtRenderer extends TileEntitySpecialRenderer {
	
	private String texture;

	public TECarvedDirtRenderer() {
		this.texture = References.MOD_ID + ":textures/blocks/" + texture + ".png";
	}

	@Override
	public void renderTileEntityAt(TileEntity ent, double transX,
			double transY, double transZ, float f) {
		if (!(ent instanceof TECarvedDirt))
			return;
		TECarvedDirt t = (TECarvedDirt) ent;

		// SETUP
		float texX = 0;
		float texY = 0;
		GL11.glTranslated(transX + 0.5, transY + 0.5, transZ + 0.5);
		this.bindTexture(new ResourceLocation(this.texture));
		RenderHelper.disableStandardItemLighting();
		// RENDER
		renderCore(texX, texY);
			for (ForgeDirection dir : ForgeDirection.values()) {
				if (dir == ForgeDirection.UNKNOWN)
					continue;
				if (t.connectsTo(dir)) {
					this.renderConnection(texX, texY, dir);
				}
			}
		// CLEANUP
		RenderHelper.enableStandardItemLighting();
		GL11.glTranslated(-transX - 0.5, -transY - 0.5, -transZ - 0.5);
	}

	private void renderConnection(float texX, float texY, ForgeDirection dir) {
		// TODO Auto-generated method stub
		
	}

	private void renderCore(float texX, float texY) {
		Tessellator t = Tessellator.instance;
		GL11.glPushMatrix();
		
		t.startDrawingQuads();
		t.addVertexWithUV(-0.5, 0.5, -0.5, 0, 0);
		t.addVertexWithUV(0.5, 0.5, -0.5, 0, 1);
		t.addVertexWithUV(0.5, 0.5, 0.5, 1, 1);
		t.addVertexWithUV(-0.5, 0.5, 0.5, 1, 0);
		t.draw();
		
		
		GL11.glPopMatrix();
	}

}
