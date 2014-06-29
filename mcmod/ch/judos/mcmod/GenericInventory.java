package ch.judos.mcmod;

import java.util.Arrays;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;

/**
 * @author judos
 * 
 */
public class GenericInventory implements IInventory {

	/**
	 * amount of stacks / slots
	 */
	public ItemStack[]	stack;
	/**
	 * name of the tileEntity
	 */
	public String		tileName;

	/**
	 * @param slots
	 * @param tileName used to translate with "tile." + tileName + ".name"
	 */
	public GenericInventory(int slots, String tileName) {
		this.stack = new ItemStack[slots];
		this.tileName = tileName;
	}

	@Override
	public int getSizeInventory() {
		return this.stack.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot >= this.stack.length)
			return null;
		return this.stack[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (slot < 0 || slot > this.stack.length - 1 || amount <= 0)
			return null;
		int realAmount = Math.min(this.stack[slot].stackSize, amount);
		ItemStack itemStack = this.stack[slot].copy();
		this.stack[slot].stackSize -= realAmount;
		itemStack.stackSize = realAmount;
		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// no items should be dropped, they should remain in the tileEntity
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack items) {
		if (slot >= this.stack.length)
			return;
		this.stack[slot] = items;
	}

	@Override
	public String getInventoryName() {
		return StatCollector.translateToLocal("tile." + tileName + ".name");
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
		// not required
	}

	@Override
	public void closeInventory() {
		// not required
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack items) {
		return this.stack[slot] == null || this.stack[slot].getItem().equals(items.getItem());
	}

	/**
	 * @param tag writes all it's inventory data to the passed NBTTagCompound
	 */
	public void writeNBT(NBTTagCompound tag) {
		tag.setInteger("Slots", this.stack.length);
		NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < this.stack.length; i++) {
			if (this.stack[i] != null) {
				NBTTagCompound compoundTag = new NBTTagCompound();
				this.stack[i].writeToNBT(compoundTag);
				tagList.appendTag(compoundTag);
			} else { // write a empty stack incase the slot is empty
				ItemStack st = new ItemStack(Blocks.dirt, 0);
				NBTTagCompound compoundTag = new NBTTagCompound();
				st.writeToNBT(compoundTag);
				tagList.appendTag(compoundTag);
			}
		}
		tag.setTag("Items", tagList);
	}

	/**
	 * @param tag reads all it's inventory data from the passed NBTTagCompound
	 */
	public void readNBT(NBTTagCompound tag) {
		this.stack = new ItemStack[tag.getInteger("Slots")];
		NBTTagList tagList = tag.getTagList("Items", 10);
		for (int i = 0; i < this.stack.length; i++) {
			NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
			this.stack[i] = ItemStack.loadItemStackFromNBT(compoundTag);
			if (this.stack[i].stackSize == 0)
				this.stack[i] = null;
		}
	}

	@Override
	public void markDirty() {}

	/**
	 * resizes the amount of slots
	 * 
	 * @param newSlotCount
	 */
	public void resizeInventory(int newSlotCount) {
		this.stack = Arrays.copyOf(this.stack, newSlotCount);
	}

}
