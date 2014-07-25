package ch.judos.mcmod.emptyGui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

/**
 * @author j
 *
 */
public class EmptyContainer extends Container {

	/**
	 * @param inventory
	 * @param stack
	 * @param slot
	 */
	public EmptyContainer(InventoryPlayer inventory, ItemStack stack, int slot) {
		// do nothing
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

}
