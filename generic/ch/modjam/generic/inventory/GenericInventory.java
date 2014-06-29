package ch.modjam.generic.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

/**
 * @author judos
 * 
 */
public class GenericInventory extends AbstractInventory {

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
		return this.stack[slot];
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

}
