package ch.judos.mcmod.gui;

import java.util.Arrays;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import ch.judos.mcmod.lib.Names;

/**
 * @author j
 */
public class CustomBoxTE extends BoxTE {

	/**
	 * 
	 */
	public CustomBoxTE() {
		this.stack = new ItemStack[2];
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
		this.stack = Arrays.copyOf(this.stack, newSize);
	}

	@Override
	public Container getGuiServer(InventoryPlayer inventory) {
		return new CustomBoxContainer(inventory, this);
	}

	@Override
	public GuiContainer getGuiClient(InventoryPlayer inventory) {
		return new CustomBoxGuiContainer(inventory, this);
	}

}
