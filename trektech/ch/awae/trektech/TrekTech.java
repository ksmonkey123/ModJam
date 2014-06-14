package ch.awae.trektech;

import java.util.Random;

import test.Test;
import ch.awae.trektech.blocks.BlockDuraniumWall;
import ch.awae.trektech.blocks.BlockPlasmaPipe;
import ch.awae.trektech.blocks.BlockPlasmaPipeCombined;
import ch.awae.trektech.blocks.BlockPlasmaSource;
import ch.awae.trektech.entities.TileEntityPlasmaPipe;
import ch.awae.trektech.entities.TileEntityPlasmaPipeCombined;
import ch.awae.trektech.entities.TileEntityPlasmaSource;
import ch.awae.trektech.items.DuraniumIngot;
import ch.awae.trektech.items.ItemDilithiumCrystal;
import ch.awae.trektech.items.ItemDilithiumRaw;
import ch.awae.trektech.items.ItemPlasmaContainmentRing;
import ch.awae.trektech.items.ItemStarFleetSymbol;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = TrekTech.MODID, version = TrekTech.VERSION)
public class TrekTech {

	public static final String MODID = "TrekTech";
	public static final String VERSION = "0.1";

	public static Random rand = new Random();

	public static CreativeTabs tabCustom = new CreativeTabs("tabTrekTech") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return TrekTech.itemStarFleetSymbol;
		}
	};

	@Instance("TrekTech")
	public static TrekTech instance;

	public static Item itemStarFleetSymbol = new ItemStarFleetSymbol();
	public static Item itemDuraniumIngot = new DuraniumIngot();
	public static Item itemDilithiumRaw = new ItemDilithiumRaw();
	public static Item itemDilithiumCrystal = new ItemDilithiumCrystal();
	public static Item itemPlasmaContainmentRing = new ItemPlasmaContainmentRing();

	public static Block blockDuraniumWall = new BlockDuraniumWall();
	public static Block blockPlasmaPipe = new BlockPlasmaPipe("plasmaPipe",
			EnumPlasmaTypes.NEUTRAL, "conduit", 4);
	public static Block blockPlasmaPipeEnergy = new BlockPlasmaPipe(
			"plasmaPipeEnergyA", EnumPlasmaTypes.LOW, "conduit_energy", 4);
	public static Block blockPlasmaPipeMediumEnergy = new BlockPlasmaPipe(
			"plasmaPipeEnergyB", EnumPlasmaTypes.MEDIUM,
			"conduit_medium_energy", 5);
	public static Block blockPlasmaPipeHighEnergy = new BlockPlasmaPipe(
			"plasmaPipeEnergyC", EnumPlasmaTypes.HIGH, "conduit_high_energy", 6);
	public static Block blockPlasmaPipeEncased = new BlockPlasmaPipe(
			"plasmaPipeEncased", EnumPlasmaTypes.NEUTRAL, "conduit_encased");
	public static Block blockPlasmaPipeEnergyEncased = new BlockPlasmaPipe(
			"plasmaPipeEncasedEnergyA", EnumPlasmaTypes.LOW,
			"conduit_encased_energy");
	public static Block blockPlasmaPipeMediumEnergyEncased = new BlockPlasmaPipe(
			"plasmaPipeEncasedEnergyB", EnumPlasmaTypes.MEDIUM,
			"conduit_encased_medium_energy");
	public static Block blockPlasmaPipeHighEnergyEncased = new BlockPlasmaPipe(
			"plasmaPipeEncasedEnergyC", EnumPlasmaTypes.HIGH,
			"conduit_encased_high_energy");
	public static Block blockPlasmaPipeCombined = new BlockPlasmaPipeCombined(
			false);
	public static Block blockPlasmaPipeCombinedEncased = new BlockPlasmaPipeCombined(
			true);
	public static Block blockPlasmaSource = new BlockPlasmaSource();

	@SidedProxy(clientSide = "ch.awae.trektech.ClientProxy", serverSide = "ch.awae.trektech.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLInitializationEvent event) {
		// ITEMS
		GameRegistry.registerItem(itemStarFleetSymbol, "symbolStarFleet");
		GameRegistry.registerItem(itemDuraniumIngot, "ingotDuranium");
		GameRegistry.registerItem(itemDilithiumRaw, "dilithiumRaw");
		GameRegistry.registerItem(itemDilithiumCrystal, "dilithiumCrystal");
		GameRegistry.registerItem(itemPlasmaContainmentRing,
				"plasmaContainmentRing");
		// BLOCKS
		GameRegistry.registerBlock(blockDuraniumWall, "duraniumWall");
		GameRegistry.registerBlock(blockPlasmaPipe, "plasmaPipe");
		GameRegistry.registerBlock(blockPlasmaPipeEnergy, "plasmaPipeEnergyA");
		GameRegistry.registerBlock(blockPlasmaPipeMediumEnergy,
				"plasmaPipeEnergyB");
		GameRegistry.registerBlock(blockPlasmaPipeHighEnergy,
				"plasmaPipeEnergyC");
		GameRegistry.registerBlock(blockPlasmaPipeEncased, "plasmaPipeEncased");
		GameRegistry.registerBlock(blockPlasmaPipeEnergyEncased,
				"plasmaPipeEncasedEnergyA");
		GameRegistry.registerBlock(blockPlasmaPipeMediumEnergyEncased,
				"plasmaPipeEncasedEnergyB");
		GameRegistry.registerBlock(blockPlasmaPipeHighEnergyEncased,
				"plasmaPipeEncasedEnergyC");
		GameRegistry.registerBlock(blockPlasmaPipeCombined,
				"plasmaPipeCombined");
		GameRegistry.registerBlock(blockPlasmaPipeCombinedEncased,
				"plasmaPipeCombinedEncased");
		GameRegistry.registerBlock(blockPlasmaSource, "plasmaSource");
		// ENTITIES
		GameRegistry.registerTileEntity(TileEntityPlasmaPipe.class,
				"tilePlasmaPipe");
		GameRegistry.registerTileEntity(TileEntityPlasmaPipeCombined.class,
				"tilePlasmaPipeCombined");
		GameRegistry.registerTileEntity(TileEntityPlasmaSource.class,
				"tilePlasmaSource");
		// RECIPES
		registerRecipes();
		proxy.registerRenderers();

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GUIHandler());
	}

	private static void registerRecipes() {
		GameRegistry.addSmelting(itemDilithiumRaw, new ItemStack(
				itemDilithiumCrystal, 1), 0.5F);
		GameRegistry.addShapedRecipe(new ItemStack(blockDuraniumWall, 4),
				"DDD", "DDD", 'D', new ItemStack(itemDuraniumIngot));
		ItemStack duraniumWallStack = new ItemStack(blockDuraniumWall);
		GameRegistry.addShapelessRecipe(new ItemStack(itemDuraniumIngot, 6),
				duraniumWallStack, duraniumWallStack, duraniumWallStack,
				duraniumWallStack);
		GameRegistry.addSmelting(Items.redstone, new ItemStack(
				itemPlasmaContainmentRing, 1), 0.5F);
		GameRegistry.addShapedRecipe(new ItemStack(blockPlasmaPipe, 6), "III",
				"CCC", "III", 'I', new ItemStack(Items.iron_ingot), 'C',
				new ItemStack(itemPlasmaContainmentRing));
		GameRegistry.addShapedRecipe(new ItemStack(blockPlasmaPipeEnergy, 6),
				"PPP", "CCC", "PPP", 'P', new ItemStack(blockPlasmaPipe), 'C',
				new ItemStack(itemPlasmaContainmentRing));

		ItemStack duraniumings = new ItemStack(itemDuraniumIngot);
		ItemStack mk1pipestack = new ItemStack(blockPlasmaPipeEnergy);
		ItemStack containments = new ItemStack(itemPlasmaContainmentRing);

		GameRegistry.addShapelessRecipe(new ItemStack(
				blockPlasmaPipeMediumEnergy, 3), duraniumings, duraniumings,
				duraniumings, containments, containments, containments,
				mk1pipestack, mk1pipestack, mk1pipestack);

		ItemStack mk2pipestack = new ItemStack(blockPlasmaPipeMediumEnergy);

		GameRegistry.addShapelessRecipe(new ItemStack(
				blockPlasmaPipeHighEnergy, 3), duraniumings, duraniumings,
				duraniumings, containments, containments, containments,
				mk2pipestack, mk2pipestack, mk2pipestack);

		GameRegistry.addShapelessRecipe(new ItemStack(blockPlasmaPipeCombined,
				1), new ItemStack(blockPlasmaPipe), new ItemStack(
				Items.iron_ingot), new ItemStack(blockPlasmaPipeEnergy));

		addEncasingRecipe(blockPlasmaPipeEncased, blockPlasmaPipe);
		addEncasingRecipe(blockPlasmaPipeEnergyEncased, blockPlasmaPipeEnergy);
		addEncasingRecipe(blockPlasmaPipeMediumEnergyEncased,
				blockPlasmaPipeMediumEnergy);
		addEncasingRecipe(blockPlasmaPipeHighEnergyEncased,
				blockPlasmaPipeHighEnergy);
		addEncasingRecipe(blockPlasmaPipeCombinedEncased,
				blockPlasmaPipeCombined);
	}

	private static void addEncasingRecipe(Block result, Block pipe) {
		GameRegistry.addShapedRecipe(new ItemStack(result, 4), " P ", "PDP",
				" P ", 'D', new ItemStack(blockDuraniumWall), 'P',
				new ItemStack(pipe));
	}

}
