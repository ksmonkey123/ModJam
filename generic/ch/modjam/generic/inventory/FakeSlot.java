package ch.modjam.generic.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author judos
 * 
 */
public class FakeSlot extends Slot {

	/**
	 * @param par1iInventory
	 * @param par2
	 * @param par3
	 * @param par4
	 */
	public FakeSlot(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int par1) {
		// stack is not changed
		return this.inventory.getStackInSlot(par1);
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return false;
	}
}
