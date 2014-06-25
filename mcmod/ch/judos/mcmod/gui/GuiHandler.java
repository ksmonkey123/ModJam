package ch.judos.mcmod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ch.modjam.generic.tileEntity.IHasGui;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * @author j
 */
public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int guiID, EntityPlayer player, World world, int x, int y,
			int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof IHasGui)
			return ((IHasGui) te).getGuiServer(player.inventory);

		return null;
	}

	@Override
	public Object getClientGuiElement(int guiID, EntityPlayer player, World world, int x, int y,
			int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof IHasGui)
			return ((IHasGui) te).getGuiClient(player.inventory);
		return null;
	}

}
