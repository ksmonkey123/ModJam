package ch.modjam.generic.inventory;

import java.util.HashSet;

import net.minecraft.item.Item;

public class ItemFilterWhiteList extends ItemFilter {

	private HashSet<Item>	allowed;

	public ItemFilterWhiteList() {
		this.allowed = new HashSet<Item>();
	}

	@Override
	public void addItem(Item i) {
		this.allowed.add(i);
	}

	@Override
	public boolean itemAllowed(Item item) {
		return this.allowed.contains(item);
	}

}
