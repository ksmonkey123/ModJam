package ch.judos.at.gearbox.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import ch.judos.at.gearbox.TEStationGearbox;
import ch.modjam.generic.gui.GenericContainer;

public class ContainerGearbox extends GenericContainer {

	public ContainerGearbox(InventoryPlayer inventoryPlayer, TEStationGearbox te) {
		super();
	}

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}
