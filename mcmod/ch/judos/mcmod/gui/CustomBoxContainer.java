package ch.judos.mcmod.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class CustomBoxContainer extends BoxContainer {

	public CustomBoxContainer(InventoryPlayer inventory, CustomBoxTE te) {
		super(te);
		for (int i = 0; i < te.stack.length; i++)
			addSlotToContainer(new Slot(te, i, 26 + 18 * i, 42));
		bindPlayerInventory(inventory);
	}

}