package ch.awae.trektech.items;

import ch.awae.trektech.EnumUpgrade;
import ch.awae.trektech.TrekTech;
import net.minecraft.item.Item;

public class ItemUpgrade extends Item {
    
    private EnumUpgrade type;
    
    /**
     * Basic Constructor
     */
    public ItemUpgrade(EnumUpgrade type) {
        setMaxStackSize(1);
        setUnlocalizedName("upgrade" + type.ordinal());
        setTextureName(TrekTech.MODID + ":chip" + type.ordinal());
        setCreativeTab(TrekTech.tabCustom);
    }
    
}
