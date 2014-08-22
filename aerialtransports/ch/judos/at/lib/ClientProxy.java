package ch.judos.at.lib;

import ch.judos.at.station.StationRenderer;
import ch.judos.at.station.TEStation;
import cpw.mods.fml.client.registry.ClientRegistry;

@SuppressWarnings("javadoc")
public class ClientProxy extends CommonProxy {

	public static StationRenderer	stationRenderer;

	@Override
	public void registerRenderInformation() {

		stationRenderer = new StationRenderer();

		ClientRegistry.bindTileEntitySpecialRenderer(TEStation.class, stationRenderer);

		// MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MCMod.blockCarvedDirt),
		// new TECarvedDirtItemRenderer());

	}
}
