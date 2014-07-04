package ch.phyranja.EssenceCrops.entities;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import ch.modjam.generic.gui.IHasGui;
import ch.modjam.generic.tileEntity.GenericTileEntity;
import ch.phyranja.EssenceCrops.guis.ContainerExtractor;
import ch.phyranja.EssenceCrops.guis.ExtractorGui;

public class TileEntityExtractor extends GenericTileEntity implements IInventory, IHasGui{

	
	private ItemStack[] items=new ItemStack[2];
	private int extractTimer=0;
	
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		tag.setInteger("timer", this.extractTimer);
        
        for (int i=0; i<this.items.length; i++) {
        	if(this.items[i]!=null){
        		NBTTagList nbttaglist = new NBTTagList();
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                this.items[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
                tag.setTag("slot"+i, nbttaglist);
        	}
        	
        }

		
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		this.extractTimer=tag.getInteger("timer");
		
		for (int i=0; i<this.items.length; i++) {
			if(this.items[i]!=null){
				NBTTagList nbttaglist = tag.getTagList("slot"+i, 10);
				NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(0);
		        this.items[i] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
			

        }
		
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return items[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (slot < 0 || slot > 1 || amount <= 0){
			return null;
		}
        int realAmount = Math.min(this.items[slot].stackSize, amount);
        ItemStack itemStack = this.items[slot].copy();
        this.items[slot].stackSize -= realAmount;
        itemStack.stackSize = realAmount;
        return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		System.out.println(slot);
		if(slot==0){
			items[slot]=stack;
		}
		
	}

	@Override
	public String getInventoryName() {
		return "Essence Extractor";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() { }

	@Override
	public void closeInventory() { }

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return true;
	}

	@Override
	public GuiContainer getGuiClient(InventoryPlayer inventory) {
		return new ExtractorGui(inventory, this);
	}

	@Override
	public Container getGuiServer(InventoryPlayer inventory) {
		return new ContainerExtractor(inventory, this);
	}

}
