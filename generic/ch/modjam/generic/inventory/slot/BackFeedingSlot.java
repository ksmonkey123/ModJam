package ch.modjam.generic.inventory.slot;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author judos
 *
 */
public class BackFeedingSlot extends Slot {

	private ItemStack	lastStack;

	/**
	 * @param inventory
	 * @param slotNr
	 * @param x
	 * @param y
	 */
	public BackFeedingSlot(IInventory inventory, int slotNr, int x, int y) {
		super(inventory, slotNr, x, y);
	}

	@Override
	public ItemStack getStack() {
		if (Minecraft.getMinecraft().theWorld.isRemote && this.slotNumber == 36) {
			StackTraceElement origin = new Exception().getStackTrace()[1];
			if (origin.getMethodName().equals("func_146977_a") || origin.getMethodName().equals(
				"drawSlot"))
				return this.lastStack;
			if (origin.getMethodName().equals("detectAndSendChanges") || origin.getMethodName()
				.equals("func_75142_b"))
				return this.lastStack;
			System.out.println(this.lastStack + " " + origin.getMethodName());
		}

		this.lastStack = super.getStack();
		return this.lastStack;
	}

	@Override
	public void onSlotChanged() {
		this.inventory.setInventorySlotContents(this.slotNumber, this.lastStack);
		super.onSlotChanged();
	}

}
