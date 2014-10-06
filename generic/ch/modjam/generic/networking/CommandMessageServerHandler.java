package ch.modjam.generic.networking;

import net.minecraft.tileentity.TileEntity;
import ch.modjam.generic.tileEntity.GenericTileEntity;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 * Server side handler for Command Messages. This will propagate the command and its data to the
 * correct Tile Entity.
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class CommandMessageServerHandler implements IMessageHandler<CommandMessage, IMessage> {

	@Override
	public IMessage onMessage(CommandMessage message, MessageContext ctx) {
		// retrieve Tile Entity
		TileEntity te = FMLCommonHandler.instance().getMinecraftServerInstance()
			.worldServerForDimension(message.getDimension()).getTileEntity(message.getXCoord(),
				message.getYCoord(), message.getZCoord());
		if (!(te instanceof GenericTileEntity)) {
			System.err.println("TileEntity is no GenericTileEntity");
			return null;
		}
		GenericTileEntity entity = (GenericTileEntity) te;
		// process
		if (!"".equals(message.getCommand()))
			try {
				entity.onNetworkCommand(message.getCommand(), message.getObjectData());
			} catch (SerializerException e) {
				e.printStackTrace();
			}

		te.getWorld().markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
		// finish
		return null;
	}
}