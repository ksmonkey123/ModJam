package ch.judos.mcmod.itemblockfluids;

import net.minecraft.item.ItemFood;
import net.minecraft.potion.Potion;
import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;

/**
 * @author judos
 * 
 */
public class ItemLivingFlesh extends ItemFood {

	/**
	 * 
	 */
	public ItemLivingFlesh() {
		super(4, 2, false);
		this.setUnlocalizedName(Names.LivingFlesh);
		this.setCreativeTab(MCMod.modTab);
		this.setTextureName(References.MOD_ID + ":" + Names.LivingFlesh);
		this.setPotionEffect(Potion.regeneration.id, 20, 0, 1);
	}

}
