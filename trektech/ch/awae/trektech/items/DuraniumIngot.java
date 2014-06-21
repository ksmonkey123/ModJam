package ch.awae.trektech.items;

import ch.awae.trektech.TrekTech;
import net.minecraft.item.Item;

@SuppressWarnings("javadoc")
public class DuraniumIngot extends Item {

    public DuraniumIngot() {
        setMaxStackSize(64);
        setUnlocalizedName("duraniumIngot");
        setTextureName(TrekTech.MODID + ":duranium_ingot");
        setCreativeTab(TrekTech.tabCustom);
    }

}
