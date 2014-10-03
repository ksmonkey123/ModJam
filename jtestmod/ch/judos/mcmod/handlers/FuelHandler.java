package ch.judos.mcmod.handlers;

import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler {

	private HashMap<Item, Integer>	map;

	public FuelHandler() {
		this.map = new HashMap<Item, Integer>();
	}

	public void addFuel(Item i, int lastForXTicks) {
		this.map.put(i, Integer.valueOf(lastForXTicks));
	}

	@Override
	public int getBurnTime(ItemStack fuel) {
		Integer x = this.map.get(fuel.getItem());
		return x == null ? 0 : x.intValue();
	}

}
