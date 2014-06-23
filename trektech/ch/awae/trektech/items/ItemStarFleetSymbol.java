package ch.awae.trektech.items;

import ch.awae.trektech.TrekTech;
import net.minecraft.item.Item;

/**
 * This item is only used for the creative tab symbol
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
public class ItemStarFleetSymbol extends Item {

	/**
	 * Basic Constructor
	 */
	public ItemStarFleetSymbol() {
		setMaxStackSize(1);
		setUnlocalizedName("starfleetSymbol");
		setTextureName(TrekTech.MODID + ":starfleet_symbol");
	}

}
