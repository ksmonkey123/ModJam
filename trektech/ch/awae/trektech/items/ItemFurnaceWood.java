package ch.awae.trektech.items;

import net.minecraft.item.Item;
import ch.awae.trektech.TrekTech;

@SuppressWarnings("javadoc")
public class ItemFurnaceWood extends Item {
    
    public ItemFurnaceWood() {
        this.setMaxStackSize(64);
        this.setUnlocalizedName("furnaceWood");
        this.setTextureName(TrekTech.MODID + ":furnaceWood");
        this.setCreativeTab(TrekTech.tabCustom);
    }
    
}
