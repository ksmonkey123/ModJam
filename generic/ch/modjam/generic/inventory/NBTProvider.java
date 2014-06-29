package ch.modjam.generic.inventory;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author judos
 * 
 */
public interface NBTProvider {

	/**
	 * @return the current nbt tag compound where all information is stored
	 */
	public NBTTagCompound getNBT();

}
