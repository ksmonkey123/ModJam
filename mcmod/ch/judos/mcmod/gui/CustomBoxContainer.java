package ch.judos.mcmod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import ch.modjam.generic.tileEntity.InventoryUtil;

/**
 * @author j
 */
public class CustomBoxContainer extends BoxContainer {

	private CustomBoxTE		tileEntity;
	private InventoryPlayer	inventory;

	/**
	 * @param inventory
	 * @param te
	 */
	public CustomBoxContainer(InventoryPlayer inventory, CustomBoxTE te) {
		super(te);
		this.tileEntity = te;
		this.tileEntity.addListenerForUpdates(this);
		this.inventory = inventory;
		initialize();
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		this.tileEntity.closedContainer(this);
	}

	private void initialize() {
		InventoryUtil.bindPlayerInventory(this, inventory);
		for (int i = 0; i < this.tileEntity.inventory.stack.length; i++)
			addSlotToContainer(new Slot(this.tileEntity.inventory, i, 26 + 18 * i, 42));
	}

	/**
	 * remove all slots and bind them new
	 */
	public void reinitialize() {
		this.inventoryItemStacks.clear();
		this.inventorySlots.clear();
		initialize();
	}

}