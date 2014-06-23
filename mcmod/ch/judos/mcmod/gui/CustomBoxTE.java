package ch.judos.mcmod.gui;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import ch.judos.mcmod.lib.Names;

/**
 * @author j
 */
public class CustomBoxTE extends BoxTE {

	/**
	 * 
	 */
	public CustomBoxTE() {
		this.stack = new ItemStack[2];
	}

	@Override
	public String getInventoryName() {
		return StatCollector.translateToLocal("tile." + Names.CustomBox
				+ ".name");
	}

	/**
	 * 
	 */
	public void increaseSize() {
		if (this.stack.length < 5) {
			this.stack = Arrays.copyOf(this.stack, this.stack.length + 1);
			System.out.println("TE has " + this.stack.length + " slots");
			this.markDirty();
		}
	}

	/**
	 * 
	 */
	public void decreaseSize() {
		if (this.stack.length > 1) {
			this.stack = Arrays.copyOf(this.stack, this.stack.length - 1);
			System.out.println("TE has " + this.stack.length + " slots");
			this.markDirty();
		}
	}

}
