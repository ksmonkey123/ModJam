package ch.judos.at.station.entity;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class GondolaTargetMessage implements IMessage {

	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub

	}

	public static class GondolaTargetHandler implements
			IMessageHandler<GondolaTargetMessage, GondolaTargetMessage> {

		@Override
		public GondolaTargetMessage onMessage(GondolaTargetMessage message, MessageContext ctx) {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
