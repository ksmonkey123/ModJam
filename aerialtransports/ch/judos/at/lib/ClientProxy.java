package ch.judos.at.lib;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import ch.judos.at.ATMain;
import ch.judos.at.gondola.EntityGondola;
import ch.judos.at.gondola.ItemRendererGondola;
import ch.judos.at.gondola.RendererGondola;
import ch.judos.at.mast.RendererMast;
import ch.judos.at.mast.TEMast;
import ch.judos.at.station.ItemRendererStation;
import ch.judos.at.station.RendererStation;
import ch.judos.at.station.TEStation;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderInformation() {

		// Station:
		ClientRegistry.bindTileEntitySpecialRenderer(TEStation.class, new RendererStation());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ATMain.station),
			new ItemRendererStation());

		// Mast:
		ClientRegistry.bindTileEntitySpecialRenderer(TEMast.class, new RendererMast());

		// Gondolas:
		RenderingRegistry
			.registerEntityRenderingHandler(EntityGondola.class, new RendererGondola());
		MinecraftForgeClient.registerItemRenderer(ATMain.gondola, new ItemRendererGondola());

	}

}
