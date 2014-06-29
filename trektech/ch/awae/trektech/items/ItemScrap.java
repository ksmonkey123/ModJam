package ch.awae.trektech.items;

import ch.awae.trektech.TrekTech;
import net.minecraft.item.Item;

@SuppressWarnings("javadoc")
public class ItemScrap extends Item {
    
    public ItemScrap() {
        setMaxStackSize(64);
        setUnlocalizedName("scrap");
        setTextureName(TrekTech.MODID + ":scrap");
        setCreativeTab(TrekTech.tabCustom);
    }
    
}
