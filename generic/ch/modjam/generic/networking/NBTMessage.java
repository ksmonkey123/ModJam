package ch.modjam.generic.networking;

import java.io.IOException;

import ch.modjam.generic.GenericTileEntity;
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

	private int xCoord, yCoord, zCoord, dimens;
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
	 * @param te
	 *            the tile entity to update
	 * @param xCoord
	 *            the x coordinate of the tile entity
	 * @param yCoord
	 *            the y coordinate of the tile entity
	 * @param zCoord
	 *            the z coordinate of the tile entity
	 * @param nbt
	 *            the NBT data of the tile entity
	 */
	public NBTMessage(GenericTileEntity te) {
		super();
		this.xCoord = te.xCoord;
		this.yCoord = te.yCoord;
		this.zCoord = te.zCoord;
		this.dimens = te.getWorldObj().provider.dimensionId;
		this.nbt = new NBTTagCompound();
		te.writeToNBT(this.nbt);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.dimens = buf.readInt();
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
		buf.writeInt(this.dimens);
		S35PacketUpdateTileEntity packet = new S35PacketUpdateTileEntity(
				this.xCoord, this.yCoord, this.zCoord, 1, this.nbt);
		PacketBuffer pb = new PacketBuffer(buf);
		try {
			packet.writePacketData(pb);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the xCoord
	 */
	public int getXCoord() {
		return this.xCoord;
	}

	/**
	 * @return the yCoord
	 */
	public int getYCoord() {
		return this.yCoord;
	}

	/**
	 * @return the zCoord
	 */
	public int getZCoord() {
		return this.zCoord;
	}

	/**
	 * @return the dimension id
	 */
	public int getDimension() {
		return this.dimens;
	}

	/**
	 * @return the nbt
	 */
	public NBTTagCompound getNbt() {
		return this.nbt;
	}

}
