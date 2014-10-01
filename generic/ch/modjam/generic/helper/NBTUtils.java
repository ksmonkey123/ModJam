package ch.modjam.generic.helper;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import ch.modjam.generic.blocks.Vec3C;

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

	public static <T extends NBTStorable> void writeListToNBT(NBTTagCompound tag, List<T> list,
			String name) {
		NBTTagCompound dataTag = new NBTTagCompound();
		tag.setTag(name, dataTag);
		int index = 0;
		for (T t : list) {
			NBTTagCompound element = new NBTTagCompound();
			t.writeNBT(element);
			dataTag.setTag("" + index, element);
			index++;
		}
	}

	public static <T extends NBTStorable> ArrayList<T> readListFromNBT(NBTTagCompound tag,
			String name, Class<T> cl) {
		ArrayList<T> result = new ArrayList<T>();
		NBTTagCompound dataTag = (NBTTagCompound) tag.getTag(name);
		int index = 0;
		while (true) {
			NBTTagCompound element = (NBTTagCompound) dataTag.getTag("" + index);
			if (element == null)
				break;
			try {
				T t = cl.newInstance();
				t.readNBT(element);
				result.add(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
			index++;
		}
		return result;
	}

	public static void writeVecToNBT(NBTTagCompound tag, Vec3 vector, String name) {
		if (vector == null)
			throw new RuntimeException("couldn't write vecotr: " + vector);
		tag.setDouble(name + "_x", vector.xCoord);
		tag.setDouble(name + "_y", vector.yCoord);
		tag.setDouble(name + "_z", vector.zCoord);
	}

	public static Vec3C readVecFromNBT(NBTTagCompound tag, String name) {
		if (!tag.hasKey(name + "_x"))
			return null;
		double x = tag.getDouble(name + "_x");
		double y = tag.getDouble(name + "_y");
		double z = tag.getDouble(name + "_z");
		return new Vec3C(x, y, z);
	}

}
