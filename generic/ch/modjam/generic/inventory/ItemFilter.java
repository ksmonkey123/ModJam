package ch.modjam.generic.inventory;

import net.minecraft.item.Item;

public abstract class ItemFilter {

	/**
	 * adds an item to this filter
	 * 
	 * @param i
	 */
	public abstract void addItem(Item i);

	public abstract boolean itemAllowed(Item item);

}
