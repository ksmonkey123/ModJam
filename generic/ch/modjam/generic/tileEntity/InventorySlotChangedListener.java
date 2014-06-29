package ch.modjam.generic.tileEntity;

import net.minecraft.item.ItemStack;

/**
 * @author judos
 * 
 */
public interface InventorySlotChangedListener {

	/**
	 * notification that the content of one slot has changed
	 * 
	 * @param slot
	 * @param items
	 */
	void slotChanged(int slot, ItemStack items);

}
