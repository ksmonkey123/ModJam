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
import net.minecraft.util.StatCollector;
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
		this.stack = new ItemStack[2];
		this.containers = new ArrayList<CustomBoxContainer>();
	}

	@Override
	public String getInventoryName() {
		return StatCollector.translateToLocal("tile." + Names.CustomBox + ".name");
	}

	/**
	 * 
	 */
	public void tryIncreaseSize() {
		if (this.stack.length < 5)
			this.sendNetworkCommand("slotSizeChanged", (byte) (this.stack.length + 1));
	}

	/**
	 * 
	 */
	public void tryDecreaseSize() {
		if (this.stack.length > 1)
			this.sendNetworkCommand("slotSizeChanged", (byte) (this.stack.length - 1));
	}

	@Override
	public void onNetworkCommand(String command, byte[] data) {
		int newSize = (int) data[0];
		if (newSize < this.stack.length)
			dropItemsOnTheFloor(this.stack[this.stack.length - 1]);
		this.stack = Arrays.copyOf(this.stack, newSize);
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
