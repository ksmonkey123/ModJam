package ch.judos.mcmod.itemblockfluids;

import net.minecraft.item.Item;
import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;

public class ItemKryptonite extends Item {
	public ItemKryptonite() {
		this.setUnlocalizedName(Names.KryptonitItem);
		this.setTextureName(References.MOD_ID + ":" + Names.KryptonitItem);
		this.setCreativeTab(MCMod.modTab);
	}
}
