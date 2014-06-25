package test;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings("javadoc")
public class TestMessageServerHandler implements
		IMessageHandler<TestMessage, IMessage> {

	@Override
	public IMessage onMessage(TestMessage message, MessageContext ctx) {
		System.out.println("Data Received: " + message.data + " (" + ctx.side + ")");
		return null;
	}
}
