package ch.judos.mcmod.itemNbt;

import net.minecraft.init.Blocks;
import ch.judos.mcmod.MCMod;
import ch.modjam.generic.crafting.NbtIncreaseIntModifier;
import ch.modjam.generic.crafting.NbtItemRecipe;
import ch.modjam.generic.crafting.NbtMaxIntMatcher;
import ch.modjam.generic.crafting.RecipiesCrafting;

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
		NbtItemRecipe r = new NbtItemRecipe(MCMod.itemBoundHeart, new NbtIncreaseIntModifier(
			BoundHeart.NBT_TRANSFER_SPEED, 1, 1));
		r.addIngredient(Blocks.redstone_block);
		r.addIngredient(Blocks.hopper);
		r.addConditionForCrafting(new NbtMaxIntMatcher(BoundHeart.NBT_TRANSFER_SPEED, 10));
		addRecipe(r);
	}
}
