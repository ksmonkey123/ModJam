package ch.modjam.generic;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

public class Vec3Helper {
	public static void writeVecToNBT(NBTTagCompound tag, Vec3 vector, String name) {
		tag.setDouble(name + "_x", vector.xCoord);
		tag.setDouble(name + "_y", vector.yCoord);
		tag.setDouble(name + "_z", vector.zCoord);
	}

	public static Vec3 readVecFromNBT(NBTTagCompound tag, String name) {
		if (!tag.hasKey(name + "_x"))
			return null;
		double x = tag.getDouble(name + "_x");
		double y = tag.getDouble(name + "_y");
		double z = tag.getDouble(name + "_z");
		return Vec3.createVectorHelper(x, y, z);
	}
}
