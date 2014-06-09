package testj.itemblockfluids;

import net.minecraft.item.Item;
import testj.TutorialMod;
import testj.lib.Names;
import testj.lib.References;

public class ObsidianStick extends Item {
	public ObsidianStick() {
		this.setUnlocalizedName(Names.OStick);
		this.setTextureName(References.MOD_ID + ":" + Names.OStick);
		this.setCreativeTab(TutorialMod.modTab);
	}
}
