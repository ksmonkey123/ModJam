package ch.judos.mcmod.gui;

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
import cpw.mods.fml.common.FMLCommonHandler;

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

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		System.out.println(FMLCommonHandler.instance().getSide() + " onDataPacket");
	}

	@Override
	public Packet getDescriptionPacket() {
		System.out.println(FMLCommonHandler.instance().getSide() + " getDescriptionPacket");
		return super.getDescriptionPacket();
	}

	/**
	 * 
	 */
	public void increaseSize() {
		if (this.stack.length < 5) {
			this.stack = Arrays.copyOf(this.stack, this.stack.length + 1);
			System.out.println("TE has " + this.stack.length + " slots");
			this.markDirty();
		}
	}

	/**
	 * 
	 */
	public void decreaseSize() {
		if (this.stack.length > 1) {
			this.stack = Arrays.copyOf(this.stack, this.stack.length - 1);
			System.out.println("TE has " + this.stack.length + " slots");
			this.markDirty();
		}
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
