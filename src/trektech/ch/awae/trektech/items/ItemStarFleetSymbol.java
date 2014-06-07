package ch.awae.trektech.items;

import ch.awae.trektech.TrekTech;
import net.minecraft.item.Item;

public class ItemStarFleetSymbol extends Item {

    public ItemStarFleetSymbol() {
        setMaxStackSize(1);
        setUnlocalizedName("starfleetSymbol");
        setTextureName(TrekTech.MODID + ":starfleet_symbol");
    }

}
