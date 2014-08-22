package ch.judos.at.station.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import ch.judos.at.ATMain;
import ch.judos.at.station.TEStation;
import ch.modjam.generic.gui.GenericContainer;
import ch.modjam.generic.inventory.slot.WhiteListSlot;

/**
 * @author judos
 */
public class ContainerStation extends GenericContainer {

	protected IInventory	tileEntityInventory;

	/**
	 * @param inventory
	 * @param te
	 */
	public ContainerStation(InventoryPlayer inventory, TEStation te) {
		super(inventory);
		this.tileEntityInventory = te.inventory;
		init();
	}

	protected void init() {
		WhiteListSlot gondolas = new WhiteListSlot(this.tileEntityInventory, 0, 70, 31 - 5);
		gondolas.addItem(ATMain.gondola);
		addSlotToContainer(gondolas);
		addSlotToContainer(new Slot(this.tileEntityInventory, 1, 70, 49 - 5));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileEntityInventory.isUseableByPlayer(player);
	}

	@Override
	public int getSizeInventory() {
		return this.tileEntityInventory.getSizeInventory();
	}

}
