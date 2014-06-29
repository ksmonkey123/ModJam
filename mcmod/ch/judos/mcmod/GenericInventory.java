package ch.judos.mcmod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import ch.modjam.generic.inventory.InventorySlotChangedListener;

/**
 * @author judos
 * 
 */
public class GenericInventory implements IInventory {

	/**
	 * amount of stacks / slots
	 */
	public ItemStack[]							stack;
	/**
	 * name of the tileEntity
	 */
	public String								tileName;
	private List<InventorySlotChangedListener>	listeners;

	/**
	 * @param slots
	 * @param tileName used to translate with "tile." + tileName + ".name"
	 */
	public GenericInventory(int slots, String tileName) {
		this.stack = new ItemStack[slots];
		this.tileName = tileName;
		this.listeners = new ArrayList<InventorySlotChangedListener>();
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
		if (this.stack[slot].stackSize == 0)
			this.stack[slot] = null;
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
		for (InventorySlotChangedListener l : this.listeners) {
			l.slotChanged(slot, items);
		}
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
		for (int i = 0; i < this.stack.length; i++) {
			NBTTagCompound nbtStack = new NBTTagCompound();
			if (this.stack[i] != null)
				this.stack[i].writeToNBT(nbtStack);
			tag.setTag("Slot" + i, nbtStack);
		}
	}

	/**
	 * @param tag reads all it's inventory data from the passed NBTTagCompound
	 */
	public void readNBT(NBTTagCompound tag) {
		this.stack = new ItemStack[tag.getInteger("Slots")];
		for (int i = 0; i < this.stack.length; i++) {
			NBTTagCompound nbtStack = (NBTTagCompound) tag.getTag("Slot" + i);
			this.stack[i] = ItemStack.loadItemStackFromNBT(nbtStack);
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

	/**
	 * @param list
	 */
	public void addListener(InventorySlotChangedListener list) {
		this.listeners.add(list);
	}

	/**
	 * @return the first item that can be removed from the
	 */
	public ItemStack getAndRemoveFirstItem() {
		for (int i = 0; i < this.getSizeInventory(); i++)
			if (this.stack[i] != null)
				return this.decrStackSize(i, 1);
		return null;
	}

	/**
	 * @param push the stack to be pushed into the inventory (note the stacksize of this object is
	 *            changed!)
	 * @return true if everything could be pushed into the inventory
	 */
	public boolean addItemStackToInventory(ItemStack push) {
		if (push == null || push.stackSize <= 0)
			return true;
		for (int i = 0; i < this.getSizeInventory(); i++) {
			ItemStack s = this.stack[i];
			if (s.isItemEqual(push)) {
				int real = Math.min(push.stackSize, s.getMaxStackSize() - s.stackSize);
				push.stackSize -= real;
				s.stackSize += real;
				if (push.stackSize == 0)
					return true;
			}
		}
		return false;
	}

}
