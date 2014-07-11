package ch.judos.mcmod.gas;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import ch.modjam.generic.blocks.CustomRenderer;

/**
 * @author judos
 *
 */
public class GasRenderer extends TileEntitySpecialRenderer {

	private CustomRenderer	r;
	private int				frame;

	/**
	 * 
	 */
	public GasRenderer() {
		this.r = new CustomRenderer(GasCO2.getTexture());
		this.frame = 0;
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {

		if (!(te instanceof GasCO2TileEntity))
			return;

		int frameCount = 4;
		float vStep = 1f / frameCount;

		int hash = te.xCoord ^ te.yCoord ^ te.zCoord;
		int time = (int) (te.getWorldObj().getTotalWorldTime());
		float vStart = (float) (Math.abs(time / 20 + hash) % frameCount) / frameCount;

		GasCO2TileEntity teA = (GasCO2TileEntity) te;

		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);

		GL11.glEnable(GL11.GL_BLEND); // Turn on blending
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(false);

		this.r.begin(false);

		// this.r.quad(0, 0, 0, 1, 1, Side.TOP);
		this.r.drawSpriteTile(teA.xCoord, teA.yCoord, teA.zCoord, 1f, 1f, 0, vStart, 1,
			vStart + vStep);

		int[][] offset = { { 1, 0, 0 }, { 0, 0, 1 }, { 1, 0, 1 }, { -1, 0, 1 }, // all 2d xz cases
		{ 0, 1, 0 }, // simple y case
		{ -1, 1, 1 }, { 0, 1, 1 }, { 1, 1, 1 }, { 1, 1, 0 }, // y-cases up
		{ -1, -1, 1 }, { 0, -1, 1 }, { 1, -1, 1 }, { 1, -1, 0 } // y-cases down
		};
		for (int[] off : offset) {
			if (teA.connectsTo(off[0], off[1], off[2]))
				this.r.drawSprite(teA.xCoord, teA.yCoord, teA.zCoord, 1f, 1f, off[0] / 2f,
					off[1] / 2f + (float) Math.sin((float) time / 20) / 20, off[2] / 2f, 0, vStart,
					1, vStart + vStep);
		}

		this.r.end(false);
		GL11.glDepthMask(true);
		GL11.glTranslated(-x - 0.5, -y - 0.5, -z - 0.5);
		GL11.glDisable(GL11.GL_BLEND); // Turn off blending

	}
}
