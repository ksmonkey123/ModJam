package ch.awae.trektech;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumPlantType;
import ch.awae.trektech.blocks.BlockDuraniumWall;
import ch.awae.trektech.blocks.BlockPlasmaPipe;
import ch.awae.trektech.blocks.BlockPlasmaPressureValve;
import ch.awae.trektech.blocks.BlockPlasmaSource;
import ch.awae.trektech.entities.TileEntityPlasmaPipe;
import ch.awae.trektech.entities.TileEntityPlasmaPipeCombined;
import ch.awae.trektech.entities.TileEntityPlasmaPressureValve;
import ch.awae.trektech.entities.TileEntityPlasmaSource;
import ch.awae.trektech.items.ItemDuraniumIngot;
import ch.awae.trektech.items.ItemDilithiumCrystal;
import ch.awae.trektech.items.ItemDilithiumRaw;
import ch.awae.trektech.items.ItemPlasmaContainmentRing;
import ch.awae.trektech.items.ItemStarFleetSymbol;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * TrekTech Core Class
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
@SuppressWarnings("javadoc")
@Mod(modid = TrekTech.MODID, version = TrekTech.VERSION)
public class TrekTech {

	// -- MOD DATA --
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
	public static Item itemDuraniumIngot = new ItemDuraniumIngot();
	public static Item itemDilithiumRaw = new ItemDilithiumRaw();
	public static Item itemDilithiumCrystal = new ItemDilithiumCrystal();
	public static Item itemPlasmaContainmentRing = new ItemPlasmaContainmentRing();

	public static Block blockDuraniumWall = new BlockDuraniumWall();
	public static Block blockPlasmaSource = new BlockPlasmaSource();
	public static Block blockPlasmaValve = new BlockPlasmaPressureValve(
			"valve1", EnumPlasmaTypes.NEUTRAL);

	public static Block[][] pipes = new Block[EnumPlantType.values().length + 1][2];

	@SidedProxy(clientSide = "ch.awae.trektech.ClientProxy", serverSide = "ch.awae.trektech.CommonProxy")
	public static CommonProxy proxy;

	/**
	 * @param event
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		setMetadata(event.getModMetadata());
		// ITEMS
		GameRegistry.registerItem(itemStarFleetSymbol, "symbolStarFleet");
		GameRegistry.registerItem(itemDuraniumIngot, "ingotDuranium");
		GameRegistry.registerItem(itemDilithiumRaw, "dilithiumRaw");
		GameRegistry.registerItem(itemDilithiumCrystal, "dilithiumCrystal");
		GameRegistry.registerItem(itemPlasmaContainmentRing,
				"plasmaContainmentRing");
		// BLOCKS
		GameRegistry.registerBlock(blockDuraniumWall, "duraniumWall");

		EnumPlasmaTypes plasmaTypes[] = EnumPlasmaTypes.values();
		for (int i = 0; i <= plasmaTypes.length; i++) {
			if (i == plasmaTypes.length) {
				// FIXME: possible index error?
			} else {
                // TODO: retrieve radius from plasma type
				pipes[i][0] = new BlockPlasmaPipe("pipe" + i, plasmaTypes[i],
						i == 0 ? 6 : Math.min(Math.max(4, i + 2), 8));
				pipes[i][1] = new BlockPlasmaPipe("pipe" + i + "c",
						plasmaTypes[i]);
				GameRegistry.registerBlock(pipes[i][0], "pipe" + i);
				GameRegistry.registerBlock(pipes[i][1], "pipe" + i + "c");
				addEncasingRecipe(i);
				// TODO: dynamically create valves
			}
		}

		GameRegistry.registerBlock(blockPlasmaSource, "plasmaSource");
		GameRegistry.registerBlock(blockPlasmaValve, "valve1"); // temporary
		// ENTITIES
		GameRegistry.registerTileEntity(TileEntityPlasmaPipe.class,
				"tilePlasmaPipe");
		GameRegistry.registerTileEntity(TileEntityPlasmaPipeCombined.class,
				"tilePlasmaPipeCombined");
		GameRegistry.registerTileEntity(TileEntityPlasmaSource.class,
				"tilePlasmaSource");
		GameRegistry.registerTileEntity(TileEntityPlasmaPressureValve.class,
				"tilePlasmaValve");
		// RECIPES
		registerRecipes();
		proxy.registerRenderers();

		Properties.WRENCH = Items.diamond;

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GUIHandler());
	}

	@SuppressWarnings("boxing")
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
		GameRegistry.addShapedRecipe(new ItemStack(pipes[1][0], 6), "III",
				"CCC", "III", 'I', new ItemStack(Items.iron_ingot), 'C',
				new ItemStack(itemPlasmaContainmentRing));
		GameRegistry.addShapedRecipe(new ItemStack(pipes[2][0], 6), "PPP",
				"CCC", "PPP", 'P', new ItemStack(pipes[1][0]), 'C',
				new ItemStack(itemPlasmaContainmentRing));

		ItemStack duraniumings = new ItemStack(itemDuraniumIngot);
		ItemStack mk1pipestack = new ItemStack(pipes[2][0]);
		ItemStack containments = new ItemStack(itemPlasmaContainmentRing);

		GameRegistry.addShapelessRecipe(new ItemStack(pipes[3][0], 3),
				duraniumings, duraniumings, duraniumings, containments,
				containments, containments, mk1pipestack, mk1pipestack,
				mk1pipestack);

		ItemStack mk2pipestack = new ItemStack(pipes[3][0]);

		GameRegistry.addShapelessRecipe(new ItemStack(pipes[4][0], 3),
				duraniumings, duraniumings, duraniumings, containments,
				containments, containments, mk2pipestack, mk2pipestack,
				mk2pipestack);

		GameRegistry.addShapelessRecipe(new ItemStack(pipes[0][0], 1),
				new ItemStack(pipes[1][0]), new ItemStack(Items.iron_ingot),
				new ItemStack(pipes[2][0]));
	}

	@SuppressWarnings("boxing")
	private static void addEncasingRecipe(int pipeID) {
		GameRegistry.addShapedRecipe(new ItemStack(pipes[pipeID][1], 4), " P ",
				"PDP", " P ", 'D', new ItemStack(blockDuraniumWall), 'P',
				new ItemStack(pipes[pipeID][0]));
	}

	private static void setMetadata(ModMetadata meta) {
		meta.autogenerated = false;
		meta.authorList.add("Andreas Waelchli (andreas.waelchli@me.com)");
		meta.name = "Trek Tech";
		meta.version = VERSION;
		meta.description = "Star Trek Technologies";
	}
}
