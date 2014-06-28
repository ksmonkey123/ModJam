package ch.phyranja.EssenceCrops.guis;


import ch.phyranja.EssenceCrops.entities.TileEntityExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


public class ContainerExtractor extends Container {
    
    private TileEntityExtractor tileEntity;
    
    
    public ContainerExtractor(InventoryPlayer inventory, TileEntityExtractor tileEntity) {
        this.tileEntity = tileEntity;
        addSlotToContainer(new Slot(this.tileEntity, 0, 80, 42));
        bindPlayerInventory(inventory);
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileEntity.isUseableByPlayer(player);
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
        ItemStack items = null;
        Slot slotObject = (Slot) this.inventorySlots.get(slot);
        
        // null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            items = stackInSlot.copy();
            
            // merges the item into player inventory since its in the tileEntity
            if (slot < 1) {
                if (!this.mergeItemStack(stackInSlot, 1, 37, true))
                    return null;
            }
            // places it into the tileEntity is possible since its in the
            // player inventory
            else if (!this.mergeItemStack(stackInSlot, 0, 1, false))
                return null;
            
            if (stackInSlot.stackSize == 0)
                slotObject.putStack(null);
            else
                slotObject.onSlotChanged();
            
            if (stackInSlot.stackSize == items.stackSize)
                return null;
            slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return items;
    }
}
