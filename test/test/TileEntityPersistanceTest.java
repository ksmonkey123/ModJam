package test;

import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityPersistanceTest extends TileEntity {

	private int i = 0;

	@Override
	public void updateEntity() {
		super.updateEntity();
		System.out.println(i++);
		markDirty();
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		tag.setInteger("value", this.i);
		super.writeToNBT(tag);
		System.out.println("wrote");
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.i = tag.getInteger("value");
		System.out.println("read");
	}

	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		System.out.println("gotPacket");
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord,
				this.zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		System.out.println("gotPacket");
		readFromNBT(pkt.func_148857_g());
	}
	
	
	
}
