package ch.judos.mcmod.itemNbt;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ch.judos.mcmod.GenericInventory;
import ch.modjam.generic.tileEntity.InventoryUtil;

/**
 * @author judos
 * 
 */
public class BoundHeartContainer extends Container {

	/**
	 * the item stack of the heart
	 */
	private ItemStack			stack;
	/**
	 * inventory of the player who currently has this gui opened
	 */
	private InventoryPlayer		inventory;
	private GenericInventory	heart;

	/**
	 * @param inventory
	 * @param stack
	 */
	public BoundHeartContainer(InventoryPlayer inventory, ItemStack stack) {
		this.stack = stack;
		this.heart = new GenericInventory(4, "boundheart_inventory");
		this.inventory = inventory;
		InventoryUtil.bindPlayerInventory(this, inventory);
		addSlotToContainer(new Slot(heart, 0, 80, 42));
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

}
