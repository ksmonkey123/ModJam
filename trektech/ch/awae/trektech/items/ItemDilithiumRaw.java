package ch.awae.trektech.items;

import ch.awae.trektech.TrekTech;
import net.minecraft.item.Item;

public class ItemDilithiumRaw extends Item {

    public ItemDilithiumRaw() {
        setMaxStackSize(64);
        setUnlocalizedName("dilithiumRaw");
        setTextureName(TrekTech.MODID + ":dilithium_raw");
        setCreativeTab(TrekTech.tabCustom);
    }

}
