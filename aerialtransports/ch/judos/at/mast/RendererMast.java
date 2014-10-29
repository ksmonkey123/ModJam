package ch.judos.at.mast;

import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import ch.judos.at.ATMain;
import ch.judos.at.lib.ATNames;
import ch.modjam.generic.blocks.BlockCoordinates;
import ch.modjam.generic.blocks.EFace;
import ch.modjam.generic.rendering.BaseTESRenderer;
import ch.modjam.generic.rendering.customRenderer.Geometry;
import ch.modjam.generic.rendering.customRenderer.Geometry3;

public class RendererMast extends BaseTESRenderer {

	public RendererMast() {

	}

	@Override
	public void renderTileEntityAt(TileEntity ent, double transX, double transY, double transZ,
			float partialTickTime) {
		if (!(ent instanceof TEMast))
			return;

		TEMast st = (TEMast) ent;
		// SETUP
		GL11.glTranslated(transX + 0.5, transY + 0.5, transZ + 0.5);

		// ent.xCoord
		int h = ent.xCoord % 4 + 2;
		float angleXZ = (float) ((-90 * h + 30.) / 180 * Math.PI);

		Geometry stationG = renderMast(angleXZ);
		DestroyBlockProgress bdp = getBlockDestroyProgress(new BlockCoordinates(st));
		if (bdp != null) {
			String iconName = damagedIcons[bdp.getPartialBlockDamage()].getIconName();
			stationG.setTexture("minecraft:textures/blocks/" + iconName + ".png");
			stationG.overWriteUV(0, 0, 1, 1);
			stationG.drawDamaged(Tessellator.instance);
		}

		// if (st.blockingBlocks != null) {
		// for (BlockCoordinates block : st.blockingBlocks) {
		// GL11.glTranslated(block.x - st.xCoord, block.y - st.yCoord, block.z - st.zCoord);
		// Tessellator.instance.setColorRGBA(255, 255, 255, 255);
		//
		// Geometry3 collBlock = new Geometry3((String) null);
		// // collBlock.setTexture(stationG.getTexture());
		// collBlock.addCubeOfRadius(1.01);
		// GL11.glDepthMask(false);
		// GL11.glColor4f(1, 0, 0, 0.5f);
		// // GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		// OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE, GL11.GL_ZERO);
		// // GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		// GL11.glDisable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_BLEND);
		// collBlock.draw(Tessellator.instance);
		//
		// GL11.glDisable(GL11.GL_BLEND);
		// GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glDepthMask(true);
		//
		// GL11.glTranslated(st.xCoord - block.x, st.yCoord - block.y, st.zCoord - block.z);
		// }
		// st.showBlockingBlocksTimer--;
		// if (st.showBlockingBlocksTimer <= 0)
		// st.blockingBlocks = null;
		// }

		// TEAR DOWN
		GL11.glTranslated(-transX - 0.5, -transY - 0.5, -transZ - 0.5);
	}

	public static Geometry renderMast(float angleXZ) {

		Geometry3 g3 = new Geometry3(ATMain.MOD_ID + ":textures/blocks/" + ATNames.mast + ".png");
		g3.clear();
		g3.transform.rotate(angleXZ, new Vector3f(0, 1, 0));

		final double s = 0.75; // side of one quad
		// final double t = s / 2;
		// final double b = -0.499;
		g3.setUvModeScaled(true);
		g3.addCubeWithoutFaces(s, 1, s, 0, EFace.FRONT, EFace.BACK);

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

		// if (lengthRope > 0) {
		// GL11.glTranslated(0, 0.4, 0);
		//
		// double length = lengthRope;
		//
		// Geometry2 rope = new Geometry2("minecraft:textures/blocks/wool_colored_white.png");
		// rope.setUvModeScaled(false);
		// double r = 0.05; // rope thickness
		//
		// rope.addQuadOnSideWithTex(-r / 2, r / 2, -length, length, r, EFace.TOP, 0);
		// rope.addQuadOnSideWithTex(-r / 2, -r / 2, -length, length, r, EFace.BOTTOM, 0);
		// rope.addQuadOnSideWithTex(-r / 2, -r / 2, -length, length, r, EFace.RIGHT, 0);
		// rope.addQuadOnSideWithTex(r / 2, -r / 2, -length, length, r, EFace.LEFT, 0);
		// rope.addQuadOnSideWithTex(-r / 2, -r / 2, -length, r, r, EFace.FRONT, 0);
		//
		// rope.transform.rotate(angleXZ, new Vector3f(0, 1, 0));
		// rope.transform.rotate(angleY, new Vector3f(-1, 0, 0));
		// rope.draw(Tessellator.instance);
		//
		// // MovingObjectPosition x = t.getWorldObj().func_147447_a(station, pos, true, false,
		// // true);
		// GL11.glTranslated(0, -0.4, 0);
		// }

		// r.addQuadOnSide(-0.36, -0.36, -0.36, 2 * 0.36, 0.36, EFace.FRONT);
		g3.draw(Tessellator.instance);
		return g3;
	}
}
