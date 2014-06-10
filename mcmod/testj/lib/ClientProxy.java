package testj.lib;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import testj.TutorialMod;
import testj.customrender.TECarvedDirtItemRenderer;
import testj.customrender.TECarvedDirtRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	
	public static TECarvedDirtRenderer renderer = new TECarvedDirtRenderer();
	
	public void registerRenderInformation() {
//		TECarvedDirtRenderer renderer = new TECarvedDirtRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TutorialMod.teCarvedDirt, renderer);
		
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(TutorialMod.blockCarvedDirt), new TECarvedDirtItemRenderer());
	}
}
