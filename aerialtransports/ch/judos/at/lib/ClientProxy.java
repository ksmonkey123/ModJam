package ch.judos.at.lib;

@SuppressWarnings("javadoc")
public class ClientProxy extends CommonProxy {

	@Override
	public void registerRenderInformation() {
		// ClientRegistry.bindTileEntitySpecialRenderer(MCMod.teCarvedDirt, teCarvedDirtRenderer);

		// MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MCMod.blockCarvedDirt),
		// new TECarvedDirtItemRenderer());

	}
}
