package ch.judos.mcmod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ch.judos.mcmod.lib.References;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * @author j
 */
public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int guiID, EntityPlayer player,
			World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		switch (guiID) {
		case References.GUI_BOX:
			return new BoxContainer(player.inventory, (BoxTE) te);
		case References.GUI_CUSTOM_BOX:
			return new CustomBoxContainer(player.inventory, (CustomBoxTE) te);
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int guiID, EntityPlayer player,
			World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		switch (guiID) {
		case References.GUI_BOX:
			return new BoxGuiContainer(player.inventory, (BoxTE) te);
		case References.GUI_CUSTOM_BOX:
			return new CustomBoxGuiContainer(player.inventory, (CustomBoxTE) te);
		default:
			return null;
		}
	}

}
