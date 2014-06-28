package ch.judos.mcmod.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import ch.modjam.generic.tileEntity.GenericContainer;

/**
 * @author j
 */
public class BoxContainer extends GenericContainer {

	protected IInventory	tileEntityInventory;

	/**
	 * @param inventory
	 * @param te
	 */
	public BoxContainer(InventoryPlayer inventory, BoxTE te) {
		super(inventory);
		this.tileEntityInventory = te.inventory;
		init();
	}

	protected void init() {
		addSlotToContainer(new Slot(tileEntityInventory, 0, 80, 42));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileEntityInventory.isUseableByPlayer(player);
	}

	@Override
	public int getSizeInventory() {
		return this.tileEntityInventory.getSizeInventory();
	}

}
