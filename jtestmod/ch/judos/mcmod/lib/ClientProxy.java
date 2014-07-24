package ch.judos.mcmod.lib;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.customrender.TECarvedDirtItemRenderer;
import ch.judos.mcmod.customrender.TECarvedDirtRenderer;
import ch.judos.mcmod.gas.GasCO2TileEntity;
import ch.judos.mcmod.gas.GasRenderer;
import cpw.mods.fml.client.registry.ClientRegistry;

@SuppressWarnings("javadoc")
public class ClientProxy extends CommonProxy {

	public static TECarvedDirtRenderer	teCarvedDirtRenderer	= new TECarvedDirtRenderer();
	public static GasRenderer			gasRenderer				= new GasRenderer();

	@Override
	public void registerRenderInformation() {
		ClientRegistry.bindTileEntitySpecialRenderer(MCMod.teCarvedDirt, teCarvedDirtRenderer);

		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MCMod.blockCarvedDirt),
			new TECarvedDirtItemRenderer());

		ClientRegistry.bindTileEntitySpecialRenderer(GasCO2TileEntity.class, gasRenderer);

	}
}
