package ch.judos.at;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.ShapedOreRecipe;

import org.apache.logging.log4j.Logger;

import ch.judos.at.gearbox.BlockStationGearBox;
import ch.judos.at.gearbox.ItemGearBox;
import ch.judos.at.gearbox.TEStationGearbox;
import ch.judos.at.gondola.EntityGondola;
import ch.judos.at.gondola.ItemGondola;
import ch.judos.at.lib.ATNames;
import ch.judos.at.lib.CommonProxy;
import ch.judos.at.mast.BlockMast;
import ch.judos.at.mast.TEMast;
import ch.judos.at.rope.BlockRope;
import ch.judos.at.rope.ItemRope;
import ch.judos.at.station.BlockStation;
import ch.judos.at.station.TEStation;
import ch.judos.at.workinprogress.GondolaTargetMessage;
import ch.modjam.generic.helper.RegistryUtil;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * if you need to add dependencies, add the following in the @Mod() tag:<br>
 * dependencies="required-after:"modid"@["version"]"
 */
@Mod(modid = ATMain.MOD_ID, version = ATMain.VERSION, name = ATMain.NAME)
public class ATMain {

	/**
	 * public instance of this mod
	 */
	@Mod.Instance(ATMain.MOD_ID)
	public static ATMain				instance;

	/**
	 * proxy of the mod
	 */
	@SidedProxy(clientSide = ATMain.Client, serverSide = ATMain.Common)
	public static CommonProxy			proxy;
	/**
	 * used for logging
	 */
	public static Logger				logger;

	public static final String			MOD_ID	= "aerialtransports";
	public static final String			VERSION	= "1.7.10-0.11";
	public static final String			NAME	= "Aerial Transports";
	public static final String			Common	= "ch.judos.at.lib.CommonProxy";
	public static final String			Client	= "ch.judos.at.lib.ClientProxy";

	public static SimpleNetworkWrapper	network;

	public static CreativeTabs			modTab;
	public static BlockStation			station;
	public static BlockRope				ropeBlock;
	public static ItemRope				ropeOfStation;
	public static ItemGondola			gondola;
	public static ItemGearBox			gearbox;
	public static BlockStationGearBox	stationGearbox;

	public static BlockMast				mast;

	/**
	 * @param e
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		// Create and register all items
		logger = e.getModLog();
		setMetaData(e.getModMetadata());
		createCreativeTab();

		RegistryUtil.registerBlock(ropeBlock = new BlockRope());
		RegistryUtil.registerItem(ropeOfStation = new ItemRope());

		RegistryUtil.registerItem(gearbox = new ItemGearBox());
		RegistryUtil.registerBlock(stationGearbox = new BlockStationGearBox(),
			TEStationGearbox.class);

		RegistryUtil.registerItem(gondola = new ItemGondola());
		EntityRegistry.registerModEntity(EntityGondola.class, ATNames.gondola, 0, ATMain.instance,
			80, 1, true);

		RegistryUtil.registerBlock(station = new BlockStation(), TEStation.class);
		RegistryUtil.registerBlock(mast = new BlockMast(), TEMast.class);

		network = NetworkRegistry.INSTANCE.newSimpleChannel(ATMain.MOD_ID + "c1");
		network.registerMessage(GondolaTargetMessage.GondolaTargetHandler.class,
			GondolaTargetMessage.class, 0, Side.CLIENT);

		proxy.registerRenderInformation();
	}

	/**
	 * @param e
	 */
	@EventHandler
	public void init(FMLInitializationEvent e) {
		// add all recipies

		// gear box
		GameRegistry.addRecipe(new ShapedOreRecipe(gearbox, " W ", "WSW", " W ", 'W',
			Blocks.planks, 'S', Items.stick));

		// gondolas
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(gondola, 2), "G", "I", "B", 'G',
			ATMain.gearbox, 'I', Items.stick, 'B', Blocks.chest));

		// stations
		GameRegistry.addRecipe(new ShapedOreRecipe(station, "GGG", "WCW", "WCW", 'G', gearbox, 'W',
			Blocks.planks, 'C', Blocks.chest));

		// /station gearbox
		GameRegistry.addRecipe(new ShapedOreRecipe(stationGearbox, "WWW", "rGG", "WWW", 'W',
			Blocks.planks, 'r', Items.redstone, 'G', gearbox));
	}

	private static void setMetaData(ModMetadata m) {
		m.modId = ATMain.MOD_ID;
		m.name = EnumChatFormatting.GREEN + ATMain.NAME;
		m.description = "A couple of utilities to transport items easily through air.";
		m.version = ATMain.VERSION;
		m.authorList.add("judos (judos@gmx.ch)");
		m.credits = "The Forge and FML guys, for making the example mod, Lunatrius for the mcmod.info file, LexManos for answering multiple requests, diesieben07 for answering numerous questions in the IRC";
		m.autogenerated = false;
	}

	private static void createCreativeTab() {
		modTab = new CreativeTabs(ATMain.NAME) {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return ATMain.gondola;
			}

			@Override
			@SideOnly(Side.CLIENT)
			public String getTabLabel() {
				return "Aerial Transports";
			}

			@Override
			@SideOnly(Side.CLIENT)
			public String getTranslatedTabLabel() {
				return "Aerial Transports";
			}
		};
	}
}
