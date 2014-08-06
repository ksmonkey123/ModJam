package ch.modjam.generic;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * @author judos
 *
 */
public class PlayerUtils {

	/**
	 * @param name
	 * @return true if the player is online
	 */
	public static boolean isPlayerOnlineInWorld(String name) {
		World w = Minecraft.getMinecraft().theWorld;
		return getPlayerByName(name, w) != null;
	}

	/**
	 * <b>note:</b>: this works clientside, but modification for other players will need to be
	 * synchronized with the server
	 * 
	 * @param name
	 * @param serverWorld
	 * @return the player object, if it doesn't exist, null is returned
	 */
	public static EntityPlayer getPlayerByName(String name, World serverWorld) {
		return serverWorld.getPlayerEntityByName(name);
	}

}
