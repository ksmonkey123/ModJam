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

	public static final String	nbtSendMode						= "sendMode";

	private static final String	netCmdSetSendMode				= "setSendMode";

	private static final String	netCmdSetReceiveEmitRedstone	= "setReceiveEmitRedstone";

	private static final String	nbtReceiveEmitsRedstone			= "receiveEmitsRedstone";

	public SendMode				sendMode;

	public boolean				receiveEmitsRedstone;

	/**
	 * set to true on a signal (in block class), nearby station will set it to false in update
	 * tick() when a gondola can be sent
	 */
	public boolean				isPowered;

	public TEStationGearbox() {
		this.sendMode = SendMode.Periodically;
		this.receiveEmitsRedstone = false;
	}

	@Override
	public void onNetworkCommand(String command, Object data) {
		if (netCmdSetSendMode.equals(command)) {
			this.sendMode = SendMode.fromId((Integer) data);
			this.worldObj.updateNeighborsAboutBlockChange(this.xCoord, this.yCoord, this.zCoord,
				null);
		} else if (netCmdSetReceiveEmitRedstone.equals(command)) {
			this.receiveEmitsRedstone = (Boolean) data;
			this.worldObj.updateNeighborsAboutBlockChange(this.xCoord, this.yCoord, this.zCoord,
				null);
		}
		super.onNetworkCommand(command, data);
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
		tag.setBoolean(nbtReceiveEmitsRedstone, this.receiveEmitsRedstone);
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		if (tag.hasKey(nbtSendMode))
			this.sendMode = SendMode.fromId(tag.getInteger(nbtSendMode));
		if (tag.hasKey(nbtReceiveEmitsRedstone))
			this.receiveEmitsRedstone = tag.getBoolean(nbtReceiveEmitsRedstone);
	}

	@Override
	public GuiContainer getGuiClient(InventoryPlayer inventory) {
		return new GuiContainerGearbox(inventory, this);
	}

	@Override
	public Container getGuiServer(InventoryPlayer inventory) {
		return new ContainerGearbox(inventory, this);
	}

	public void requestSetSendMode(SendMode requestedSendMode) {
		this.sendNetworkCommand(netCmdSetSendMode, requestedSendMode.id);
	}

	public void requestSetReceiveRedstoneSignal(boolean checked) {
		this.sendNetworkCommand(netCmdSetReceiveEmitRedstone, checked);
	}

}
