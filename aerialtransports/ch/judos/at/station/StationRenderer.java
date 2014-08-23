package ch.judos.at.station;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import ch.modjam.generic.blocks.EFace;
import ch.modjam.generic.rendering.customRenderer.Geometry2;
import ch.modjam.generic.rendering.customRenderer.Geometry3;

public class StationRenderer extends TileEntitySpecialRenderer {

	private Geometry3	g3;

	public StationRenderer() {
		this.g3 = new Geometry3(TEStation.getTextureName());
	}

	@Override
	public void renderTileEntityAt(TileEntity ent, double transX, double transY, double transZ,
			float partialTickTime) {
		if (!(ent instanceof TEStation))
			return;

		TEStation st = (TEStation) ent;
		// SETUP
		GL11.glTranslated(transX + 0.5, transY + 0.5, transZ + 0.5);
		int time = (int) (ent.getWorldObj().getTotalWorldTime());

		Vec3 pos = st.getConnectedEndCoordinatesOrSelf(partialTickTime);
		Vec3 station = Vec3.createVectorHelper(st.xCoord + 0.5, st.yCoord + 0.9, st.zCoord + 0.5);
		Vec3 relPos = pos.subtract(station);
		float angleXZ = (float) (Math.atan2(relPos.xCoord, relPos.zCoord) + Math.PI);
		float angleY = (float) (Math.atan2(relPos.yCoord, Math.hypot(relPos.xCoord, relPos.zCoord)));

		this.g3.clear();
		this.g3.subParts.clear();
		this.g3.setUvModeScaled(false);
		this.g3.transform.rotate(angleXZ, new Vector3f(0, 1, 0));

		final double s = 0.75; // side of one quad
		final double t = s / 2;
		final double b = -0.499;
		this.g3.addQuadOnSide(-t, b, -t, s, s, EFace.TOP);
		this.g3.addQuadOnSide(-t, b, -t, s, s, EFace.BOTTOM);
		this.g3.addQuadOnSide(-t, 0.5, -t, s, s, EFace.TOP);
		this.g3.addQuadOnSide(-t, 0.5, -t, s, s, EFace.BOTTOM);

		this.g3.addQuadOnSide(-t, b, -t, s, 1, EFace.FRONT);
		this.g3.addQuadOnSide(-t, b, -t, s, 1, EFace.BACK);
		this.g3.addQuadOnSide(-t, b, -t, s, 1, EFace.LEFT);
		this.g3.addQuadOnSide(-t, b, -t, s, 1, EFace.RIGHT);
		this.g3.addQuadOnSide(t, b, -t, s, 1, EFace.LEFT);
		this.g3.addQuadOnSide(t, b, -t, s, 1, EFace.RIGHT);

		// this.g3.addCubeOfRadius(0.45);

		if (st.isConnectedToSomething()) {
			GL11.glTranslated(0, 0.4, 0);

			double length = relPos.lengthVector();

			Geometry2 rope = new Geometry2("minecraft:textures/blocks/wool_colored_white.png");
			rope.setUvModeScaled(false);
			double r = 0.05; // rope thickness

			rope.addQuadOnSideWithTex(-r / 2, r / 2, 0, length, r, EFace.TOP, 0);
			rope.addQuadOnSideWithTex(-r / 2, -r / 2, 0, length, r, EFace.RIGHT, 0);
			rope.addQuadOnSideWithTex(r / 2, -r / 2, 0, length, r, EFace.LEFT, 0);
			rope.addQuadOnSideWithTex(-r / 2, -r / 2, length, r, r, EFace.BACK, 0);

			rope.transform.rotate(angleXZ, new Vector3f(0, 1, 0));
			rope.transform.rotate(angleY, new Vector3f(1, 0, 0));
			rope.draw(Tessellator.instance);

			// MovingObjectPosition x = t.getWorldObj().func_147447_a(station, pos, true, false,
			// true);
			GL11.glTranslated(0, -0.4, 0);
		}

		// r.addQuadOnSide(-0.36, -0.36, -0.36, 2 * 0.36, 0.36, EFace.FRONT);
		this.g3.draw(Tessellator.instance);

		// TEAR DOWN
		GL11.glTranslated(-transX - 0.5, -transY - 0.5, -transZ - 0.5);

	}
}
