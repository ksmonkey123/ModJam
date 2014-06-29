package ch.modjam.generic.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ch.modjam.generic.inventory.FakeSlot;

/**
 * @author judos
 * 
 */
public abstract class GenericContainer extends Container {

	/**
	 * @param inventoryPlayer inventory of the player
	 */
	public GenericContainer(InventoryPlayer inventoryPlayer) {
		bindPlayerInventory(inventoryPlayer);
	}

	/**
	 * note: the player inventory is automatically bound in the constructor of the GenericContainer
	 * 
	 * @param inventoryPlayer inventory of the player
	 */
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				if (!isPlayerSlotAFakeSlot(j + i * 9 + 9))
					addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18,
						84 + i * 18));
				else
					addSlotToContainer(new FakeSlot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18,
						84 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			if (!isPlayerSlotAFakeSlot(i))
				addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
			else
				addSlotToContainer(new FakeSlot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	protected void unbindPlayerInventory() {
		this.inventorySlots.clear();
		this.inventoryItemStacks.clear();
	}

	protected boolean isPlayerSlotAFakeSlot(int slot) {
		return false;
	}

	/**
	 * @return the amount of custom slots
	 */
	public abstract int getSizeInventory();

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) this.inventorySlots.get(slot);

		// null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			// merges the item into player inventory since its in the tileEntity
			int s = this.getSizeInventory();
			if (slot >= 36) {
				if (!this.mergeItemStack2(stackInSlot, 27, 36))
					if (!this.mergeItemStack2(stackInSlot, 0, 27))
						return null;

			}
			// places it into the tileEntity is possible since its in the player
			// inventory
			else {
				try {
					if (!this.mergeItemStack2(stackInSlot, 36, 36 + s))
						return null;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}

			if (stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}

			// no items were moved by mergeItemStack (since size is still the same)
			if (stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}

	protected boolean mergeItemStack2(ItemStack stack, int slotStart, int slotEnd) {
		for (int slot = slotStart; slot < slotEnd; slot++) {
			Slot s = (Slot) this.inventorySlots.get(slot);
			if (s.isItemValid(stack)) {
				if (this.mergeItemStack(stack, slot, slot + 1, false))
					return true;
			}
		}
		return false;
	}
}
