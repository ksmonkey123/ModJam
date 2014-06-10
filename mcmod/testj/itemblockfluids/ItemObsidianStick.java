package testj.itemblockfluids;

import net.minecraft.item.Item;
import testj.TutorialMod;
import testj.lib.Names;
import testj.lib.References;

public class ItemObsidianStick extends Item {
	public ItemObsidianStick() {
		this.setUnlocalizedName(Names.OStick);
		this.setTextureName(References.MOD_ID + ":" + Names.OStick);
		this.setCreativeTab(TutorialMod.modTab);
	}
}
