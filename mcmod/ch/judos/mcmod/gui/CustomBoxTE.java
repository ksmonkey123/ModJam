package ch.judos.mcmod.gui;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import ch.judos.mcmod.GenericInventory;
import ch.judos.mcmod.lib.Names;

/**
 * @author j
 */
public class CustomBoxTE extends BoxTE {

	private ArrayList<CustomBoxContainer>	containers;

	/**
	 * 
	 */
	public CustomBoxTE() {
		this.inventory = new GenericInventory(2, Names.CustomBox);
		this.containers = new ArrayList<CustomBoxContainer>();
	}

	/**
	 * 
	 */
	public void tryIncreaseSize() {
		if (this.inventory.stack.length < 5)
			this.sendNetworkCommand("slotSizeChanged", (byte) (this.inventory.stack.length + 1));
	}

	/**
	 * 
	 */
	public void tryDecreaseSize() {
		if (this.inventory.stack.length > 1)
			this.sendNetworkCommand("slotSizeChanged", (byte) (this.inventory.stack.length - 1));
	}

	@Override
	public void onNetworkCommand(String command, byte[] data) {
		int newSize = (int) data[0];
		if (newSize < this.inventory.stack.length)
			dropItemsOnTheFloor(this.inventory.stack[this.inventory.stack.length - 1]);
		this.inventory.stack = Arrays.copyOf(this.inventory.stack, newSize);
		for (CustomBoxContainer c : this.containers)
			c.reinitialize();
	}

	private void dropItemsOnTheFloor(ItemStack itemStack) {
		if (itemStack == null || itemStack.stackSize == 0)
			return;
		EntityItem ei = new EntityItem(this.worldObj, this.xCoord + 0.5, this.yCoord + 1.5,
			this.zCoord + 0.5, itemStack);
		ei.motionX *= 0.5f;
		ei.motionY *= 0.5f;
		ei.motionZ *= 0.5f;
		this.worldObj.spawnEntityInWorld(ei);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		for (CustomBoxContainer c : this.containers)
			c.reinitialize();
	}

	@Override
	public Container getGuiServer(InventoryPlayer inventory) {
		return new CustomBoxContainer(inventory, this);
	}

	@Override
	public GuiContainer getGuiClient(InventoryPlayer inventory) {
		return new CustomBoxGuiContainer(inventory, this);
	}

	/**
	 * removes a container from the update notification list
	 * 
	 * @param container
	 */
	public void closedContainer(CustomBoxContainer container) {
		this.containers.remove(container);
	}

	/**
	 * @param c
	 */
	public void addListenerForUpdates(CustomBoxContainer c) {
		this.containers.add(c);
	}

}
