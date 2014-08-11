package ch.judos.at.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import ch.judos.at.ModMain;
import ch.judos.at.te.TEStation;
import ch.modjam.generic.blocks.customRenderer.Geometry3;

public class StationRenderer extends TileEntitySpecialRenderer {

	private Geometry3	r;

	public StationRenderer() {
		this.r = new Geometry3(ModMain.station.getTextureName());
	}

	@Override
	public void renderTileEntityAt(TileEntity ent, double transX, double transY, double transZ,
			float f) {
		if (!(ent instanceof TEStation))
			return;
		TEStation t = (TEStation) ent;
		// SETUP
		GL11.glTranslated(transX + 0.5, transY + 0.5, transZ + 0.5);

		r.clear();

		int time = (int) (ent.getWorldObj().getTotalWorldTime());
		float angle = (float) time / 50;

		r.transform.rotate(angle, new Vector3f(0, 1, 0));

		r.addCubeOfRadius(0.7);

		r.draw(Tessellator.instance);
		// TEAR DOWN
		GL11.glTranslated(-transX - 0.5, -transY - 0.5, -transZ - 0.5);
	}

}
