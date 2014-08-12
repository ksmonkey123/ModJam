package ch.modjam.generic.rendering;

import net.minecraft.tileentity.TileEntity;

/**
 * @author judos
 *
 */
public class AnimatedTextureUtils {

	/**
	 * this method helps the custom renderer to draw an animated texture. <br>
	 * based on the world time of minecraft it interates over all frames of a texture.<br>
	 * the hash of the tileentity helps to randomize the start frame of the animation.<br>
	 * <br>
	 * Ping pong means that the animation when finished is plays again but reverted, thereby you
	 * always get a smooth animation, since it does not roll over from the end frame to the first
	 * frame.
	 * 
	 * @param te
	 * @param ticksPerFrame
	 * @param amountOfFrames
	 * @return the number of the current frame
	 */
	public static int getFramePingPong(TileEntity te, int ticksPerFrame, int amountOfFrames) {
		int hash = te.xCoord ^ te.yCoord ^ te.zCoord;
		int time = (int) (te.getWorldObj().getTotalWorldTime());
		int timeFrameBased = time / ticksPerFrame + hash;
		int frame = timeFrameBased % (amountOfFrames * 2 - 1);
		// implement ping pong from end of animation back to start
		if (frame >= amountOfFrames)
			frame = amountOfFrames - 1 - (frame - amountOfFrames);
		return frame;
	}

}
