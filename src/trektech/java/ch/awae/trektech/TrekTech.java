package ch.awae.trektech;

import ch.awae.trektech.blocks.BlockDuraniumWall;
import ch.awae.trektech.items.DuraniumIngot;
import ch.awae.trektech.items.ItemDilithiumCrystal;
import ch.awae.trektech.items.ItemDilithiumRaw;
import ch.awae.trektech.items.ItemStarFleetSymbol;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = TrekTech.MODID, version = TrekTech.VERSION)
public class TrekTech {

	public static final String MODID = "TrekTech";
	public static final String VERSION = "0.1";

	public static CreativeTabs tabCustom = new CreativeTabs("tabTrekTech") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return TrekTech.itemStarFleetSymbol;
		}
	};

	public static Item itemStarFleetSymbol = new ItemStarFleetSymbol();
	public static Item itemDuraniumIngot = new DuraniumIngot();
	public static Item itemDilithiumRaw = new ItemDilithiumRaw();
	public static Item itemDilithiumCrystal = new ItemDilithiumCrystal();

	public static Block blockDuraniumWall = new BlockDuraniumWall();

	@EventHandler
	public void init(FMLInitializationEvent event) {
		GameRegistry.registerItem(itemStarFleetSymbol, "symbolStarFleet");
		GameRegistry.registerItem(itemDuraniumIngot, "ingotDuranium");
		GameRegistry.registerItem(itemDilithiumRaw, "dilithiumRaw");
		GameRegistry.registerItem(itemDilithiumCrystal, "dilithiumCrystal");

		GameRegistry.registerBlock(blockDuraniumWall, "duraniumWall");

		registerRecipes();
	}

	private static void registerRecipes() {
		GameRegistry.addSmelting(Items.iron_ingot, new ItemStack(itemDuraniumIngot, 1), 0.5F); // TODO: Implement Real Recipe. This is a placeholder
		GameRegistry.addSmelting(itemDilithiumRaw, new ItemStack(itemDilithiumCrystal, 1), 0.5F);
	}

}
