package ch.modjam.generic.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

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
}
