package ch.awae.trektech.items;

import ch.awae.trektech.TrekTech;
import net.minecraft.item.Item;

/**
 * Plasma Containment Ring
 * 
 * This Item is used in most plasma related recipes
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
public class ItemPlasmaContainmentRing extends Item {

	/**
	 * Basic Constructor
	 */
	public ItemPlasmaContainmentRing() {
		setMaxStackSize(64);
		setUnlocalizedName("plasmaContainmentRing");
		setTextureName(TrekTech.MODID + ":plasma_containment_ring");
		setCreativeTab(TrekTech.tabCustom);
	}

}
