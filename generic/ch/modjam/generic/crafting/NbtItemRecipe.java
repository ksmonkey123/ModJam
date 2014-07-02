package ch.modjam.generic.crafting;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author j
 *
 */
public class NbtItemRecipe {

	private ArrayList<ItemStack>		ingredients;
	private NbtModifier					nbtModifier;
	private Item						nbtItem;
	private ArrayList<NbtValueMatcher>	conditions;

	/**
	 * @param nbtItem
	 * @param nbtModifier
	 * 
	 */
	public NbtItemRecipe(Item nbtItem, NbtModifier nbtModifier) {
		this.ingredients = new ArrayList<ItemStack>();
		this.nbtItem = nbtItem;
		this.nbtModifier = nbtModifier;
		this.conditions = new ArrayList<NbtValueMatcher>();
	}

	/**
	 * @param inventory
	 * @return true if the recipe is valid for this inventory
	 */
	public boolean matches(InventoryCrafting inventory) {
		if (getItemStackFor(inventory, this.nbtItem) == null)
			return false;
		for (NbtValueMatcher condition : this.conditions)
			if (!condition.matchesNBT(getItemStackFor(inventory, this.nbtItem).stackTagCompound))
				return false;
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
		ItemStack i = getItemStackFor(inventory, this.nbtItem).copy();
		this.nbtModifier.modifyNBT(i.stackTagCompound);
		return i;
	}

	protected boolean isItemPresent(InventoryCrafting inventory, Item item) {
		return isItemPresent(inventory, new ItemStack(item));
	}

	protected boolean isItemPresent(InventoryCrafting inventory, ItemStack item) {
		int trueAmount = item.stackSize;
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack xxx = inventory.getStackInSlot(i);
			if (xxx != null && xxx.isItemEqual(item))
				trueAmount--;
		}
		return trueAmount == 0;
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
		addIngredient(block, 1);
	}

	/**
	 * @param block
	 * @param amount
	 */
	public void addIngredient(Block block, int amount) {
		addIngredient(Item.getItemFromBlock(block), amount);
	}

	/**
	 * @param item
	 */
	public void addIngredient(Item item) {
		addIngredient(item, 1);
	}

	/**
	 * @param item
	 * @param amount
	 */
	public void addIngredient(Item item, int amount) {
		this.ingredients.add(new ItemStack(item, amount));
	}

	/**
	 * @param matcher
	 */
	public void addConditionForCrafting(NbtValueMatcher matcher) {
		this.conditions.add(matcher);
	}
}
