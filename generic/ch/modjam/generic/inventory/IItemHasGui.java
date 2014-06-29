package ch.modjam.generic.inventory;

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
	 * @param slot the slot number of the player inventory where the stack is located
	 * @return GuiContainer for this item gui
	 */
	GuiContainer getGuiClient(InventoryPlayer inventory, ItemStack stack, int slot);

	/**
	 * @param inventory
	 * @param stack the item upon which the gui is opened
	 * @param slot the slot number of the player inventory where the stack is located
	 * @return Container the server uses to interact with the gui
	 */
	Container getGuiServer(InventoryPlayer inventory, ItemStack stack, int slot);

}
