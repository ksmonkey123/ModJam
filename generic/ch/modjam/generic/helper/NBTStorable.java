package ch.modjam.generic.helper;

import net.minecraft.nbt.NBTTagCompound;

public interface NBTStorable {

	public void readNBT(NBTTagCompound tag);

	public void writeNBT(NBTTagCompound tag);
}
