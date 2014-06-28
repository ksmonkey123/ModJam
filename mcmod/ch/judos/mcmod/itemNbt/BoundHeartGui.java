package ch.judos.mcmod.itemNbt;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

/**
 * @author judos
 */
public class BoundHeartGui extends GuiContainer {

	/**
	 * @param inventory
	 * @param stack
	 */
	public BoundHeartGui(InventoryPlayer inventory, ItemStack stack) {
		super(new BoundHeartContainer(inventory, stack));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {}

}
