package ch.modjam.generic;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

/**
 * @author j
 *
 */
public class PlayerUtils {

	/**
	 * @param name
	 * @return true if the player is online
	 */
	public static boolean isPlayerOnlineInWorld(String name) {
		return getPlayerByName(name) != null;
	}

	/**
	 * <b>note:</b>: this works clientside, but modification for other players will need to be
	 * synchronized with the server
	 * 
	 * @param name
	 * @return the player object, if it doesn't exist, null is returned
	 */
	public static EntityPlayer getPlayerByName(String name) {
		List<Object> entities = Minecraft.getMinecraft().theWorld.playerEntities;
		for (Object x : entities) {
			if (x instanceof EntityPlayer) {
				EntityPlayer xPlayer = (EntityPlayer) x;
				if (xPlayer.getCommandSenderName().equals(name))
					return xPlayer;
			}
		}
		return null;
	}

	/**
	 * @param name
	 * @return the object of the player
	 */
	public static EntityPlayer getPlayerByNameOnServer(String name) {
		MinecraftServer s = MinecraftServer.getServer();
		return s.getConfigurationManager().getPlayerForUsername(name);
	}

}
