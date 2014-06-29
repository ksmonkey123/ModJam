package ch.modjam.generic.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * This slot can be emptied but items cannot be placed into it.
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class OutputSlot extends Slot {
    
    /**
     * Basic Constructor
     * 
     * @param inventory
     * @param slot
     * @param guiX
     * @param guiY
     */
    public OutputSlot(IInventory inventory, int slot, int guiX, int guiY) {
        super(inventory, slot, guiX, guiY);
    }
    
    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
        return false;
    }
    
}
