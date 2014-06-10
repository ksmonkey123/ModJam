package testj;

import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import testj.customrender.BlockCarvedDirt;
import testj.customrender.TECarvedDirt;
import testj.handlers.FillBucketHandler;
import testj.handlers.FuelHandler;
import testj.itemblockfluids.ItemKryptonite;
import testj.itemblockfluids.BlockKryptoniteOre;
import testj.itemblockfluids.ItemObsidianStick;
import testj.itemblockfluids.BlockFluidTar;
import testj.itemblockfluids.ItemTarBucket;
import testj.itemblockfluids.FluidTar;
import testj.lib.Names;
import testj.lib.CommonProxy;
import testj.lib.References;
import testj.world.WorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = References.MOD_ID, version = References.VERSION)
public class TutorialMod {
	@SidedProxy(clientSide = References.Client, serverSide = References.Common)
	public static CommonProxy proxy;

	// Item
	public static Item obsidianStick = new ItemObsidianStick();

	// Tar Fluids
	public static Block tarBlock;
	public static Fluid tarFluid;
	public static Material materialTar;
	public static ItemTarBucket tarBucket;
	
	// Ores
	public static Block oreKryptonit=new BlockKryptoniteOre();
	
	// Custom rendered block
	public static Block blockCarvedDirt = new BlockCarvedDirt();
	public static Class<? extends TileEntity> teCarvedDirt = TECarvedDirt.class;
	
	public static CreativeTabs modTab;

	private ItemKryptonite itemKryptonit;

	private FuelHandler fuelHandler;

	

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) throws IOException {
		createCreativeTab();
		createTarFluidAndBucket();
		addBoneRecipies();
		addObsidianStick();
		addKryptonitOre();
		addSmelting();
		
		addCarvedDirtCustomRenderingBlock();
		proxy.registerRenderInformation();
	}

	private void addCarvedDirtCustomRenderingBlock() {
		GameRegistry.registerBlock(this.blockCarvedDirt,Names.CarvedDirt);
		GameRegistry.registerTileEntity(this.teCarvedDirt,"tile_"+Names.CarvedDirt);
	}

	private void addSmelting() {
		GameRegistry.addSmelting(this.oreKryptonit, new ItemStack(this.itemKryptonit,1), 5);
		
		
		this.fuelHandler = new FuelHandler();
		this.fuelHandler.addFuel(this.itemKryptonit, 200*10);
		GameRegistry.registerFuelHandler(this.fuelHandler);
		
	}

	private void addKryptonitOre() {
		GameRegistry.registerWorldGenerator(new WorldGenerator(), 0);
		GameRegistry.registerBlock(this.oreKryptonit, Names.KryptonitBlock);
		
		this.itemKryptonit = new ItemKryptonite();
		GameRegistry.registerItem(this.itemKryptonit, Names.KryptonitItem);
	}

	private void createTarFluidAndBucket() {
		this.tarFluid = new FluidTar();
		FluidRegistry.registerFluid(this.tarFluid);

		this.tarBlock = new BlockFluidTar(this.tarFluid);
		GameRegistry.registerBlock(this.tarBlock, Names.TarBlock);

		this.tarBucket = new ItemTarBucket(this.tarBlock);
		GameRegistry.registerItem(this.tarBucket, Names.TarBucket);

		int bucketVolume = FluidContainerRegistry.BUCKET_VOLUME;
		FluidStack fluidStack = FluidRegistry.getFluidStack(Names.TarFluid,
				bucketVolume);
		FluidContainerRegistry.registerFluidContainer(fluidStack,
				new ItemStack(this.tarBucket), new ItemStack(Items.bucket));
		FillBucketHandler.add(tarBlock, tarBucket);

		MinecraftForge.EVENT_BUS.register(FillBucketHandler.INSTANCE);
	}

	private void createCreativeTab() {
		this.modTab = new CreativeTabs("MCMod") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return TutorialMod.obsidianStick;
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

	private void addObsidianStick() {
		GameRegistry.registerItem(this.obsidianStick, Names.OStick);
	}

	private void addBoneRecipies() {
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
