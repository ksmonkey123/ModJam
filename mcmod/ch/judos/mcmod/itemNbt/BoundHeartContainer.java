package ch.judos.mcmod.itemNbt;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ch.modjam.generic.gui.GenericContainer;
import ch.modjam.generic.inventory.GenericNBTInventory;
import ch.modjam.generic.inventory.NBTProvider;

/**
 * @author judos
 * 
 */
public class BoundHeartContainer extends GenericContainer {

	private GenericNBTInventory	heart;
	private int					heartSlot;
	private InventoryPlayer		playerInventory;

	/**
	 * @param inventory
	 * @param stack
	 * @param slot
	 */
	public BoundHeartContainer(final InventoryPlayer inventory, final ItemStack stack,
			final int slot) {
		super(inventory);
		this.playerInventory = inventory;
		this.heartSlot = slot;

		NBTProvider pr = new NBTProvider() {
			@Override
			public NBTTagCompound getNBT() {
				if (inventory.mainInventory[slot] == null || !stack
					.isItemEqual(inventory.mainInventory[slot])) {
					inventory.player.closeScreen();
					return null;
				}
				return inventory.mainInventory[slot].stackTagCompound;
			}
		};

		this.heart = new GenericNBTInventory(pr, "boundheart_inventory");
		init();
	}

	@Override
	public boolean isPlayerSlotAFakeSlot(int slot) {
		return slot == this.heartSlot;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
	}

	private void init() {
		unbindPlayerInventory();
		bindPlayerInventory(this.playerInventory);
		for (int i = 0; i < this.heart.getSizeInventory(); i++) {
			// addSlotToContainer(new BackFeedingSlot(this.heart, i, 44 + 18 * i, 53));
			addSlotToContainer(new Slot(this.heart, i, 44 + 18 * i, 53));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	@Override
	public int getSizeInventory() {
		return this.heart.getSizeInventory();
	}

}
