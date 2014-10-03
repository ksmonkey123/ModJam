package ch.judos.mcmod.itemblockfluids;

import net.minecraft.item.Item;
import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;

public class ItemObsidianStick extends Item {
	public ItemObsidianStick() {
		this.setUnlocalizedName(Names.OStick);
		this.setTextureName(References.MOD_ID + ":" + Names.OStick);
		this.setCreativeTab(MCMod.modTab);
	}
}
