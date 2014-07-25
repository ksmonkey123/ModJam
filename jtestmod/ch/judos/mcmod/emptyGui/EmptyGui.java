package ch.judos.mcmod.emptyGui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

/**
 * @author j
 *
 */
public class EmptyGui extends GuiContainer {

	/**
	 * @param inventory
	 * @param stack
	 * @param slot
	 */
	public EmptyGui(InventoryPlayer inventory, ItemStack stack, int slot) {
		super(new EmptyContainer(inventory, stack, slot));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_,
			int p_146976_3_) {
		// do nothing
	}

}
