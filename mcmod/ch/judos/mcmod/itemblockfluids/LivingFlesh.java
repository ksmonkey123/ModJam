package ch.judos.mcmod.itemblockfluids;

import net.minecraft.item.Item;
import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;

/**
 * @author judos
 * 
 */
public class LivingFlesh extends Item {

	public LivingFlesh() {
		this.setUnlocalizedName(Names.LivingFlesh);
		this.setCreativeTab(MCMod.modTab);
		this.setTextureName(References.MOD_ID + ":" + Names.LivingFlesh);
	}
}
