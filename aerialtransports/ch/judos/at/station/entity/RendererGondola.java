package ch.judos.at.station.entity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import ch.judos.at.ATMain;
import ch.judos.at.lib.ATNames;
import ch.modjam.generic.rendering.customRenderer.Geometry3;

public class RendererGondola extends Render {

	private static final ResourceLocation	TEXTURE	= new ResourceLocation(
														ATMain.MOD_ID + ":textures/entity/" + ATNames.gondola + ".png");

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return TEXTURE;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float fx, float fy) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		Geometry3 g = new Geometry3(ATMain.MOD_ID + ":textures/blocks/station.png");
		g.addCubeOfRadius(0.5);

		g.draw(Tessellator.instance);
		GL11.glTranslated(-x, -y, -z);
		GL11.glPopMatrix();
	}

}
