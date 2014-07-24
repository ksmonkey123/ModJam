package ch.judos.mcmod.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import ch.judos.mcmod.lib.Names;
import ch.modjam.generic.gui.IHasGui;
import ch.modjam.generic.inventory.GenericInventory;
import ch.modjam.generic.tileEntity.GenericTileEntity;

/**
 * @author judos
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
	public GuiContainer getGuiClient(InventoryPlayer inventoryPlayer) {
		return new BoxGuiContainer(inventoryPlayer, this);
	}

	@Override
	public Container getGuiServer(InventoryPlayer inventoryPlayer) {
		return new BoxContainer(inventoryPlayer, this);
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
