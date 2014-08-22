package ch.judos.at;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumChatFormatting;

import org.apache.logging.log4j.Logger;

import ch.judos.at.blocks.BlockStation;
import ch.judos.at.items.ItemRope;
import ch.judos.at.lib.CommonProxy;
import ch.judos.at.te.TEStation;
import ch.modjam.generic.RegistryUtil;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * if you need to add dependencies, add the following in the @Mod() tag:<br>
 * dependencies="required-after:"modid"@["version"]"
 */
@Mod(modid = ATMain.MOD_ID, version = ATMain.VERSION, name = ATMain.NAME)
@SuppressWarnings("javadoc")
public class ATMain {

	/**
	 * public instance of this mod
	 */
	@Mod.Instance(ATMain.MOD_ID)
	public static ATMain		instance;

	@SidedProxy(clientSide = ATMain.Client, serverSide = ATMain.Common)
	public static CommonProxy	proxy;

	public static CreativeTabs	modTab;

	public static BlockStation		station;

	public static Logger		logger;

	public static ItemRope	ropeOfStation;

	public static final String	MOD_ID	= "aerialtransports";

	public static final String	VERSION	= "1.7.10-0.11";

	public static final String	NAME	= "Aerial Transports";

	public static final String	Common	= "ch.judos.at.lib.CommonProxy";

	public static final String	Client	= "ch.judos.at.lib.ClientProxy";

	/**
	 * @param e
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		logger = e.getModLog();
		setMetaData(e.getModMetadata());
		createCreativeTab();

		registerRopeOfStationItem();
		registerStation();

		proxy.registerRenderInformation();
	}

	private static void registerRopeOfStationItem() {
		ropeOfStation = new ItemRope();
		RegistryUtil.registerItem(ropeOfStation);
	}

	private static void registerStation() {
		station = new BlockStation();
		RegistryUtil.registerBlock(station, TEStation.class);
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
				return new ItemBlock(ATMain.station);
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