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
import ch.modjam.generic.rendering.customRenderer.Geometry;
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
		final float radianToDegreeF = (float) (Math.PI / 180);

		Geometry3 g = new Geometry3(ATMain.MOD_ID + ":textures/entity/gondola2.png");

		g.transform.rotate(entity.rotationYaw, new Vector3f(0, 1, 0));
		g.setUvModeScaled(true);

		double in = 0.25; // inner x/z offset
		double ou = 0.3; // outer x/z offset from center

		double hb = -0.4; // lowest height (out)
		double hi = -0.38; // lowest height (inside)
		double ra = -0.1; // border
		double ro = 0.4; // height of the rope

		final double rm = (float) (in + ou) / 2; // middle of the border
		final double wo = 2 * ou; // outer width
		final double wi = 2 * in; // inner width

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

			Geometry hanger = new Geometry(side);
			hanger.transform.translate(new Vector3f(0, 0.4f, 0));
			hanger.transform.rotate(radianToDegreeF * 25, new Vector3f(0, 0, 1));
			hanger.transform.rotate(radianToDegreeF * 25, new Vector3f(1, 0, 0));

			Geometry3 hang = new Geometry3(g.getTexture());
			hanger.addSubGeometry(hang);
			hang.transform.translate(new Vector3f(0, -0.33f, 0));
			hang.addCube(0.03, 0.66, 0.03, 3);

		}

		Geometry3 wheel = new Geometry3(g);
		wheel.transform.translate(new Vector3f(0, 0.4f, 0));
		wheel.transform.rotate(entity.rotationPitch, new Vector3f(0, 0, 1));
		wheel.setUvModeScaled(true);
		wheel.addCube(0.3, 0.1, 0.1, 2);

		g.draw(Tessellator.instance);

		if (ent.transportGoods != null) {
			GL11.glTranslated(0, -0.25, 0);
			GL11.glRotated(entity.rotationYaw / Math.PI * 180, 0, 1, 0);
			ItemRendering.render3DItem(ent.transportGoods, partialTickTime, false);
		}

		GL11.glPopMatrix();
	}
}
