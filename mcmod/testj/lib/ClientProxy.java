package testj.lib;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import testj.TutorialMod;
import testj.customrender.TECarvedDirtItemRenderer;
import testj.customrender.TECarvedDirtRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{
	
	public void registerRenderInformation() {
		TECarvedDirtRenderer r = new TECarvedDirtRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TutorialMod.teCarvedDirt, r);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(TutorialMod.blockCarvedDirt), new TECarvedDirtItemRenderer());
	}
}
