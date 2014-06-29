package ch.awae.trektech;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@SuppressWarnings("javadoc")
public class UpgradeSlot extends Slot {
    
    public UpgradeSlot(IInventory inventory, int slot, int guiX, int guiY) {
        super(inventory, slot, guiX, guiY);
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        for (Item upgrade : TrekTech.upgrades)
            if (stack.getItem().equals(upgrade))
                return true;
        return false;
    }
    
}
