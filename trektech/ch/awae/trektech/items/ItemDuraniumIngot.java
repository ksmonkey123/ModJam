package ch.awae.trektech.items;

import ch.awae.trektech.TrekTech;
import net.minecraft.item.Item;

/**
 * Duranium Ingot
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
public class ItemDuraniumIngot extends Item {

	/**
	 * Basic Constructor
	 */
    public ItemDuraniumIngot() {
        setMaxStackSize(64);
        setUnlocalizedName("duraniumIngot");
        setTextureName(TrekTech.MODID + ":duranium_ingot");
        setCreativeTab(TrekTech.tabCustom);
    }

}
