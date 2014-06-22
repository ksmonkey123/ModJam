package ch.judos.mcmod.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

/**
 * @author j
 */
public class CustomBoxContainer extends BoxContainer {

	/**
	 * @param inventory
	 * @param te
	 */
	public CustomBoxContainer(InventoryPlayer inventory, CustomBoxTE te) {
		super(te);
		for (int i = 0; i < te.stack.length; i++)
			addSlotToContainer(new Slot(te, i, 26 + 18 * i, 42));
		bindPlayerInventory(inventory);
	}

}