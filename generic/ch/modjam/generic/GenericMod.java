package ch.modjam.generic;

import ch.modjam.generic.networking.CommandMessage;
import ch.modjam.generic.networking.CommandMessageServerHandler;
import ch.modjam.generic.networking.NBTMessage;
import ch.modjam.generic.networking.NBTMessageClientHandler;
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
			NETWORK.registerMessage(CommandMessageServerHandler.class,
					CommandMessage.class, 0, Side.SERVER);
		else
			NETWORK.registerMessage(NBTMessageClientHandler.class,
					NBTMessage.class, 0, Side.CLIENT);

	}
}