package test;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = Test.MODID, version = Test.VERSION, name = Test.MODID)
public class Test {

	public static final String MODID = "Test";
	public static final String VERSION = "0.1";

	public static CreativeTabs tabCustom = new CreativeTabs("tabTest") {

		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Items.diamond;
		}
	};

	@Instance("Test")
	public static Test instance;

	public static Block blockTestInventory = new BlockTestInventory();

	// public static Block blockPlasmaTest = new BlockPlasmaTest();
	// public static Block blockPersTest = new BlockTestTileEntityPersistance();
	// public static Block blockTestSource = new BlockPlasmaSource();
	public static Block blockTestDrain = new BlockPlasmaDrain();

	@EventHandler
	public void preInit(FMLInitializationEvent event) {
		GameRegistry.registerBlock(blockTestInventory, "testInventory");
		// GameRegistry.registerBlock(blockPlasmaTest, "plasmaTest");
		// GameRegistry.registerTileEntity(TileEntityTestInventory.class,"testEnt");
		// GameRegistry.registerTileEntity(TileEntityPlasma.class, "plasmaEnt");
		// GameRegistry.registerBlock(blockPersTest, "persTest");
		// GameRegistry.registerTileEntity(TileEntityPersistanceTest.class,"persTester");
		// GameRegistry.registerBlock(blockTestSource, "plasmaTestSource");
		// GameRegistry.registerTileEntity(TileEntityPlasmaSource.class,
		// "testSource");
		GameRegistry.registerBlock(blockTestDrain, "testPlasmaDrain");
		GameRegistry.registerTileEntity(TileEntityPlasmaSource.class,
				"testDrain");
		// NetworkRegistry.INSTANCE.registerGuiHandler(this, new GUIHandler());
	}
}
