package ch.judos.at.gearbox;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import ch.judos.at.gearbox.gui.ContainerGearbox;
import ch.judos.at.gearbox.gui.GuiContainerGearbox;
import ch.modjam.generic.gui.IHasGui;
import ch.modjam.generic.tileEntity.GenericTileEntity;

public class TEStationGearbox extends GenericTileEntity implements IHasGui {

	public enum SendMode {
		Periodically(0), GondolaFilled(1), OnRedstone(2);
		public int	id;

		private SendMode(int id) {
			this.id = id;
		}

		public static SendMode fromId(int id) {
			for (SendMode x : values()) {
				if (x.id == id)
					return x;
			}
			return null;
		}
	}

	public static final String	nbtSendMode			= "sendMode";

	private static final String	netCmdSetSendMode	= "setSendMode";

	public SendMode				sendMode;

	public TEStationGearbox() {
		this.sendMode = SendMode.Periodically;
	}

	@Override
	public boolean canUpdate() {
		return false;
	}

	@Override
	public void tick() {
		// do nothing, has no ticks
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		tag.setInteger(nbtSendMode, this.sendMode.id);
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		if (tag.hasKey(nbtSendMode))
			this.sendMode = SendMode.fromId(tag.getInteger(nbtSendMode));
	}

	@Override
	public GuiContainer getGuiClient(InventoryPlayer inventory) {
		return new GuiContainerGearbox(inventory, this);
	}

	@Override
	public Container getGuiServer(InventoryPlayer inventory) {
		return new ContainerGearbox(inventory, this);
	}

	public void requestSetSendMode(SendMode sendMode) {
		// Integer.sendMode.id
		// this.sendNetworkCommand(netCmdSetSendMode, data);
		// TODO: implement
	}

	public void requestSetReceiveRedstoneSignal(boolean checked) {
		// TODO Auto-generated method stub

	}

}
