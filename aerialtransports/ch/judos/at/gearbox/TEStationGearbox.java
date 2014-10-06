package ch.judos.at.gearbox;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import ch.judos.at.ATConfig;
import ch.judos.at.gearbox.gui.ContainerGearbox;
import ch.judos.at.gearbox.gui.GuiContainerGearbox;
import ch.modjam.generic.gui.IHasGui;
import ch.modjam.generic.tileEntity.GenericTileEntity;

public class TEStationGearbox extends GenericTileEntity implements IHasGui {

	public static final String	nbtSendMode						= "sendMode";
	public static final String	nbtReceiveEmitsRedstone			= "receiveEmitsRedstone";
	public static final String	netCmdSetSendMode				= "setSendMode";
	public static final String	netCmdSetReceiveEmitRedstone	= "setReceiveEmitRedstone";
	public static final String	nbtCurrentlyEmitingRs			= "currentlyEmitingRs";

	public SendMode				sendMode;
	public boolean				receiveEmitsRedstone;

	/**
	 * set to true when the station receives a gondola, emitRedstone() schedules an update and the
	 * block will reset the value
	 */
	public boolean				currentlyEmitingRedstone;

	/**
	 * whether the gearbox has been powered recently by redstone. this is set by the block and reset
	 * by a nearby station when it sends out a gondola
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
		tag.setBoolean(nbtCurrentlyEmitingRs, this.currentlyEmitingRedstone);
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		if (tag.hasKey(nbtSendMode))
			this.sendMode = SendMode.fromId(tag.getInteger(nbtSendMode));
		if (tag.hasKey(nbtReceiveEmitsRedstone))
			this.receiveEmitsRedstone = tag.getBoolean(nbtReceiveEmitsRedstone);
		if (tag.hasKey(nbtCurrentlyEmitingRs))
			this.currentlyEmitingRedstone = tag.getBoolean(nbtCurrentlyEmitingRs);
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

	public void emitRedstone() {
		this.currentlyEmitingRedstone = true;
		this.worldObj.scheduleBlockUpdate(this.xCoord, this.yCoord, this.zCoord, this
			.getBlockType(), ATConfig.GEARBOX_EMIT_REDSTONE_X_TICKS);

		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this
			.getBlockType());
	}

}
