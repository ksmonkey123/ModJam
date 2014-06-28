package ch.modjam.generic.tileEntity;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

/**
 * @author judos
 * 
 */
public interface IItemHasGui {

	/**
	 * @param inventory
	 * @param stack the item upon which the gui is opened
	 * @return GuiContainer for this item gui
	 */
	GuiContainer getGuiClient(InventoryPlayer inventory, ItemStack stack);

	/**
	 * @param inventory
	 * @param stack the item upon which the gui is opened
	 * @return Container the server uses to interact with the gui
	 */
	Container getGuiServer(InventoryPlayer inventory, ItemStack stack);

}
