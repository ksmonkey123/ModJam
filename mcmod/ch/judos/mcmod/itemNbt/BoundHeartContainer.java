package ch.judos.mcmod.itemNbt;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ch.judos.mcmod.GenericInventory;
import ch.modjam.generic.tileEntity.GenericContainer;

/**
 * @author judos
 * 
 */
public class BoundHeartContainer extends GenericContainer {

	/**
	 * the item stack of the heart
	 */
	private ItemStack			stack;
	private GenericInventory	heart;

	/**
	 * @param inventory
	 * @param stack
	 */
	public BoundHeartContainer(InventoryPlayer inventory, ItemStack stack) {
		super(inventory);
		this.stack = stack;
		this.heart = new GenericInventory(5, "boundheart_inventory");
		this.heart.readNBT(stack.stackTagCompound);
		System.out.println("constructed container for " + this.stack.stackTagCompound);
		for (ItemStack s : this.heart.stack)
			System.out.println(s);
		this.heart.resizeInventory(5);
		init();
	}

	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		this.heart.writeNBT(this.stack.stackTagCompound);
		System.out.println(Thread.currentThread().getName() + ": closed and saved " + this.stack);
		System.out.println(this.stack.stackTagCompound);
		super.onContainerClosed(par1EntityPlayer);
	}

	private void init() {
		for (int i = 0; i < this.heart.getSizeInventory(); i++)
			addSlotToContainer(new Slot(heart, i, 44 + 18 * i, 53));
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
