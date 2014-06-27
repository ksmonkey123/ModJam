package ch.judos.mcmod.gui;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.StatCollector;
import ch.judos.mcmod.lib.Names;

/**
 * @author j
 */
public class CustomBoxTE extends BoxTE {

	private ArrayList<CustomBoxGuiContainer>	containers;

	/**
	 * 
	 */
	public CustomBoxTE() {
		this.stack = new ItemStack[2];
		this.containers = new ArrayList<CustomBoxGuiContainer>();
	}

	@Override
	public String getInventoryName() {
		return StatCollector.translateToLocal("tile." + Names.CustomBox + ".name");
	}

	/**
	 * 
	 */
	public void tryIncreaseSize() {
		System.out
			.println(Thread.currentThread().getName() + ": tryIncreaseSize length: " + this.stack.length);
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
		System.out
			.println(Thread.currentThread().getName() + ": onNetworkCommand (cmd:" + command + ", byte[0]:" + data[0] + ")");
		int newSize = (int) data[0];
		this.stack = Arrays.copyOf(this.stack, newSize);

	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		System.out.println(Thread.currentThread().getName() + " GuiContainers: " + this.containers
			.size() + " current Slots: " + this.stack.length);
		for (CustomBoxGuiContainer c : this.containers) {
			System.out
				.println(Thread.currentThread().getName() + ": onDataPacket() updating container: " + c);
			c.reinitialize();
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		System.out.println(Thread.currentThread().getName() + ": getDescriptionPacket()");
		return super.getDescriptionPacket();
	}

	@Override
	public Container getGuiServer(InventoryPlayer inventory) {
		return new CustomBoxContainer(inventory, this);
		// this.containers.add(c);
		// return c;
	}

	@Override
	public GuiContainer getGuiClient(InventoryPlayer inventory) {
		CustomBoxGuiContainer c = new CustomBoxGuiContainer(inventory, this);
		this.containers.add(c);
		System.out.println(Thread.currentThread().getName() + ": getGuiClient()");
		return c;
	}

	/**
	 * removes a container from the update notification list
	 * 
	 * @param container
	 */
	public void closedContainer(CustomBoxContainer container) {
		this.containers.remove(container);
	}

}
