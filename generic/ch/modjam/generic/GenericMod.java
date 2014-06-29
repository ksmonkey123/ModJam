package ch.modjam.generic;

import ch.modjam.generic.gui.GenericGuiHandler;
import ch.modjam.generic.networking.CommandMessage;
import ch.modjam.generic.networking.CommandMessageServerHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Generic Mod Class. Initializing the generic collection as a mod is required for networking.
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 * @author judos
 */
@Mod(modid = GenericMod.MODID, version = GenericMod.VERSION, name = GenericMod.NAME)
public class GenericMod {
	/**
	 * ModId
	 */
	public static final String			MODID	= "generic";
	/**
	 * Version identifier
	 */
	public static final String			VERSION	= "1.0";

	/**
	 * name
	 */
	public static final String			NAME	= "GenericMod";

	/**
	 * GenericMod Network Wrapper. Used for TileEntity updates
	 */
	public static SimpleNetworkWrapper	NETWORK;

	/**
	 * The GenericMod instance
	 */
	@Instance("generic")
	public static GenericMod			instance;

	/**
	 * PreInit
	 * 
	 * @param event
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GenericGuiHandler());
	}

	/**
	 * Initialize the mod. sets up the networking system
	 * 
	 * @param event
	 */
	@EventHandler
	public void init(FMLInitializationEvent event) {
		NETWORK = new SimpleNetworkWrapper("Generic");
		NETWORK.registerMessage(CommandMessageServerHandler.class, CommandMessage.class, 0,
			Side.SERVER);

	}
}