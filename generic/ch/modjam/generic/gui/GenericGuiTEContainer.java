package ch.modjam.generic.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

/**
 * @author judos
 * 
 */
public abstract class GenericGuiTEContainer extends GuiContainer {

	protected TileEntity		te;
	protected InventoryPlayer	inventory;

	/**
	 * @param container
	 * @param te tileentity (for checking whether it was destroyed and gui should be closed)
	 * @param inventory
	 */
	public GenericGuiTEContainer(Container container, TileEntity te, InventoryPlayer inventory) {
		super(container);
		this.te = te;
		this.inventory = inventory;
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		checkInventoryClosed();
	}

	private void checkInventoryClosed() {
		TileEntity other = this.te.getWorld().getTileEntity(this.te.xCoord, this.te.yCoord,
			this.te.zCoord);
		if (other != this.te)
			this.inventory.player.closeScreen();
	}

	protected void drawText(String unlocalized_text, String appendedText, int x, int y,
			boolean centered) {
		String text = StatCollector.translateToLocal(unlocalized_text) + appendedText;
		int color = 4210752;
		if (centered)
			x -= this.fontRendererObj.getStringWidth(text) / 2;
		this.fontRendererObj.drawString(text, x, y, color);
	}

	protected void drawText(String unlocalized_text, int x, int y, boolean centered) {
		this.drawText(unlocalized_text, "", x, y, centered);
	}
}
