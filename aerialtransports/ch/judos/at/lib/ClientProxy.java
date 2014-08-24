package ch.judos.at.lib;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import ch.judos.at.ATMain;
import ch.judos.at.station.ItemRendererStation;
import ch.judos.at.station.RendererStation;
import ch.judos.at.station.TEStation;
import ch.judos.at.station.gondola.EntityGondola;
import ch.judos.at.station.gondola.ItemRendererGondola;
import ch.judos.at.station.gondola.RendererGondola;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

@SuppressWarnings("javadoc")
public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderInformation() {

		// Station:
		ClientRegistry.bindTileEntitySpecialRenderer(TEStation.class, new RendererStation());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ATMain.station),
			new ItemRendererStation());

		// Gondolas:
		RenderingRegistry
			.registerEntityRenderingHandler(EntityGondola.class, new RendererGondola());
		MinecraftForgeClient.registerItemRenderer(ATMain.gondola, new ItemRendererGondola());

	}

}
