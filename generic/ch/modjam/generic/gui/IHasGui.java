package ch.modjam.generic.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

/**
 * defines that a tileEntity has a gui
 * 
 * @author judos
 * 
 */
public interface IHasGui {
	/**
	 * @param inventory of the player
	 * @return a GuiContainer that contains the rendering (buttons, slots) and
	 *         all interaction of the gui
	 */
	public GuiContainer getGuiClient(InventoryPlayer inventory);

	/**
	 * @param inventory of the player
	 * @return the server-side container that is used to interact with the tile
	 *         entity
	 */
	public Container getGuiServer(InventoryPlayer inventory);
}
