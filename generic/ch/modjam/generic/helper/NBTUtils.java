package ch.modjam.generic.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import ch.modjam.generic.blocks.Vec3P;

public class NBTUtils {
	public static void writeItemStackToNBT(ItemStack stack, NBTTagCompound nbt, String identifier) {
		NBTTagCompound stackNBT = new NBTTagCompound();
		stack.writeToNBT(stackNBT);
		nbt.setTag(identifier, stackNBT);
	}

	public static ItemStack readItemStackFromNBT(NBTTagCompound nbt, String identifier) {
		NBTTagCompound tag = (NBTTagCompound) nbt.getTag(identifier);
		return ItemStack.loadItemStackFromNBT(tag);
	}

	public static void writeVecToNBT(NBTTagCompound tag, Vec3 vector, String name) {
		if (vector == null)
			throw new RuntimeException("couldn't write vecotr: " + vector);
		tag.setDouble(name + "_x", vector.xCoord);
		tag.setDouble(name + "_y", vector.yCoord);
		tag.setDouble(name + "_z", vector.zCoord);
	}

	public static Vec3P readVecFromNBT(NBTTagCompound tag, String name) {
		if (!tag.hasKey(name + "_x"))
			return null;
		double x = tag.getDouble(name + "_x");
		double y = tag.getDouble(name + "_y");
		double z = tag.getDouble(name + "_z");
		return new Vec3P(x, y, z);
	}

}
