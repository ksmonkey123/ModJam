package ch.judos.at.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import ch.judos.at.te.TEStation;
import ch.modjam.generic.blocks.EFace;
import ch.modjam.generic.rendering.ItemRendering;
import ch.modjam.generic.rendering.customRenderer.Geometry2;
import ch.modjam.generic.rendering.customRenderer.Geometry3;

public class StationRenderer extends TileEntitySpecialRenderer {

	private Geometry3	r;

	public StationRenderer() {
		this.r = new Geometry3(TEStation.getTextureName());
	}

	@Override
	public void renderTileEntityAt(TileEntity ent, double transX, double transY, double transZ,
			float partialTickTime) {
		if (!(ent instanceof TEStation))
			return;

		TEStation t = (TEStation) ent;
		// SETUP
		GL11.glTranslated(transX + 0.5, transY + 0.5, transZ + 0.5);
		int time = (int) (ent.getWorldObj().getTotalWorldTime());
		float angle = (float) time / 50;

		r.clear();
		r.subParts.clear();
		r.setUvModeScaled(false);
		r.transform.rotate(angle, new Vector3f(0, 1, 0));

		final double s = 0.415; // side of one quad for an 8-sided cylinder
		for (int i = 0; i < 8; i++) {
			Geometry2 g = new Geometry2(r);
			g.setUvModeScaled(false);
			g.transform.rotate((float) (i * 2 * Math.PI / 8), new Vector3f(0, 1, 0));
			g.addQuadOnSide(-s / 2, -0.5, -0.5, s, 1, EFace.FRONT);

			double us = 1. / 4;
			double ue = 2. / 4;
			double vs = 0;
			double ve = 1. / 4;
			g.addPoint(-s / 2f, 0.5f, 0, us, vs);
			g.addPoint(s / 2f, 0.5f, 0, ue, vs);
			g.addPoint(s / 2f, 0.49f, -0.5f, ue, ve);
			g.addPoint(-s / 2f, 0.5f, -0.5f, us, ve);

			Item x = Items.apple;
			x.getIconFromDamage(0);
			// g.addQuadOnSide(-s / 2, 0.5, -0.5, s, 0.5, EFace.TOP);
		}

		Item i = Items.apple;
		IIcon icon = i.getIconFromDamage(0);

		// r.addQuadOnSide(-0.36, -0.36, -0.36, 2 * 0.36, 0.36, EFace.FRONT);
		r.draw(Tessellator.instance);

		ItemRendering.render3DItem(new ItemStack(Items.diamond, 1), partialTickTime, true);
		// TEAR DOWN
		GL11.glTranslated(-transX - 0.5, -transY - 0.5, -transZ - 0.5);

	}

}
