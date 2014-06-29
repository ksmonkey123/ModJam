package ch.modjam.generic.inventory.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class OutputSlot extends Slot {
    
    public OutputSlot(IInventory inventory, int slot, int guiX, int guiY) {
        super(inventory, slot, guiX, guiY);
    }
    
    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
        return false;
    }
    
}
