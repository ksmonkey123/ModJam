package ch.modjam.generic.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import ch.modjam.generic.tileEntity.GenericTileEntity;

/**
 * This is an adapter class, it uses the other class GenericInventory and AbstractInventory.<br>
 * The methods that have to be provided for IInventory are just passed on to the GenericInventory
 * object.
 * 
 * @author j
 */
public abstract class GenericTileEntityWithInventory extends GenericTileEntity implements
		IInventory {

	public GenericInventory	inventory;

	public GenericTileEntityWithInventory(GenericInventory inv) {
		this.inventory = inv;
		this.inventory.setTileEntity(this);
	}

	@Override
	public int getSizeInventory() {
		return this.inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.inventory.getStackInSlot(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		return this.inventory.decrStackSize(slot, amount);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int stack) {
		return this.inventory.getStackInSlotOnClosing(stack);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack items) {
		this.inventory.setInventorySlotContents(slot, items);
	}

	@Override
	public String getInventoryName() {
		return this.inventory.getInventoryName();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.inventory.hasCustomInventoryName();
	}

	@Override
	public int getInventoryStackLimit() {
		return this.inventory.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.inventory.isUseableByPlayer(player);
	}

	@Override
	public void openInventory() {
		this.inventory.openInventory();
	}

	@Override
	public void closeInventory() {
		this.inventory.closeInventory();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack items) {
		return this.inventory.isItemValidForSlot(slot, items);
	}

}
