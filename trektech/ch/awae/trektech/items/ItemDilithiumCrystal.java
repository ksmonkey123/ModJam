package ch.awae.trektech.items;

import ch.awae.trektech.TrekTech;
import net.minecraft.item.Item;

@SuppressWarnings("javadoc")
public class ItemDilithiumCrystal extends Item {

    public ItemDilithiumCrystal() {
        setMaxStackSize(64);
        setUnlocalizedName("dilithiumCrystal");
        setTextureName(TrekTech.MODID + ":dilithium_crystal");
        setCreativeTab(TrekTech.tabCustom);
    }

}
