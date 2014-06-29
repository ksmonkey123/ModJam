package ch.modjam.generic.inventory.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

/**
 * This slot does accept items, but they cannot be removed.
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 * 
 */
public class InputSlot extends Slot {
    
    /**
     * basic constructor
     * 
     * @param inventory
     * @param slot
     * @param guiX
     * @param guiY
     */
    public InputSlot(IInventory inventory, int slot, int guiX, int guiY) {
        super(inventory, slot, guiX, guiY);
    }
    
    @Override
    public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
        return false;
    }
    
}
