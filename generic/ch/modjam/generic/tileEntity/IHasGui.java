package ch.modjam.generic.tileEntity;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

/**
 * defines that a tileEntity has a gui
 * 
 * @author judos
 * 
 */
public interface IHasGui {
	/**
	 * @return a GuiContainer that contains the rendering (buttons, slots) and
	 *         all interaction of the gui
	 */
	public GuiContainer getGuiClient();

	/**
	 * @return the server-side container that is used to interact with the tile
	 *         entity
	 */
	public Container getGuiServer();
}
