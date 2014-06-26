package ch.awae.trektech;

import ch.awae.trektech.entities.IWrenchable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class Handler {

	/**
	 * Checks if the TrekTech wrench is equipped and forwards the event to the
	 * TileEntity (must be of type IWrenchable)
	 * 
	 * @param player
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 * @return true if finished, false otherwise
	 */
	public static boolean handleWrenching(EntityPlayer player, World w, int x,
			int y, int z) {
		if (player.getCurrentEquippedItem() != null
				&& player.getCurrentEquippedItem().getItem()
						.equals(Properties.WRENCH)) {
			TileEntity te = w.getTileEntity(x, y, z);
			if (te == null || !(te instanceof IWrenchable))
				return false;
			IWrenchable wrenchable = ((IWrenchable) te);
			return wrenchable.onWrench(player);
		}
		return false;
	}
}
