package ch.modjam.generic.networking;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * Client-side handler for TileEntity updates
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
public class NBTMessageClientHandler implements
		IMessageHandler<NBTMessage, IMessage> {

	@Override
	public IMessage onMessage(NBTMessage message, MessageContext ctx) {
		
		return null;
	}

}
