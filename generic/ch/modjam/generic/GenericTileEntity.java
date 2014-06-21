package ch.modjam.generic;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class GenericTileEntity extends TileEntity {

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
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord,
				this.zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
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

}
