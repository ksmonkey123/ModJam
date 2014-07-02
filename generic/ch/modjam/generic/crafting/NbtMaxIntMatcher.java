package ch.modjam.generic.crafting;

import net.minecraft.nbt.NBTTagCompound;

/**
 * matches if one tag is smaller than a maximal set value
 * 
 * @author judos
 *
 */
public class NbtMaxIntMatcher implements NbtValueMatcher {

	private String	tag;
	private int		maxValue;

	/**
	 * @param tag name of the tag to test
	 * @param maxValue the maximal value which should be reached with crafting
	 */
	public NbtMaxIntMatcher(String tag, int maxValue) {
		this.tag = tag;
		this.maxValue = maxValue;
	}

	@Override
	public boolean matchesNBT(NBTTagCompound c) {
		return c.getInteger(this.tag) <= this.maxValue;
	}

}
