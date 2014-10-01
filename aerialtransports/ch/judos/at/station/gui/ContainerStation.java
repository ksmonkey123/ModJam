package ch.judos.at.station.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ch.judos.at.ATMain;
import ch.judos.at.station.TEStation;
import ch.modjam.generic.gui.GenericContainer;
import ch.modjam.generic.inventory.GenericInventory;
import ch.modjam.generic.inventory.InventorySlotChangedListener;
import ch.modjam.generic.inventory.slot.WhiteListSlot;

/**
 * @author judos
 */
public class ContainerStation extends GenericContainer {

	protected GenericInventory				tileEntityInventory;
	private InventorySlotChangedListener	slotListener;

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
		this.slotListener = new InventorySlotChangedListener() {
			@Override
			public void slotChanged(int slot, ItemStack items) {
				ContainerStation.this.detectAndSendChanges();
			}
		};
		this.tileEntityInventory.addListener(this.slotListener);
		gondolas.addAllowedItem(ATMain.gondola);
		addSlotToContainer(gondolas);
		addSlotToContainer(new Slot(this.tileEntityInventory, 1, 70, 49 - 5));
	}

	@Override
	public void onContainerClosed(EntityPlayer p_75134_1_) {
		this.tileEntityInventory.removeListener(this.slotListener);
		super.onContainerClosed(p_75134_1_);
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
