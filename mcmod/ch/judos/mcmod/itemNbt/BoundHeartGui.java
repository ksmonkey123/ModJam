package ch.judos.mcmod.itemNbt;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * @author judos
 */
public class BoundHeartGui extends GuiContainer {

	/**
	 * @param inventory
	 */
	public BoundHeartGui(InventoryPlayer inventory) {
		super(new BoundHeartContainer(inventory));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {

	}

}
