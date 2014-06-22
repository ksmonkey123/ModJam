package ch.judos.mcmod.lib;

import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.customrender.TECarvedDirtItemRenderer;
import ch.judos.mcmod.customrender.TECarvedDirtRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;

@SuppressWarnings("javadoc")
public class ClientProxy extends CommonProxy {
	
	public static TECarvedDirtRenderer renderer = new TECarvedDirtRenderer();
	
	@Override
	public void registerRenderInformation() {
//		TECarvedDirtRenderer renderer = new TECarvedDirtRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(MCMod.teCarvedDirt, renderer);
		
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MCMod.blockCarvedDirt), new TECarvedDirtItemRenderer());
	}
}
