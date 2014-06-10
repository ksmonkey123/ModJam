package testj.lib;

import testj.TutorialMod;
import testj.customrender.TECarvedDirtRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy{
	
	public void registerRenderInformation() {
		TECarvedDirtRenderer r = new TECarvedDirtRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TutorialMod.teCarvedDirt, r);
	}
}
