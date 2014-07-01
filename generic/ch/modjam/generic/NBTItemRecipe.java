package ch.modjam.generic;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ch.modjam.generic.crafting.NBTModifier;

/**
 * @author j
 *
 */
public class NBTItemRecipe {

	private ArrayList<ItemStack>	ingredients;
	private NBTModifier				nbtModifier;
	private Item					nbtItem;

	/**
	 * @param nbtItem
	 * @param nbtModifier
	 * 
	 */
	public NBTItemRecipe(Item nbtItem, NBTModifier nbtModifier) {
		this.ingredients = new ArrayList<ItemStack>();
		this.nbtItem = nbtItem;
		this.nbtModifier = nbtModifier;
	}

	/**
	 * @param inventory
	 * @return true if the recipe is valid for this inventory
	 */
	public boolean matches(InventoryCrafting inventory) {
		for (ItemStack i : this.ingredients)
			if (!isItemPresent(inventory, i))
				return false;
		return true;
	}

	/**
	 * @param inventory
	 * @return the result of this crafting
	 */
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		ItemStack i = getItemStackFor(inventory, nbtItem).copy();
		this.nbtModifier.modifyNBT(i.stackTagCompound);
		return i;
	}

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

	/**
	 * @param itemStack
	 */
	public void addIngredient(ItemStack itemStack) {
		this.ingredients.add(itemStack);
	}

	/**
	 * @param block
	 */
	public void addIngredient(Block block) {
		addIngredient(Item.getItemFromBlock(block));
	}

	/**
	 * @param item
	 */
	public void addIngredient(Item item) {
		this.ingredients.add(new ItemStack(item));
	}
}
