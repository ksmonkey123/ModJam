package ch.judos.mcmod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * @author judos
 */
public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int guiID, EntityPlayer player, World world, int x, int y,
			int z) {

		return null;
	}

	@Override
	public Object getClientGuiElement(int guiID, EntityPlayer player, World world, int x, int y,
			int z) {
		return null;
	}

}
