package ch.modjam.generic.crafting;

import net.minecraft.nbt.NBTTagCompound;

/**
 * provides a method to check whether the nbt data matches a given assumption.<br>
 * Can be used to add maximal values for nbt item recipes
 * 
 * @author j
 *
 */
public interface NbtValueMatcher {

	/**
	 * @param c
	 * @return whether this nbt data matches the assumption
	 */
	public boolean matchesNBT(NBTTagCompound c);

}
