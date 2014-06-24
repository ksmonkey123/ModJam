package ch.modjam.generic;

import test.TestMessage;
import test.TestMessageServerHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * Generic Mod Class. Initializing the generic collection as a mod is required
 * for networking.
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
@Mod(modid = GenericMod.MODID, version = GenericMod.VERSION)
public class GenericMod {
	/**
	 * ModId
	 */
	public static final String MODID = "generic";
	/**
	 * Version identifier
	 */
	public static final String VERSION = "1.0";

	/**
	 * GenericMod Network Wrapper. Used for TileEntity updates
	 */
	public static SimpleNetworkWrapper NETWORK;

	/**
	 * Initialize the mod. sets up the networking system
	 * 
	 * @param event
	 */
	@EventHandler
	public void init(FMLInitializationEvent event) {
		NETWORK = new SimpleNetworkWrapper("Generic");
		if (FMLCommonHandler.instance().getSide() == Side.SERVER)
		NETWORK.registerMessage(TestMessageServerHandler.class,
				TestMessage.class, 0, Side.SERVER);
		//else
			
	}
}