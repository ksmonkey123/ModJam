package ch.judos.mcmod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import ch.judos.mcmod.armor.ItemSlimyBoots;
import ch.judos.mcmod.customrender.BlockCarvedDirt;
import ch.judos.mcmod.customrender.TECarvedDirt;
import ch.judos.mcmod.handlers.FillBucketHandler;
import ch.judos.mcmod.handlers.FuelHandler;
import ch.judos.mcmod.itemblockfluids.BlockFluidTar;
import ch.judos.mcmod.itemblockfluids.BlockKryptoniteOre;
import ch.judos.mcmod.itemblockfluids.FluidTar;
import ch.judos.mcmod.itemblockfluids.ItemDirtShovel;
import ch.judos.mcmod.itemblockfluids.ItemKryptonite;
import ch.judos.mcmod.itemblockfluids.ItemObsidianStick;
import ch.judos.mcmod.itemblockfluids.ItemPotionTest;
import ch.judos.mcmod.itemblockfluids.ItemTarBucket;
import ch.judos.mcmod.lib.CommonProxy;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;
import ch.judos.mcmod.potions.OnEntityLivingHook;
import ch.judos.mcmod.potions.PotionTest;
import ch.judos.mcmod.world.WorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * if you need to add dependencies:<br>
 * dependencies="required-after:"modid"@["version"]"
 */
@SuppressWarnings("javadoc")
@Mod(modid = References.MOD_ID, version = References.VERSION)
public class TutorialMod {
	@SidedProxy(clientSide = References.Client, serverSide = References.Common)
	public static CommonProxy proxy;

	public static CreativeTabs modTab;

	// Item
	public static Item itemObsidianStick;

	// Tar Fluids
	public static Block tarBlock;
	public static Fluid tarFluid;
	public static Material materialTar;
	public static ItemTarBucket tarBucket;

	// Ores
	public static Block oreKryptonit;
	public static ItemKryptonite itemKryptonit;
	public static FuelHandler fuelHandler;

	// Custom rendered block
	public static Block blockCarvedDirt;
	public static Class<? extends TileEntity> teCarvedDirt;

	// Custom tool
	public static ItemDirtShovel itemDirtShovel;

	// Potions
	public static PotionTest potionTest;
	public static ItemPotionTest itemPotionTest;

	// Slimy boots
	public static int slimyBootsID;
	public static ArmorMaterial armorSlimeMaterial;
	public static Item itemSlimyBoots;

	/**
	 * @param e
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		System.out.println("PreInit MC Mod");
		createCreativeTab();
		createTarFluidAndBucket();
		addBoneRecipies();
		addObsidianStick();
		addKryptonitOre();
		addSmelting();
		addCarvedDirtCustomRenderingBlock();
		addDirtShovel();
		addPotion();
		addArmor();
		addBlockWithCustomGui();

		proxy.registerRenderInformation();
	}

	private void addBlockWithCustomGui() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("boxing")
	private static void addArmor() {
		armorSlimeMaterial = EnumHelper.addArmorMaterial("slimy", 5, new int[] {
				0, 0, 0, 2 }, 10);
		itemSlimyBoots = new ItemSlimyBoots();
		GameRegistry.registerItem(itemSlimyBoots, Names.SlimyBoots);
		MinecraftForge.EVENT_BUS.register(itemSlimyBoots);
		GameRegistry.addShapedRecipe(new ItemStack(itemSlimyBoots), "L L",
				"S S", 'L', Items.leather, 'S', Items.slime_ball);
		GameRegistry.addShapelessRecipe(new ItemStack(itemSlimyBoots),
				Items.leather_boots, Items.slime_ball, Items.slime_ball);
	}

	private static void addPotion() {
		// adds the effect of a custom potion to any living entity who currently
		// has the effect on it
		MinecraftForge.EVENT_BUS.register(new OnEntityLivingHook());
		potionTest = new PotionTest();
		itemPotionTest = new ItemPotionTest();
		GameRegistry.registerItem(itemPotionTest, Names.PotionTest);
	}

	@SuppressWarnings("boxing")
	private static void addDirtShovel() {
		itemDirtShovel = new ItemDirtShovel();
		GameRegistry.registerItem(itemDirtShovel, Names.DirtShovel);
		GameRegistry.addShapedRecipe(new ItemStack(itemDirtShovel, 2), "XX ",
				"XI ", "  I", 'X', Blocks.planks, 'I', Items.stick);
		GameRegistry.addShapedRecipe(new ItemStack(itemDirtShovel, 2), " XX",
				" IX", "  I", 'X', Blocks.planks, 'I', Items.stick);
	}

	private static void addCarvedDirtCustomRenderingBlock() {
		blockCarvedDirt = new BlockCarvedDirt();
		teCarvedDirt = TECarvedDirt.class;

		GameRegistry.registerBlock(blockCarvedDirt, Names.CarvedDirt);
		GameRegistry.registerTileEntity(teCarvedDirt, "tile_"
				+ Names.CarvedDirt);
	}

	private static void addSmelting() {
		GameRegistry.addSmelting(oreKryptonit, new ItemStack(itemKryptonit, 1),
				5);

		fuelHandler = new FuelHandler();
		fuelHandler.addFuel(itemKryptonit, 200 * 10);
		GameRegistry.registerFuelHandler(fuelHandler);

	}

	private static void addKryptonitOre() {
		oreKryptonit = new BlockKryptoniteOre();
		GameRegistry.registerWorldGenerator(new WorldGenerator(), 0);
		GameRegistry.registerBlock(oreKryptonit, Names.KryptonitBlock);

		itemKryptonit = new ItemKryptonite();
		GameRegistry.registerItem(itemKryptonit, Names.KryptonitItem);
	}

	private static void createTarFluidAndBucket() {
		tarFluid = new FluidTar();
		FluidRegistry.registerFluid(tarFluid);

		tarBlock = new BlockFluidTar(tarFluid);
		GameRegistry.registerBlock(tarBlock, Names.TarBlock);

		tarBucket = new ItemTarBucket(tarBlock);
		GameRegistry.registerItem(tarBucket, Names.TarBucket);

		int bucketVolume = FluidContainerRegistry.BUCKET_VOLUME;
		FluidStack fluidStack = FluidRegistry.getFluidStack(Names.TarFluid,
				bucketVolume);
		FluidContainerRegistry.registerFluidContainer(fluidStack,
				new ItemStack(tarBucket), new ItemStack(Items.bucket));
		FillBucketHandler.add(tarBlock, tarBucket);

		MinecraftForge.EVENT_BUS.register(FillBucketHandler.INSTANCE);
	}

	private static void createCreativeTab() {
		modTab = new CreativeTabs("MCMod") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return TutorialMod.itemObsidianStick;
			}

			@Override
			@SideOnly(Side.CLIENT)
			public String getTabLabel() {
				return "MCMod";
			}

			@Override
			@SideOnly(Side.CLIENT)
			public String getTranslatedTabLabel() {
				return "MCMod";
			}
		};
	}

	private static void addObsidianStick() {
		itemObsidianStick = new ItemObsidianStick();
		GameRegistry.registerItem(itemObsidianStick, Names.OStick);
	}

	@SuppressWarnings("boxing")
	private static void addBoneRecipies() {
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 1),
				Items.porkchop);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 1),
				Items.cooked_porkchop);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 1),
				Items.beef);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 1),
				Items.cooked_beef);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 1),
				Items.chicken, Items.chicken);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 1),
				Items.cooked_chicken, Items.cooked_chicken);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 1),
				Items.cooked_chicken, Items.chicken);

		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 3),
				Items.cooked_fished);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 3),
				Items.fish);
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.command_block), "Y",
				"X", 'X', Blocks.diamond_block, 'Y', Items.redstone);
	}

}
