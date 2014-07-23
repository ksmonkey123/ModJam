package ch.judos.mcmod.gas;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import ch.modjam.generic.blocks.AnimatedTextureUtils;
import ch.modjam.generic.blocks.SpriteRenderer;

/**
 * @author judos
 *
 */
public class GasRenderer extends TileEntitySpecialRenderer {

	protected static final int[][]	offsetInbetweenBlocks	= { { 1, 0, 0 }, { 0, 0, 1 }, { 1, 0, 1 }, { -1, 0, 1 }, // all
															{ 0, 1, 0 }, // simple y case
															{ -1, 1, 1 }, { 0, 1, 1 }, { 1, 1, 1 }, { 1, 1, 0 }, // y-cases
																													// up
															{ -1, -1, 1 }, { 0, -1, 1 }, { 1, -1, 1 }, { 1, -1, 0 } // y-cases
																													// down
															};

	private SpriteRenderer			r;

	/**
	 * 
	 */
	public GasRenderer() {
		this.r = new SpriteRenderer(GasCO2.getTexture());
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
		if (!(te instanceof GasCO2TileEntity))
			return;
		GasCO2TileEntity teA = (GasCO2TileEntity) te;

		int frameCount = 20;
		float vStep = 1f / frameCount;
		float vStart = vStep * AnimatedTextureUtils.getFramePingPong(te, 6, frameCount);

		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);

		RenderHelper.disableStandardItemLighting();
		this.r.begin();

		this.r.drawSprite(teA.xCoord, teA.yCoord, teA.zCoord, 1f, 1f, 0, vStart, 1, vStart + vStep);
		// this.r.drawSprite(teA.xCoord, teA.yCoord, teA.zCoord, 1f, 1f, 0, 0, 1f, 1f);

		for (int[] off : offsetInbetweenBlocks) {
			if (teA.connectsTo(off[0], off[1], off[2])) {
				GL11.glTranslatef(off[0] / 2f, off[1] / 2f, off[2] / 2f);
				this.r.drawSprite(teA.xCoord, teA.yCoord, teA.zCoord, 1f, 1f, 0, vStart, 1,
					vStart + vStep);
				GL11.glTranslatef(-off[0] / 2f, -off[1] / 2f, -off[2] / 2f);
			}
		}

		this.r.end();
		RenderHelper.enableStandardItemLighting();
		GL11.glTranslated(-x - 0.5, -y - 0.5, -z - 0.5);
	}
}
