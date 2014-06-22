package ch.judos.mcmod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import ch.modjam.generic.GenericTileEntity;

public class BoxTE extends GenericTileEntity implements IInventory {

	private ItemStack stack;

	public BoxTE() {
		this.stack = null;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return this.stack;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (slot != 0 || amount <= 0)
			return null;
		int realAmount = Math.min(this.stack.stackSize, amount);
		ItemStack stack = this.stack.copy();
		this.stack.stackSize -= realAmount;
		stack.stackSize = realAmount;
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// no items should be dropped, they should remain in the tileEntity
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack items) {
		if (slot == 0)
			this.stack = items;
	}

	@Override
	public String getInventoryName() {
		return "Box";
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
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return this.stack == null || this.stack.getItem().equals(var2.getItem());
	}

	@Override
	public void tick() {
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		NBTTagList tagList = new NBTTagList();
		if (this.stack != null) {
			NBTTagCompound compoundTag = new NBTTagCompound();
			this.stack.writeToNBT(compoundTag);
			tagList.appendTag(compoundTag);
		}
		tag.setTag("Items", tagList);
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		NBTTagList tagList = tag.getTagList("Items", 10);
		NBTTagCompound compoundTag = tagList.getCompoundTagAt(0);
		this.stack = ItemStack.loadItemStackFromNBT(compoundTag);
	}

}
