package ch.judos.mcmod.itemNbt;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import ch.judos.mcmod.MCMod;
import ch.modjam.generic.NBTItemRecipe;
import ch.modjam.generic.RecipiesCrafting;
import ch.modjam.generic.crafting.NBTModifier;

/**
 * @author judos
 * 
 */
public class HeartCrafting extends RecipiesCrafting {

	/**
	 * 
	 */
	public HeartCrafting() {
		super();
		NBTItemRecipe r = new NBTItemRecipe(MCMod.itemBoundHeart, new NBTModifier() {
			public void modifyNBT(NBTTagCompound c) {
				if (c.hasKey("Slots"))
					c.setInteger("Slots", c.getInteger("Slots") + 1);
				else
					c.setInteger("Slots", 1);
			}
		});
		r.addIngredient(Blocks.redstone_block);
		r.addIngredient(Blocks.hopper);
		addRecipe(r);
	}
}
