package ch.judos.mcmod.lib;

import ch.judos.mcmod.TutorialMod;
import ch.judos.mcmod.customrender.TECarvedDirtItemRenderer;
import ch.judos.mcmod.customrender.TECarvedDirtRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	
	public static TECarvedDirtRenderer renderer = new TECarvedDirtRenderer();
	
	public void registerRenderInformation() {
//		TECarvedDirtRenderer renderer = new TECarvedDirtRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(TutorialMod.teCarvedDirt, renderer);
		
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(TutorialMod.blockCarvedDirt), new TECarvedDirtItemRenderer());
	}
}
