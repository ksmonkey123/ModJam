package ch.modjam.generic;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/**
 * @author judos
 */
public abstract class RecipiesCrafting implements IRecipe {

	@Override
	public abstract boolean matches(InventoryCrafting inventory, World world);

	@Override
	public abstract ItemStack getCraftingResult(InventoryCrafting inventory);

	@Override
	public abstract int getRecipeSize();

	@Override
	public abstract ItemStack getRecipeOutput();

	protected boolean isItemPresent(InventoryCrafting inventory, Item item) {
		return isItemPresent(inventory, new ItemStack(item));
	}

	protected boolean isItemPresent(InventoryCrafting inventory, ItemStack item) {
		return isItemPresent(inventory, item, 1);
	}

	protected boolean isItemPresent(InventoryCrafting inventory, ItemStack item, int amountNeeded) {
		int trueAmount = amountNeeded;
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack xxx = inventory.getStackInSlot(i);
			if (xxx != null && xxx.isItemEqual(item))
				trueAmount--;
			if (trueAmount <= 0)
				return true;
		}
		return false;
	}

	protected ItemStack getItemStackFor(InventoryCrafting inventory, Item item) {
		return getItemStackFor(inventory, new ItemStack(item));
	}

	protected ItemStack getItemStackFor(InventoryCrafting inventory, ItemStack item) {
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack xxx = inventory.getStackInSlot(i);
			if (xxx != null && xxx.isItemEqual(item))
				return inventory.getStackInSlot(i);
		}
		return null;
	}
}
