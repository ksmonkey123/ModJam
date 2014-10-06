package ch.modjam.generic.helper;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author j
 *
 * @param <T> the own class, used for returning the own object in the readNBT() method
 */
public interface NBTStorable<T> {

	/**
	 * @param tag
	 * @param name
	 * @return the original object for ease in use
	 */
	public T readNBT(NBTTagCompound tag, String name);

	public void writeNBT(NBTTagCompound tag, String name);
}
