package ch.modjam.generic.networking;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ch.modjam.generic.GenericTileEntity;
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
		// check if in dimension (only update if in dimension)
		World w = Minecraft.getMinecraft().theWorld;
		if (w.provider.dimensionId != message.getDimension())
			return null;
		// find tile entity
		TileEntity te = w.getTileEntity(message.getXCoord(),
				message.getYCoord(), message.getZCoord());
		if (!(te instanceof GenericTileEntity))
			return null;
		GenericTileEntity entity = (GenericTileEntity) te;
		entity.onNetworkUpdate(message.getNbt());
		// finish
		return null;
	}

}
