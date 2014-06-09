package ch.awae.trektech.items;

import ch.awae.trektech.TrekTech;
import net.minecraft.item.Item;

public class ItemPlasmaContainmentRing extends Item {

	public ItemPlasmaContainmentRing() {
		setMaxStackSize(64);
		setUnlocalizedName("plasmaContainmentRing");
		setTextureName(TrekTech.MODID + ":plasma_containment_ring");
		setCreativeTab(TrekTech.tabCustom);
	}

}
