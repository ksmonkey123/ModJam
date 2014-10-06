package ch.modjam.generic.networking;

import io.netty.buffer.ByteBuf;

import java.io.Serializable;

import ch.modjam.generic.tileEntity.GenericTileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

/**
 * This message type is used to send commands from a client to the server. Commands are identified
 * over their TileEntity
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class CommandMessage implements IMessage {

	private int		xCoord;
	private int		yCoord;
	private int		zCoord;
	private int		dimension;
	private String	command;
	private byte[]	data;

	/**
	 * Empty Constructor. for internal use only
	 */
	public CommandMessage() {
		this.command = "";
	}

	/**
	 * @param tileEntity
	 * @param command
	 * @param data null objects are allowed and will be serialized as empty byte arrays
	 * @throws SerializerException
	 */
	public CommandMessage(GenericTileEntity tileEntity, String command, Serializable data)
			throws SerializerException {
		this(tileEntity, command, object2data(data));
	}

	public static byte[] object2data(Object obj) throws SerializerException {
		if (obj == null)
			return new byte[0];
		return Serializer.object2Bytes(obj);
	}

	/**
	 * Basic Constructor
	 * 
	 * @param tileEntity
	 * @param command
	 * @param data
	 */
	protected CommandMessage(GenericTileEntity tileEntity, String command, byte... data) {
		this.xCoord = tileEntity.xCoord;
		this.yCoord = tileEntity.yCoord;
		this.zCoord = tileEntity.zCoord;
		this.dimension = tileEntity.getWorld().provider.dimensionId;
		this.command = command;
		this.data = data;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.xCoord = buf.readInt();
		this.yCoord = buf.readInt();
		this.zCoord = buf.readInt();
		this.dimension = buf.readInt();
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
		buf.writeInt(this.dimension);
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
		return this.dimension;
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

	public Object getObjectData() throws SerializerException {
		if (this.data.length == 0)
			return null;
		return Serializer.bytes2object(this.data);
	}

}
