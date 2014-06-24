package test;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

@SuppressWarnings("javadoc")
public class TestMessage implements IMessage {

	public String data;

	public TestMessage(String message) {
		this.data = message;
	}

	public TestMessage() {
		this.data = "";
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.data = "";
		try {
			while (true) {
				this.data += buf.readChar();
			}
		} catch (IndexOutOfBoundsException ex) {
			// indicates EOL
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		for (int i = 0; i < this.data.length(); i++) {
			buf.writeChar(this.data.charAt(i));
		}
	}

}
