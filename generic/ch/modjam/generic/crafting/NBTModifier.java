package ch.modjam.generic.crafting;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author judos
 *
 */
public interface NbtModifier {

	/**
	 * @param c the data of the itemStack
	 */
	public void modifyNBT(NBTTagCompound c);
}
