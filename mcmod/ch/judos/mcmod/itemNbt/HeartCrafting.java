package ch.judos.mcmod.itemNbt;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ch.judos.mcmod.MCMod;
import ch.modjam.generic.RecipiesCrafting;

/**
 * @author judos
 * 
 */
public class HeartCrafting extends RecipiesCrafting {

	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		return isItemPresent(inventory, new ItemStack(MCMod.boundHeart)) && isItemPresent(
			inventory, Items.diamond_sword);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		ItemStack heart = getItemStackFor(inventory, MCMod.boundHeart).copy();
		int up = heart.stackTagCompound.getInteger("upgrade") + 1;
		heart.stackTagCompound.setInteger("upgrade", up);
		return heart;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null; // return new ItemStack(MCMod.boundHeart);
	}

}
