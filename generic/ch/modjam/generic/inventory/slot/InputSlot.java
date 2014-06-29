package ch.modjam.generic.inventory.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class InputSlot extends Slot {
    
    public InputSlot(IInventory inventory, int slot, int guiX, int guiY) {
        super(inventory, slot, guiX, guiY);
    }
    
    @Override
    public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
        return false;
    }
    
}
