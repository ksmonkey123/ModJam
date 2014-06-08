package ch.awae.trektech.entities;

import ch.awae.trektech.EnumPlasmaTypes;
import net.minecraftforge.common.util.ForgeDirection;

public interface IPlasmaPipe {

	/**
	 * Indicates whether or not the pipe connects to the neighbor in a certain
	 * direction. For an input of ForgeDirection.UNKNOWN this must always return
	 * false!
	 * 
	 * @param direction
	 *            the direction of the neighbor to be checked.
	 * @return whether or not the pipe connects.
	 */
	public boolean connectsTo(ForgeDirection direction);

	/**
	 * The ID (index) of the pipe texture in the pipe texture file. The textures
	 * are indexed in standard reading direction (Left-Right, Top-Down)
	 * 
	 * @return the texture ID
	 */
	public int getTextureID();

	/**
	 * The radius of the pipe in pixels. Note that a full block is 16px.
	 * 
	 * @return the pipe radius
	 */
	public float getPipeRadius();
	

}
