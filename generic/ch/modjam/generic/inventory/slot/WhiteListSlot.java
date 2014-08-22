package ch.modjam.generic.inventory.slot;

import java.util.HashSet;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class WhiteListSlot extends Slot {
	private HashSet<Item>	allowed;

	public WhiteListSlot(IInventory inventory, int slot, int guiX, int guiY) {
		super(inventory, slot, guiX, guiY);
		this.allowed = new HashSet<Item>();
	}

	public void addItem(Item i) {
		this.allowed.add(i);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return this.allowed.contains(stack.getItem());
	}
}
