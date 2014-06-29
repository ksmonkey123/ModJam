package ch.modjam.generic.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author judos
 */
public class GenericNBTInventory extends AbstractInventory {

	protected NBTTagCompound	nbt;

	/**
	 * @param nbt
	 */
	public GenericNBTInventory(NBTTagCompound nbt) {
		this.nbt = nbt;
	}

	@Override
	public int getSizeInventory() {
		return this.nbt.getInteger("Slots");
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		NBTTagCompound tag = (NBTTagCompound) this.nbt.getTag("Slot" + slot);
		return ItemStack.loadItemStackFromNBT(tag);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		NBTTagCompound tag = new NBTTagCompound();
		if (stack != null)
			stack.writeToNBT(tag);
		this.nbt.setTag("Slot" + slot, tag);
	}

	@Override
	public String getInventoryName() {
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public void markDirty() {}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return true;
	}

}
