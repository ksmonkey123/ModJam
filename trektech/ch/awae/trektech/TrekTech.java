package ch.awae.trektech;

import test.BlockPlasmaTest;
import ch.awae.trektech.blocks.BlockDuraniumWall;
import ch.awae.trektech.blocks.BlockPlasmaPipe;
import ch.awae.trektech.entities.TileEntityPlasmaPipe;
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
import cpw.mods.fml.common.SidedProxy;
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
	public static Block blockPlasmaPipe = new BlockPlasmaPipe("plasmaPipe",
			EnumPlasmaTypes.NEUTRAL, 0, 4);
	public static Block blockPlasmaPipeEnergy = new BlockPlasmaPipe(
			"plasmaPipeEnergy", EnumPlasmaTypes.LOW, 1, 4);
	public static Block blockPlasmaPipeMediumEnergy = new BlockPlasmaPipe(
			"plasmaPipeMediumEnergy", EnumPlasmaTypes.MEDIUM, 3, 4);
	public static Block blockPlasmaPipeHighEnergy = new BlockPlasmaPipe(
			"plasmaPipeHighEnergy", EnumPlasmaTypes.HIGH, 4, 4);
	public static Block blockPlasmaPipeEncased = new BlockPlasmaPipe(
			"plasmaPipeEncased", EnumPlasmaTypes.NEUTRAL, 8, 8);
	public static Block blockPlasmaPipeEnergyEncased = new BlockPlasmaPipe(
			"plasmaPipeEnergyEncased", EnumPlasmaTypes.LOW, 9, 8);
	public static Block blockPlasmaPipeMediumEnergyEncased = new BlockPlasmaPipe(
			"plasmaPipeMediumEnergyEncased", EnumPlasmaTypes.MEDIUM, 11, 8);
	public static Block blockPlasmaPipeHighEnergyEncased = new BlockPlasmaPipe(
			"plasmaPipeHighEnergyEncased", EnumPlasmaTypes.HIGH, 12, 8);

	@SidedProxy(clientSide = "ch.awae.trektech.ClientProxy", serverSide = "ch.awae.trektech.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// ITEMS
		GameRegistry.registerItem(itemStarFleetSymbol, "symbolStarFleet");
		GameRegistry.registerItem(itemDuraniumIngot, "ingotDuranium");
		GameRegistry.registerItem(itemDilithiumRaw, "dilithiumRaw");
		GameRegistry.registerItem(itemDilithiumCrystal, "dilithiumCrystal");
		// BLOCKS
		GameRegistry.registerBlock(blockDuraniumWall, "duraniumWall");
		GameRegistry.registerBlock(blockPlasmaPipe, "plasmaPipe");
		GameRegistry.registerBlock(blockPlasmaPipeEnergy, "plasmaPipeEnergy");
		GameRegistry.registerBlock(blockPlasmaPipeMediumEnergy,
				"plasmaPipeMediumEnergy");
		GameRegistry.registerBlock(blockPlasmaPipeHighEnergy,
				"plasmaPipeHighEnergy");
		GameRegistry.registerBlock(blockPlasmaPipeEncased, "plasmaPipeEncased");
		GameRegistry.registerBlock(blockPlasmaPipeEnergyEncased,
				"plasmaPipeEnergyEncased");
		GameRegistry.registerBlock(blockPlasmaPipeMediumEnergyEncased,
				"plasmaPipeMediumEnergyEncased");
		GameRegistry.registerBlock(blockPlasmaPipeHighEnergyEncased,
				"plasmaPipeHighEnergyEncased");
		// ENTITIES
		GameRegistry.registerTileEntity(TileEntityPlasmaPipe.class,
				"tilePlasmaPipe");

		// RECIPES
		registerRecipes();
		proxy.registerRenderers();
	}

	private static void registerRecipes() {
		GameRegistry.addSmelting(Items.iron_ingot, new ItemStack(
				itemDuraniumIngot, 1), 0.5F); // TODO: Implement Real Recipe.
												// This is a placeholder
		GameRegistry.addSmelting(itemDilithiumRaw, new ItemStack(
				itemDilithiumCrystal, 1), 0.5F);
		GameRegistry.addShapedRecipe(new ItemStack(blockDuraniumWall, 4),
				"DDD", "DDD", 'D', new ItemStack(itemDuraniumIngot));
		ItemStack duraniumWallStack = new ItemStack(blockDuraniumWall);
		GameRegistry.addShapelessRecipe(new ItemStack(itemDuraniumIngot, 6),
				duraniumWallStack, duraniumWallStack, duraniumWallStack,
				duraniumWallStack);
	}

}
