package ch.judos.mcmod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author j
 */
public class BoxContainer extends Container {

	private IInventory	te;

	protected BoxContainer(BoxTE te) {
		this.te = te;
	}

	/**
	 * 
	 * @param inventory
	 * @param te
	 */
	public BoxContainer(InventoryPlayer inventory, BoxTE te) {
		this(te);
		bindPlayerInventory(inventory);
		addSlotToContainer(new Slot(te, 0, 80, 42));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.te.isUseableByPlayer(player);
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) this.inventorySlots.get(slot);

		// null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			// merges the item into player inventory since its in the tileEntity
			int s = this.te.getSizeInventory();
			if (slot >= 36) {
				if (!this.mergeItemStack(stackInSlot, 27, 36, false))
					if (!this.mergeItemStack(stackInSlot, 0, 27, false))
						return null;

			}
			// places it into the tileEntity is possible since its in the player
			// inventory
			else {
				try {
					if (!this.mergeItemStack(stackInSlot, 36, 36 + s, false))
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

}
