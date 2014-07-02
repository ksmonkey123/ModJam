package ch.modjam.generic.crafting;

import net.minecraft.nbt.NBTTagCompound;

/**
 * modifies one tag of the nbt data. It increases the integer value for the provided tag or sets the
 * default value if it was not set before.
 * 
 * @author judos
 *
 */
public class NbtIncreaseIntModifier implements NbtModifier {
	protected final String	tag;
	protected final int		defaultValue;
	protected final int		increase;

	/**
	 * @param tag
	 * @param defaultValue
	 * @param increase
	 */
	public NbtIncreaseIntModifier(String tag, int defaultValue, int increase) {
		this.tag = tag;
		this.defaultValue = defaultValue;
		this.increase = increase;
	}

	@Override
	public void modifyNBT(NBTTagCompound c) {
		if (c.hasKey(this.tag))
			c.setInteger(this.tag, c.getInteger(this.tag) + this.increase);
		else
			c.setInteger(this.tag, this.defaultValue);
	}

}
