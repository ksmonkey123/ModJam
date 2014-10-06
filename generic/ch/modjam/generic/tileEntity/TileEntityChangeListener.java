package ch.modjam.generic.tileEntity;

import net.minecraft.nbt.NBTTagCompound;

public interface TileEntityChangeListener {

	/**
	 * This method is invoked server-side whenever a network command is received. After the
	 * completion of execution of this method, the server-side tileEntity NBT data is sent to all
	 * clients for Synchronization. <b>do not use this method client-side</b><br>
	 * <br>
	 * <b>Important:</b> Make sure your TileEntity calls super.onNetworkCommand() otherwise the
	 * listeners won't receive the method call.
	 * 
	 * @param command the command string identifying the command
	 * @param data additional command data
	 */
	public void onNetworkCommand(String command, byte[] data);

	/**
	 * this method is called client-side when the tileEntity is updated
	 * 
	 * @param nbtCompound the nbt of the packet update
	 */
	public void onDataPacket(NBTTagCompound nbtCompound);

}
