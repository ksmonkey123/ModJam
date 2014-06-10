package testj.itemblockfluids;

import net.minecraft.item.Item;
import testj.TutorialMod;
import testj.lib.Names;
import testj.lib.References;

public class ItemKryptonit extends Item {
	public ItemKryptonit() {
		this.setUnlocalizedName(Names.KryptonitItem);
		this.setTextureName(References.MOD_ID + ":" + Names.KryptonitItem);
		this.setCreativeTab(TutorialMod.modTab);
	}
}
