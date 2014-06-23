package ch.awae.trektech.items;

import ch.awae.trektech.TrekTech;
import net.minecraft.item.Item;

/**
 * Dilithium Crystal
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
public class ItemDilithiumCrystal extends Item {

	/**
	 * Basic Constructor
	 */
	public ItemDilithiumCrystal() {
		setMaxStackSize(64);
		setUnlocalizedName("dilithiumCrystal");
		setTextureName(TrekTech.MODID + ":dilithium_crystal");
		setCreativeTab(TrekTech.tabCustom);
	}

}
