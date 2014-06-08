package ch.awae.trektech;

import ch.awae.trektech.entities.TileEntityPlasmaPipe;
import ch.awae.trektech.entities.renderer.TileEntityRendererBlockPipe;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlasmaPipe.class, new TileEntityRendererBlockPipe(TileEntityPlasmaPipe.TEXTURE_ID, TileEntityPlasmaPipe.WIDTH));
	}
	
}
