package ch.modjam.generic.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

/**
 * @author judos
 */
public abstract class AbstractInventory implements IInventory {

	/**
	 * name of the tileEntity
	 */
	public String			tileName;
	protected ItemFilter[]	filters;
	protected int			size;

	/**
	 * @param slots
	 * @param tileEntityName
	 */
	public AbstractInventory(int slots, String tileEntityName) {
		this.size = slots;
		this.tileName = tileEntityName;
		this.filters = new ItemFilter[this.getSizeInventory()];
	}

	@Override
	public int getSizeInventory() {
		return this.size;
	}

	public void addWhiteListFilter(int slot, Item i) {
		if (!(this.filters[slot] instanceof ItemFilterWhiteList))
			this.filters[slot] = new ItemFilterWhiteList();
		this.filters[slot].addItem(i);
	}

	@Override
	public String getInventoryName() {
		return StatCollector.translateToLocal("tile." + this.tileName + ".name");
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (slot < 0 || slot >= this.getSizeInventory() || amount <= 0)
			return null;

		ItemStack stack = this.getStackInSlot(slot);
		int realAmount = Math.min(stack.stackSize, amount);
		ItemStack itemStack = stack.copy();
		stack.stackSize -= realAmount;
		if (stack.stackSize == 0)
			this.setInventorySlotContents(slot, null);
		else
			this.setInventorySlotContents(slot, stack);
		itemStack.stackSize = realAmount;
		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// no items should be dropped, they should remain in the tileEntity
		return null;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack items) {
		if (this.filters[slot] != null && !this.filters[slot].itemAllowed(items.getItem()))
			return false;

		return this.getStackInSlot(slot) == null || this.getStackInSlot(slot).getItem().equals(
			items.getItem());
	}

	/**
	 * @return the first item that can be removed from the inventory
	 */
	public ItemStack getAndRemoveFirstItem() {
		for (int i = 0; i < this.getSizeInventory(); i++)
			if (this.getStackInSlot(i) != null)
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
			if (!isItemValidForSlot(i, push))
				continue;

			ItemStack s = this.getStackInSlot(i);
			if (s == null) {
				ItemStack newStack = push.copy();
				int real = Math.min(push.stackSize, push.getMaxStackSize());
				newStack.stackSize = real;
				push.stackSize -= real;
				this.setInventorySlotContents(i, newStack);
				if (push.stackSize == 0)
					return true;
			} else if (s.isItemEqual(push)) {
				int real = Math.min(push.stackSize, s.getMaxStackSize() - s.stackSize);
				push.stackSize -= real;
				s.stackSize += real;
				this.setInventorySlotContents(i, s);
				if (push.stackSize == 0)
					return true;
			}
		}
		return false;
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
	public void openInventory() {
		// not required
	}

	@Override
	public void closeInventory() {
		// not required
	}

}
