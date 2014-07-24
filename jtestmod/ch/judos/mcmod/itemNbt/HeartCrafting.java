package ch.judos.mcmod.itemNbt;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
		upgradeTransferSpeed();
		upgradeSlots();
	}

	private void upgradeSlots() {
		NbtItemRecipe r = new NbtItemRecipe(MCMod.itemBoundHeart, new NbtIncreaseIntModifier(
			BoundHeart.NBT_SLOTS, 1, 1));
		r.addIngredient(Blocks.chest);
		r.addIngredient(Blocks.wool, 4);
		r.addIngredient(Items.gold_ingot);
		r.addConditionForCrafting(new NbtMaxIntMatcher(BoundHeart.NBT_SLOTS, 5));
		addRecipe(r);
	}

	private void upgradeTransferSpeed() {
		for (int i = 1; i <= 4; i++) {
			NbtItemRecipe r = new NbtItemRecipe(MCMod.itemBoundHeart, new NbtIncreaseIntModifier(
				BoundHeart.NBT_TRANSFER_SPEED, i, i));
			r.addIngredient(Blocks.redstone_block, i);
			r.addIngredient(Blocks.hopper, i);
			r.addConditionForCrafting(new NbtMaxIntMatcher(BoundHeart.NBT_TRANSFER_SPEED, 20));
			addRecipe(r);
		}
	}
}
