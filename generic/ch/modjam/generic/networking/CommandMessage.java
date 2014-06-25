package ch.modjam.generic.networking;

import ch.modjam.generic.tileEntity.GenericTileEntity;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

/**
 * This message type is used to send commands from a client to the server.
 * Commands are identified over their TileEntity
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
public class CommandMessage implements IMessage {

	private int xCoord, yCoord, zCoord, dimens;
	private String command;
	private byte[] data;

	/**
	 * Empty Constructor. for internal use only
	 */
	public CommandMessage() {
		this.command = "";
	}

	/**
	 * Basic Constructor
	 * 
	 * @param tileEntity
	 * @param command
	 * @param data
	 */
	public CommandMessage(GenericTileEntity tileEntity, String command,
			byte... data) {
		this.xCoord = tileEntity.xCoord;
		this.yCoord = tileEntity.yCoord;
		this.zCoord = tileEntity.zCoord;
		this.dimens = tileEntity.getWorldObj().provider.dimensionId;
		this.command = command;
		this.data = data;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.xCoord = buf.readInt();
		this.yCoord = buf.readInt();
		this.zCoord = buf.readInt();
		this.dimens = buf.readInt();
		byte length = buf.readByte();
		char[] chars = new char[length];
		for (int i = 0; i < length; i++)
			chars[i] = buf.readChar();
		this.command = new String(chars);
		length = buf.readByte();
		this.data = new byte[length];
		for (int i = 0; i < length; i++)
			this.data[i] = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.xCoord);
		buf.writeInt(this.yCoord);
		buf.writeInt(this.zCoord);
		buf.writeInt(this.dimens);
		byte length = (byte) this.command.length();
		char[] chars = this.command.toCharArray();
		buf.writeByte(length);
		for (byte i = 0; i < length; i++)
			buf.writeChar(chars[i]);
		length = (byte) this.data.length;
		buf.writeByte(length);
		for (byte i = 0; i < length; i++)
			buf.writeByte(this.data[i]);
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
	 * @return the world dimension id
	 */
	public int getDimension() {
		return this.dimens;
	}

	/**
	 * @return the command
	 */
	public String getCommand() {
		return this.command;
	}

	/**
	 * @return the data
	 */
	public byte[] getData() {
		return this.data;
	}

}
