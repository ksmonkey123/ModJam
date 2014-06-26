package ch.awae.trektech;

import ch.awae.trektech.entities.TileEntityPlasmaPipe;
import ch.awae.trektech.entities.renderer.TileEntityRendererBlockPipe;
import cpw.mods.fml.client.registry.ClientRegistry;

/**
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class ClientProxy extends CommonProxy {
    
    @Override
    public void registerRenderers() {
        TileEntityRendererBlockPipe rend = new TileEntityRendererBlockPipe();
        ClientRegistry.bindTileEntitySpecialRenderer(
                TileEntityPlasmaPipe.class, rend);
        
    }
    
}
