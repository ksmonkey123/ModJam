package ch.modjam.generic.crafting;

import java.util.ArrayList;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.RecipeSorter;
import cpw.mods.fml.common.Loader;

/**
 * @author judos
 */
public abstract class RecipiesCrafting implements IRecipe {

	/**
	 * 
	 */
	protected ArrayList<NbtItemRecipe>	listShapeless	= new ArrayList<NbtItemRecipe>();

	/**
	 * 
	 */
	public RecipiesCrafting() {
		String modId = Loader.instance().activeModContainer().getModId();
		RecipeSorter.register(modId + ":shapeless", this.getClass(),
			RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
	}

	protected void addRecipe(NbtItemRecipe tileEntityRecipe) {
		this.listShapeless.add(tileEntityRecipe);
	}

	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		for (NbtItemRecipe r : listShapeless) {
			if (r.matches(inventory))
				return true;
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		for (NbtItemRecipe r : listShapeless) {
			if (r.matches(inventory))
				return r.getCraftingResult(inventory);
		}
		return null;
	}

	@Override
	public int getRecipeSize() {
		return 3;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

}
