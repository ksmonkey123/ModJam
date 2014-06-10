package testj.itemblockfluids;

import net.minecraft.item.Item;
import testj.TutorialMod;
import testj.lib.Names;
import testj.lib.References;

public class ItemKryptonite extends Item {
	public ItemKryptonite() {
		this.setUnlocalizedName(Names.KryptonitItem);
		this.setTextureName(References.MOD_ID + ":" + Names.KryptonitItem);
		this.setCreativeTab(TutorialMod.modTab);
	}
}
