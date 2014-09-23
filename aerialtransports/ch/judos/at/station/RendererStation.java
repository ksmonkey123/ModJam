package ch.judos.at.station;

import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import ch.judos.at.ATMain;
import ch.judos.at.lib.ATNames;
import ch.modjam.generic.blocks.BlockCoordinates;
import ch.modjam.generic.blocks.EFace;
import ch.modjam.generic.rendering.BaseTESRenderer;
import ch.modjam.generic.rendering.customRenderer.Geometry2;
import ch.modjam.generic.rendering.customRenderer.Geometry3;

public class RendererStation extends BaseTESRenderer {

	public RendererStation() {

	}

	@Override
	public void renderTileEntityAt(TileEntity ent, double transX, double transY, double transZ,
			float partialTickTime) {
		if (!(ent instanceof TEStation))
			return;

		TEStation st = (TEStation) ent;
		// SETUP
		GL11.glTranslated(transX + 0.5, transY + 0.5, transZ + 0.5);

		Vec3 pos = st.getConnectedEndCoordinatesOrSelf(partialTickTime);
		Vec3 station = Vec3.createVectorHelper(st.xCoord + 0.5, st.yCoord + 0.9, st.zCoord + 0.5);
		Vec3 relPos = pos.subtract(station);
		float angleXZ = (float) (Math.atan2(relPos.xCoord, relPos.zCoord));
		float angleY = (float) (Math.atan2(relPos.yCoord, Math.hypot(relPos.xCoord, relPos.zCoord)));
		double lengthRope;

		if (!st.isConnectedToSomethingAndSender())
			lengthRope = 0;
		else
			lengthRope = relPos.lengthVector();

		renderStation(angleXZ, angleY, lengthRope);
		DestroyBlockProgress bdp = getBlockDestroyProgress(new BlockCoordinates(st));
		if (bdp != null) {
			String iconName = damagedIcons[bdp.getPartialBlockDamage()].getIconName();
			System.out.println(iconName);
			Geometry3 brea = new Geometry3("minecraft:textures/blocks/" + iconName + ".png");
			brea.addQuadOnSide(0, 0, 0, 1, 1, EFace.FRONT);
			// brea.addCubeOfRadius(1);
			brea.draw(Tessellator.instance);
		}

		// TEAR DOWN
		GL11.glTranslated(-transX - 0.5, -transY - 0.5, -transZ - 0.5);
	}

	public static void renderStation(float angleXZ, float angleY, double lengthRope) {

		Geometry3 g3 = new Geometry3(ATMain.MOD_ID + ":textures/blocks/" + ATNames.station + ".png");
		g3.clear();
		g3.setUvModeScaled(false);
		g3.transform.rotate(angleXZ, new Vector3f(0, 1, 0));

		final double s = 0.75; // side of one quad
		final double t = s / 2;
		final double b = -0.499;
		g3.setUvModeScaled(true);
		g3.addCubeWithoutFaces(s, 1, s, 0, EFace.FRONT);

		g3.setOffset(0, 0, -0.05);
		g3.addCubeWithoutFaces(s - 0.1, 0.9, s - 0.1, 1, true, EFace.FRONT);

		g3.setOffset(0, 0, 0);
		g3.setUvModeScaled(false);
		g3.addQuadOnSideWithTex(s / 2 - 0.1, -0.5, -s / 2, 0.1, 1, EFace.FRONT, 0);
		g3.addQuadOnSideWithTex(-s / 2, -0.5, -s / 2, 0.1, 1, EFace.FRONT, 0);
		g3.addQuadOnSideWithTex(-s / 2 + 0.1, 0.45, -s / 2, s - 0.2, 0.05, EFace.FRONT, 0);
		g3.addQuadOnSideWithTex(-s / 2 + 0.1, -0.5, -s / 2, s - 0.2, 0.05, EFace.FRONT, 0);
		g3.setOffset(0, 0.4, 0);
		g3.setUvModeScaled(true);
		g3.addCube(0.15, 0.1, 0.5, 2);

		// g3.addCubeOfRadius(0.45);

		if (lengthRope > 0) {
			GL11.glTranslated(0, 0.4, 0);

			double length = lengthRope;

			Geometry2 rope = new Geometry2("minecraft:textures/blocks/wool_colored_white.png");
			rope.setUvModeScaled(false);
			double r = 0.05; // rope thickness

			rope.addQuadOnSideWithTex(-r / 2, r / 2, -length, length, r, EFace.TOP, 0);
			rope.addQuadOnSideWithTex(-r / 2, -r / 2, -length, length, r, EFace.BOTTOM, 0);
			rope.addQuadOnSideWithTex(-r / 2, -r / 2, -length, length, r, EFace.RIGHT, 0);
			rope.addQuadOnSideWithTex(r / 2, -r / 2, -length, length, r, EFace.LEFT, 0);
			rope.addQuadOnSideWithTex(-r / 2, -r / 2, -length, r, r, EFace.FRONT, 0);

			rope.transform.rotate(angleXZ, new Vector3f(0, 1, 0));
			rope.transform.rotate(angleY, new Vector3f(-1, 0, 0));
			rope.draw(Tessellator.instance);

			// MovingObjectPosition x = t.getWorldObj().func_147447_a(station, pos, true, false,
			// true);
			GL11.glTranslated(0, -0.4, 0);
		}

		// r.addQuadOnSide(-0.36, -0.36, -0.36, 2 * 0.36, 0.36, EFace.FRONT);
		g3.draw(Tessellator.instance);
	}
}
