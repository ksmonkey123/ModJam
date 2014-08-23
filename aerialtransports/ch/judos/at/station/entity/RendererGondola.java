package ch.judos.at.station.entity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import ch.judos.at.ATMain;
import ch.judos.at.lib.ATNames;
import ch.modjam.generic.blocks.EFace;
import ch.modjam.generic.rendering.ItemRendering;
import ch.modjam.generic.rendering.customRenderer.Geometry3;

public class RendererGondola extends Render {

	private static final ResourceLocation	TEXTURE	= new ResourceLocation(
														ATMain.MOD_ID + ":textures/entity/" + ATNames.gondola + ".png");

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return TEXTURE;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float fx,
			float partialTickTime) {
		if (!(entity instanceof EntityGondola))
			return;
		EntityGondola ent = (EntityGondola) entity;

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);

		Geometry3 g = new Geometry3(ATMain.MOD_ID + ":textures/entity/gondola2.png");
		g.transform.rotate(entity.rotationYaw, new Vector3f(0, 1, 0));
		g.setUvModeScaled(true);

		double in = 0.25;
		double ou = 0.3;
		double wo = 2 * ou; // outer width
		double wi = 2 * in; // inner width

		double hb = -0.4; // lowest height (out)
		double hi = -0.38; // lowest height (inside)
		double ra = -0.1; // border

		g.addQuadOnSideWithTex(-in, hi, -in, wi, wi, EFace.TOP, 0);
		g.addQuadOnSideWithTex(-ou, hb, -ou, wo, wo, EFace.BOTTOM, 1);

		for (int i = 0; i < 4; i++) {
			Geometry3 side = new Geometry3(g);
			side.transform.rotate((float) (Math.PI / 2 * i), new Vector3f(0, 1, 0));
			side.setUvModeScaled(true);

			side.addQuadOnSideWithTex(-in, hi, -in, wi, ra - hi, EFace.BACK, 0);
			side.addQuadOnSideWithTex(-ou, hb, -ou, wo, ra - hb, EFace.FRONT, 1);

			side.setUvModeScaled(false);
			side.addQuadOnSideWithTex(-ou, ra, -ou, ou - in, wo, EFace.TOP, 1);

		}

		// g.addCubeOfRadius(0.3);

		g.draw(Tessellator.instance);

		if (ent.transportGoods != null) {
			GL11.glTranslated(0, -0.25, 0);
			GL11.glRotated(entity.rotationYaw / Math.PI * 180, 0, 1, 0);
			ItemRendering.render3DItem(ent.transportGoods, partialTickTime, false);
		}

		GL11.glPopMatrix();
	}

}
