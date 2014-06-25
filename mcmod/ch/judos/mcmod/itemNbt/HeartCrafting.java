package ch.judos.mcmod.itemNbt;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/**
 * @author judos
 */
public class HeartCrafting implements IRecipe {

	@Override
	public boolean matches(InventoryCrafting var1, World var2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRecipeSize() {
		return 1;
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return null;
	}

}
