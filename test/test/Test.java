package test;

import net.minecraft.block.Block;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@SuppressWarnings("javadoc")
@Mod(modid = Test.MODID, version = Test.VERSION)
public class Test {
	public static final String MODID = "examplemod";
	public static final String VERSION = "1.0";

	public static SimpleNetworkWrapper NETWORK;

	public static Block packetTest = new BlockPacketTest();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		GameRegistry.registerBlock(packetTest, "packetTest");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		NETWORK = new SimpleNetworkWrapper("Generic");
		NETWORK.registerMessage(TestMessageServerHandler.class,
				TestMessage.class, 0, Side.SERVER);
	}

}
