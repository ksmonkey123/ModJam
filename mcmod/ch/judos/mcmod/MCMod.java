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
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.RecipeSorter;
import ch.judos.mcmod.armor.ItemSlimyBoots;
import ch.judos.mcmod.customrender.BlockCarvedDirt;
import ch.judos.mcmod.customrender.TECarvedDirt;
import ch.judos.mcmod.gui.Box;
import ch.judos.mcmod.gui.BoxItem;
import ch.judos.mcmod.gui.BoxTE;
import ch.judos.mcmod.gui.CustomBox;
import ch.judos.mcmod.gui.CustomBoxTE;
import ch.judos.mcmod.gui.GuiHandler;
import ch.judos.mcmod.handlers.FillBucketHandler;
import ch.judos.mcmod.handlers.FuelHandler;
import ch.judos.mcmod.itemNbt.BoundHeart;
import ch.judos.mcmod.itemNbt.HeartCrafting;
import ch.judos.mcmod.itemblockfluids.BlockFluidTar;
import ch.judos.mcmod.itemblockfluids.BlockKryptoniteOre;
import ch.judos.mcmod.itemblockfluids.FluidTar;
import ch.judos.mcmod.itemblockfluids.ItemDirtShovel;
import ch.judos.mcmod.itemblockfluids.ItemKryptonite;
import ch.judos.mcmod.itemblockfluids.ItemObsidianStick;
import ch.judos.mcmod.itemblockfluids.ItemPotionTest;
import ch.judos.mcmod.itemblockfluids.ItemTarBucket;
import ch.judos.mcmod.itemblockfluids.LivingFlesh;
import ch.judos.mcmod.lib.CommonProxy;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;
import ch.judos.mcmod.potions.OnEntityLivingHook;
import ch.judos.mcmod.potions.PotionTest;
import ch.judos.mcmod.world.WorldGenerator;
import ch.modjam.generic.RegistryUtil;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * if you need to add dependencies, add the following in the @Mod() tag:<br>
 * dependencies="required-after:"modid"@["version"]"
 */
@Mod(modid = References.MOD_ID, version = References.VERSION, name = References.NAME)
@SuppressWarnings("javadoc")
public class MCMod {

	/**
	 * public instance of this mod
	 */
	@Mod.Instance(References.MOD_ID)
	public static MCMod							instance;

	@SidedProxy(clientSide = References.Client, serverSide = References.Common)
	public static CommonProxy					proxy;

	public static CreativeTabs					modTab;

	// Item
	public static Item							itemObsidianStick;

	// Tar Fluids
	public static Block							tarBlock;
	public static Fluid							tarFluid;
	public static Material						materialTar;
	public static ItemTarBucket					tarBucket;

	// Ores
	public static Block							oreKryptonit;
	public static ItemKryptonite				itemKryptonit;
	public static FuelHandler					fuelHandler;

	// Custom rendered block
	public static Block							blockCarvedDirt;
	public static Class<? extends TileEntity>	teCarvedDirt;

	// Custom tool
	public static ItemDirtShovel				itemDirtShovel;

	// Potions
	public static PotionTest					potionTest;
	public static ItemPotionTest				itemPotionTest;

	// Slimy boots
	public static int							slimyBootsID;
	public static ArmorMaterial					armorSlimeMaterial;
	public static Item							itemSlimyBoots;

	// Custom Gui
	public static Box							box;				// the block
	public static CustomBox						customBox;

	// Items with NBT
	public static BoundHeart					itemBoundHeart;
	public static LivingFlesh					livingFlesh;

	/**
	 * @param e
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		setMetaData(e.getModMetadata());
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
		addItemWithNBTData();
		addLivingFleshItem();

		proxy.registerRenderInformation();
	}

	private void addLivingFleshItem() {
		livingFlesh = new LivingFlesh();
		RegistryUtil.registerItem(livingFlesh);
	}

	private void addItemWithNBTData() {
		this.itemBoundHeart = new BoundHeart();
		GameRegistry.registerItem(this.itemBoundHeart, Names.BoundHeart);
		GameRegistry.addShapelessRecipe(new ItemStack(this.itemBoundHeart), new ItemStack(
			Items.skull, 1, 3));

		// custom recipie to change NBT data on the item
		GameRegistry.addRecipe(new HeartCrafting());
		RecipeSorter.register("mcmod:shapeless", HeartCrafting.class,
			RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
	}

	private void setMetaData(ModMetadata m) {
		m.modId = References.MOD_ID;
		m.name = EnumChatFormatting.GREEN + References.NAME;
		m.description = "A scrambled assembly line of tutorial informational implementation content.";
		m.version = References.VERSION;
		m.authorList.add("judos (judos@gmx.ch)");
		m.credits = "The Forge and FML guys, for making the example mod, Lunatrius for the mcmod.info file, LexManos for answering multiple requests, diesieben07 for answering numerous questions in the IRC";
		m.autogenerated = false;
	}

	private static void addBlockWithCustomGui() {
		box = new Box();
		GameRegistry.registerBlock(box, Names.Box);
		GameRegistry.registerTileEntity(BoxTE.class, "tile_" + Names.Box);

		customBox = new CustomBox();
		GameRegistry.registerBlock(customBox, BoxItem.class, Names.CustomBox);
		GameRegistry.registerTileEntity(CustomBoxTE.class, "tile_" + Names.CustomBox);

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

	}

	private static void addArmor() {
		armorSlimeMaterial = EnumHelper.addArmorMaterial("slimy", 5, new int[] { 0, 0, 0, 2 }, 10);
		itemSlimyBoots = new ItemSlimyBoots();
		GameRegistry.registerItem(itemSlimyBoots, Names.SlimyBoots);
		MinecraftForge.EVENT_BUS.register(itemSlimyBoots);
		GameRegistry.addShapedRecipe(new ItemStack(itemSlimyBoots), "L L", "S S", 'L',
			Items.leather, 'S', Items.slime_ball);
		GameRegistry.addShapelessRecipe(new ItemStack(itemSlimyBoots), Items.leather_boots,
			Items.slime_ball, Items.slime_ball);
	}

	private static void addPotion() {
		// adds the effect of a custom potion to any living entity who currently
		// has the effect on it
		MinecraftForge.EVENT_BUS.register(new OnEntityLivingHook());
		potionTest = new PotionTest();
		itemPotionTest = new ItemPotionTest();
		RegistryUtil.registerItem(itemPotionTest);
	}

	private static void addDirtShovel() {
		itemDirtShovel = new ItemDirtShovel();
		GameRegistry.registerItem(itemDirtShovel, Names.DirtShovel);
		GameRegistry.addShapedRecipe(new ItemStack(itemDirtShovel, 2), "XX ", "XI ", "  I", 'X',
			Blocks.planks, 'I', Items.stick);
		GameRegistry.addShapedRecipe(new ItemStack(itemDirtShovel, 2), " XX", " IX", "  I", 'X',
			Blocks.planks, 'I', Items.stick);
	}

	private static void addCarvedDirtCustomRenderingBlock() {
		blockCarvedDirt = new BlockCarvedDirt();
		teCarvedDirt = TECarvedDirt.class;

		GameRegistry.registerBlock(blockCarvedDirt, Names.CarvedDirt);
		GameRegistry.registerTileEntity(teCarvedDirt, "tile_" + Names.CarvedDirt);
	}

	private static void addSmelting() {
		GameRegistry.addSmelting(oreKryptonit, new ItemStack(itemKryptonit, 1), 5);

		fuelHandler = new FuelHandler();
		fuelHandler.addFuel(itemKryptonit, 10000);
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
		FluidStack fluidStack = FluidRegistry.getFluidStack(Names.TarFluid, bucketVolume);
		FluidContainerRegistry.registerFluidContainer(fluidStack, new ItemStack(tarBucket),
			new ItemStack(Items.bucket));
		FillBucketHandler.add(tarBlock, tarBucket);

		MinecraftForge.EVENT_BUS.register(FillBucketHandler.INSTANCE);
	}

	private static void createCreativeTab() {
		modTab = new CreativeTabs("MCMod") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return MCMod.itemObsidianStick;
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

	private static void addBoneRecipies() {
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 1), Items.porkchop);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 1), Items.cooked_porkchop);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 1), Items.beef);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 1), Items.cooked_beef);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 1), Items.chicken, Items.chicken);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 1), Items.cooked_chicken,
			Items.cooked_chicken);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 1), Items.cooked_chicken,
			Items.chicken);

		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 3), Items.cooked_fished);
		GameRegistry.addShapelessRecipe(new ItemStack(Items.bone, 3), Items.fish);
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.command_block), "Y", "X", 'X',
			Blocks.diamond_block, 'Y', Items.redstone);
	}

}
