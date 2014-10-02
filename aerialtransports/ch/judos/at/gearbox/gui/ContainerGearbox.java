package ch.judos.at.gearbox.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import ch.judos.at.gearbox.TEStationGearbox;
import ch.modjam.generic.gui.GenericContainer;
import ch.modjam.generic.inventory.GenericInventory;

public class ContainerGearbox extends GenericContainer {

	public ContainerGearbox(InventoryPlayer inventoryPlayer, TEStationGearbox te) {
		super();
	}

	/*
	 * important bug-fix, usually minecraft crashes when items with changing nbt activate a block
	 * with zero-slots (e.g. bound-heart right-click on gearbox block)
	 */
	@Override
	public int getSizeInventory() {
		return 1;
	}

	/*
	 * important bug-fix, usually minecraft crashes when items with changing nbt activate a block
	 * with zero-slots (e.g. bound-heart right-click on gearbox block)
	 */
	@Override
	public Slot getSlotFromInventory(IInventory p_75147_1_, int p_75147_2_) {
		return new Slot(new GenericInventory(1, "bug preventer"), 0, 0, 0);
	}

	/*
	 * important bug-fix, usually minecraft crashes when items with changing nbt activate a block
	 * with zero-slots (e.g. bound-heart right-click on gearbox block)
	 */
	@Override
	public Slot getSlot(int p_75139_1_) {
		return new Slot(new GenericInventory(1, "bug preventer"), 0, 0, 0);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}
