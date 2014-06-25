package ch.modjam.generic.networking;

import ch.modjam.generic.GenericMod;
import ch.modjam.generic.tileEntity.GenericTileEntity;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * Server side handler for Command Messages. This will propagate the command and
 * its data to the correct Tile Entity.
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
public class CommandMessageServerHandler implements
		IMessageHandler<CommandMessage, IMessage> {

	@Override
	public IMessage onMessage(CommandMessage message, MessageContext ctx) {
		// retreive Tile Entity
		TileEntity te = FMLCommonHandler
				.instance()
				.getMinecraftServerInstance()
				.worldServerForDimension(message.getDimension())
				.getTileEntity(message.getXCoord(), message.getYCoord(),
						message.getZCoord());
		if (!(te instanceof GenericTileEntity)) {
			System.err.println("TileEntity is no GenericTileEntity");
			return null;
		}
		GenericTileEntity entity = (GenericTileEntity) te;
		// process
		if (message.getCommand() != "")
			entity.onNetworkCommand(message.getCommand(), message.getData());
		NBTMessage updateMessage = new NBTMessage(entity);
		GenericMod.NETWORK.sendToAll(updateMessage);
		// finish
		return null;
	}

}