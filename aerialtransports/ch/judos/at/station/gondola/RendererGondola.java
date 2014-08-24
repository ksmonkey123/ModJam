package ch.judos.at.station.gondola;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
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

		renderGondola(ent.rotationYaw, ent.rotationPitch, ent.transportGoods, partialTickTime);

		GL11.glPopMatrix();
	}

	public static void renderGondola(float rotationYaw, float rotationPitch, ItemStack goods,
			float partialTickTime) {
		final float radianToDegreeF = (float) (Math.PI / 180);

		Geometry3 g = new Geometry3(ATMain.MOD_ID + ":textures/entity/gondola.png");

		g.transform.rotate(rotationYaw, new Vector3f(0, 1, 0));
		g.setUvModeScaled(true);

		final double in = 0.25; // inner x/z offset
		final double ou = 0.3; // outer x/z offset from center

		final double hb = -0.4; // lowest height (out)
		final double hi = -0.38; // lowest height (inside)
		final double ra = -0.1; // border
		// final double ro = 0.4; // height of the rope

		// final double rm = (float) (in + ou) / 2; // middle of the border
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
			hanger.transform.translate(new Vector3f(0, 0.36f, 0));
			hanger.transform.rotate(radianToDegreeF * 30, new Vector3f(1, 0, 1));
			// hanger.transform.rotate(radianToDegreeF * 28, new Vector3f(1, 0, 0));

			Geometry3 hang = new Geometry3(g.getTexture());
			hanger.addSubGeometry(hang);
			hang.transform.translate(new Vector3f(0, -0.3f, 0));
			hang.addCube(0.02, 0.5, 0.02, 3);

		}

		Geometry3 wheel = new Geometry3(g);
		wheel.transform.translate(new Vector3f(0, 0.4f, 0));
		wheel.transform.rotate(rotationPitch, new Vector3f(0, 0, 1));
		wheel.setUvModeScaled(true);
		wheel.addCube(0.3, 0.08, 0.08, 2);

		Geometry3 mech = new Geometry3(g);
		mech.transform.translate(new Vector3f(0, 0.35f, 0));
		mech.setUvModeScaled(true);
		mech.addCube(0.2, 0.1, 0.07, 4);

		g.draw(Tessellator.instance);

		if (goods != null) {
			GL11.glTranslated(0, -0.25, 0);
			GL11.glRotated(rotationYaw / Math.PI * 180, 0, 1, 0);
			ItemRendering.render3DItem(goods, partialTickTime, false);
		}

	}
}
