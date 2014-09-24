package ch.modjam.generic.tileEntity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import ch.modjam.generic.GenericMod;
import ch.modjam.generic.networking.CommandMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * a generic TileEntity with all the required methods directly visible
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public abstract class GenericTileEntity extends TileEntity {

	public boolean isOnTheSameBlockPosition(TileEntity t) {
		return this.xCoord == t.xCoord && this.yCoord == t.yCoord && this.zCoord == t.zCoord;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		this.tick();
	}

	@Override
	public void writeToNBT(NBTTagCompound p_145841_1_) {
		super.writeToNBT(p_145841_1_);
		this.writeNBT(p_145841_1_);
	}

	@Override
	public void readFromNBT(NBTTagCompound p_145839_1_) {
		super.readFromNBT(p_145839_1_);
		this.readNBT(p_145839_1_);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	/**
	 * Tick the entity
	 */
	public abstract void tick();

	/**
	 * Store the TileEntity data into NBT
	 * 
	 * @param tag
	 */
	public abstract void writeNBT(NBTTagCompound tag);

	/**
	 * Restore the TileEntity data from NBT
	 * 
	 * @param tag
	 */
	public abstract void readNBT(NBTTagCompound tag);

	/**
	 * Sends a command to the server-side tile entity. Use client side only.
	 * 
	 * @param command the command to send
	 * @param data additional data for the command
	 */
	@SideOnly(Side.CLIENT)
	public final void sendNetworkCommand(String command, byte... data) {
		if (this.worldObj.isRemote) {
			CommandMessage message = new CommandMessage(this, command, data);
			GenericMod.NETWORK.sendToServer(message);
		}
	}

	/**
	 * forces the server to send the server-side TileEntity to all clients to ensure syncing
	 */
	public final void forceNetworkUpdate() {
		if (this.worldObj.isRemote)
			this.sendNetworkCommand("");
		else
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}

	/**
	 * force serverside to send TE to all clients. ignored client side
	 */
	public final void forceServerPush() {
		if (!this.worldObj.isRemote)
			this.forceNetworkUpdate();
	}

	/**
	 * This method is invoked server-side whenever a network command is received. After the
	 * completion of execution of this method, the server-side tileEntity NBT data is sent to all
	 * clients for Synchronization. <b>do not use this method client-side</b>
	 * 
	 * @param command the command string identifying the command
	 * @param data additional command data
	 */
	public void onNetworkCommand(String command, byte[] data) {
		// default: empty
	}

}
