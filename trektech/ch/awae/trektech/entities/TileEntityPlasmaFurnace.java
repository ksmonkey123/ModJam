package ch.awae.trektech.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ch.modjam.generic.tileEntity.GenericTileEntity;

/**
 * 
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class TileEntityPlasmaFurnace extends GenericTileEntity implements
        ISidedInventory {
    
    //private static final int BASE_BURN_TIME = 
    private ItemStack[] stacks = new ItemStack[8];
    
    // -- ISidedInventory --
    
    @Override
    public int getSizeInventory() {
        return 8;
    }
    
    @Override
    public ItemStack getStackInSlot(int var1) {
        try {
            return this.stacks[var1];
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }
    
    @Override
    public ItemStack decrStackSize(int slot, int amt) {
        if (slot < 0 && slot > 8)
            return null;
        int trueAmount = amt;
        if (trueAmount > this.stacks[slot].stackSize)
            trueAmount = this.stacks[slot].stackSize;
        ItemStack returnStack = this.stacks[slot].copy();
        returnStack.stackSize = trueAmount;
        this.stacks[slot].stackSize -= trueAmount;
        return returnStack;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return null;
    }
    
    @Override
    public void setInventorySlotContents(int slot, ItemStack var2) {
        if (slot < 0 && slot > 8)
            return;
        
    }
    
    @Override
    public String getInventoryName() {
        return "container.plasmaFurnace";
    }
    
    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer var1) {
        return true;
    }
    
    @Override
    public void openInventory() {
        // not required
    }
    
    @Override
    public void closeInventory() {
        // not required
    }
    
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return (slot > 0 && slot < 4 && stack.getItem().equals(
                this.stacks[slot]));
    }
    
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        switch (side) {
            case 0:
                return new int[] { 2, 3 };
            case 1:
                return new int[] { 0, 1 };
            default:
                return new int[] {};
        }
    }
    
    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return side == 0
                && ((slot == 0 && stack.getItem().equals(this.stacks[0])) || ((slot == 1 && stack
                        .getItem().equals(this.stacks[1]))));
    }
    
    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return side == 1
                && ((slot == 2 && stack.getItem().equals(this.stacks[2])) || ((slot == 3 && stack
                        .getItem().equals(this.stacks[3]))));
    }
    
    // -- GenericTileEntity --
    
    @Override
    public void tick() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void writeNBT(NBTTagCompound tag) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void readNBT(NBTTagCompound tag) {
        // TODO Auto-generated method stub
        
    }
    
}
