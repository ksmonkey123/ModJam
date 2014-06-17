package ch.judos.mcmod.itemblockfluids;

import ch.judos.mcmod.TutorialMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;
import net.minecraft.item.Item;

public class ItemObsidianStick extends Item {
	public ItemObsidianStick() {
		this.setUnlocalizedName(Names.OStick);
		this.setTextureName(References.MOD_ID + ":" + Names.OStick);
		this.setCreativeTab(TutorialMod.modTab);
	}
}
