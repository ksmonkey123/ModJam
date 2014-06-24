package ch.modjam.generic.networking;

import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

/**
 * This Message is used for sending updated Tile Entity data from the server to
 * the clients. This is part of the TileEntity update system.
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
public class NBTMessage implements IMessage {

	private int xCoord;
	private int yCoord;
	private int zCoord;
	private NBTTagCompound nbt;

	/**
	 * raw constructor. This is required for Message reconstruction on the
	 * receiver end. <b>Do not use this to construct messages!</b>
	 */
	public NBTMessage() {
	}

	/**
	 * constructor for message construction
	 * 
	 * @param xCoord
	 *            the x coordinate of the tile entity
	 * @param yCoord
	 *            the y coordinate of the tile entity
	 * @param zCoord
	 *            the z coordinate of the tile entity
	 * @param nbt
	 *            the NBT data of the tile entity
	 */
	public NBTMessage(int xCoord, int yCoord, int zCoord, NBTTagCompound nbt) {
		super();
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.nbt = nbt;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity();
		try {
			packet.readPacketData(new PacketBuffer(buf));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.xCoord = packet.func_148856_c();
		this.yCoord = packet.func_148855_d();
		this.zCoord = packet.func_148854_e();
		this.nbt = packet.func_148857_g();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(
				this.xCoord, this.yCoord, this.zCoord, 1, this.nbt);
		PacketBuffer pb = new PacketBuffer(buf);
		try {
			packet.writePacketData(pb);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
}
