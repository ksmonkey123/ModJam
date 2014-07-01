package ch.modjam.generic.crafting;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author j
 *
 */
public interface NBTModifier {

	/**
	 * @param c the data of the itemStack
	 */
	public void modifyNBT(NBTTagCompound c);
}
