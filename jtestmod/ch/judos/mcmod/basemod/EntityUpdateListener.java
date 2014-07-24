package ch.judos.mcmod.basemod;

import net.minecraft.tileentity.TileEntity;

/**
 * @author judos
 *
 */
public interface EntityUpdateListener {
	/**
	 * called when a tileEntity is updated
	 * 
	 * @param te
	 */
	public void updateEntity(TileEntity te);
}
