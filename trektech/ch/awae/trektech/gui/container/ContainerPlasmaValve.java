package ch.awae.trektech.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class ContainerPlasmaValve extends Container {
    
    /**
     * @param inventory
     * @param te
     */
    public ContainerPlasmaValve(InventoryPlayer inventory) {
        bindPlayerInventory(inventory);
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
    
    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 9; j++)
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                        8 + j * 18, 84 + i * 18));
        for (int i = 0; i < 9; i++)
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        return null;
    }
    
}
