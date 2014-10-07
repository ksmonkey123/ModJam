package ch.modjam.generic;

import net.minecraft.world.World;

/**
 * contains bug fixes of original minecraft methods
 * 
 * @author j
 *
 */
public class WorldFixes {
	/**
	 * Returns the highest redstone signal strength powering the given block. for neighbor blocks it
	 * only checks whether they power the direction "up" because redstone cables are sometimes
	 * bugged
	 * 
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 * @return the highest redstone signal powering the given block
	 */
	public static int getBlockPowerInputUP(World w, int x, int y, int z) {
		int l = w.isBlockProvidingPowerTo(x, y - 1, z, 1);

		if (l >= 15)
			return l;
		l = Math.max(l, w.isBlockProvidingPowerTo(x, y + 1, z, 1));

		if (l >= 15)
			return l;
		l = Math.max(l, w.isBlockProvidingPowerTo(x, y, z - 1, 1)); // originally side:2

		if (l >= 15)
			return l;
		l = Math.max(l, w.isBlockProvidingPowerTo(x, y, z + 1, 1)); // originally side:3

		if (l >= 15)
			return l;
		l = Math.max(l, w.isBlockProvidingPowerTo(x - 1, y, z, 1)); // originally side:4

		if (l >= 15)
			return l;
		l = Math.max(l, w.isBlockProvidingPowerTo(x + 1, y, z, 1)); // originally side:5
		return l >= 15 ? l : l;
	}
}
