package ch.awae.trektech.entities;

import net.minecraft.entity.player.EntityPlayer;

/**
 * This interface provides TileEntities with the required method for TrekTech
 * wrench interactions
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public interface IWrenchable {
    
    /**
     * This method is invoked whenever the block of the tile-entity is wrenched,
     * given the block allows wrenching.
     * 
     * @param player
     *            the wrenching player.
     * @return true, if no further onBlockActivated operations should be
     *         performed, false otherwise. This may be important when wrenching
     *         a block should suppress the GUI.
     */
    public boolean onWrench(EntityPlayer player);
    
}
