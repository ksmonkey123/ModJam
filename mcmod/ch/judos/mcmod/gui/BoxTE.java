package ch.judos.mcmod.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import ch.judos.mcmod.GenericInventory;
import ch.judos.mcmod.lib.Names;
import ch.modjam.generic.inventory.IHasGui;
import ch.modjam.generic.tileEntity.GenericTileEntity;

/**
 * @author j
 */
public class BoxTE extends GenericTileEntity implements IHasGui {

	/**
	 * the generic inventory of this box
	 */
	public GenericInventory	inventory;

	/**
	 * 
	 */
	public BoxTE() {
		this.inventory = new GenericInventory(1, Names.Box);
	}

	@Override
	public void tick() {
		// not required
	}

	@Override
	public GuiContainer getGuiClient(InventoryPlayer inventory) {
		return new BoxGuiContainer(inventory, this);
	}

	@Override
	public Container getGuiServer(InventoryPlayer inventory) {
		return new BoxContainer(inventory, this);
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		this.inventory.writeNBT(tag);
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		this.inventory.readNBT(tag);
	}
}
