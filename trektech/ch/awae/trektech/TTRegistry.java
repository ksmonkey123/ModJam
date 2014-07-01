package ch.awae.trektech;

import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@SuppressWarnings("javadoc")
public class TTRegistry {
    
    private static HashMap<Item, ItemStack> plasmaFurnaceRecipes = new HashMap<Item, ItemStack>();
    
    public static void registerAdditionalFurnaceRecipe(Item input,
            ItemStack output) {
        TTRegistry.plasmaFurnaceRecipes.put(input, output);
    }
    
    public static ItemStack getAdditionalFurnaceRecipe(Item input) {
        return TTRegistry.plasmaFurnaceRecipes.get(input);
    }
    
}
