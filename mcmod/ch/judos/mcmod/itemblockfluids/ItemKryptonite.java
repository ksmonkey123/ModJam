package ch.judos.mcmod.itemblockfluids;

import ch.judos.mcmod.TutorialMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;
import net.minecraft.item.Item;

@SuppressWarnings("javadoc")
public class ItemKryptonite extends Item {
	public ItemKryptonite() {
		this.setUnlocalizedName(Names.KryptonitItem);
		this.setTextureName(References.MOD_ID + ":" + Names.KryptonitItem);
		this.setCreativeTab(TutorialMod.modTab);
	}
}
