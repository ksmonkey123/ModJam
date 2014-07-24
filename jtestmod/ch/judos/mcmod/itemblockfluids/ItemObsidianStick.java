package ch.judos.mcmod.itemblockfluids;

import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;
import net.minecraft.item.Item;

@SuppressWarnings("javadoc")
public class ItemObsidianStick extends Item {
	public ItemObsidianStick() {
		this.setUnlocalizedName(Names.OStick);
		this.setTextureName(References.MOD_ID + ":" + Names.OStick);
		this.setCreativeTab(MCMod.modTab);
	}
}
