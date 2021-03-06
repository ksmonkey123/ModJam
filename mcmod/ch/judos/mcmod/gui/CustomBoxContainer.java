package ch.judos.mcmod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

/**
 * @author judos
 */
public class CustomBoxContainer extends BoxContainer {

	private CustomBoxTE		tileEntity;
	private InventoryPlayer	playerInventory;

	/**
	 * @param inventory
	 * @param te
	 */
	public CustomBoxContainer(InventoryPlayer inventory, CustomBoxTE te) {
		super(inventory, te);
		this.tileEntity = te;
		this.tileEntity.addListenerForUpdates(this);
		this.playerInventory = inventory;
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		this.tileEntity.closedContainer(this);
	}

	@Override
	protected void init() {
		for (int i = 0; i < this.tileEntityInventory.getSizeInventory(); i++)
			addSlotToContainer(new Slot(this.tileEntityInventory, i, 26 + 18 * i, 42));
	}

	/**
	 * remove all slots and bind them new
	 */
	public void reinitialize() {
		this.inventoryItemStacks.clear();
		this.inventorySlots.clear();
		bindPlayerInventory(this.playerInventory);
		init();
	}

}