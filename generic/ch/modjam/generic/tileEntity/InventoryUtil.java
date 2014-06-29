package ch.modjam.generic.tileEntity;

import java.lang.reflect.Method;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.launchwrapper.Launch;

/**
 * @author judos
 * 
 */
public class InventoryUtil {

	private static Method	addSlotMethod	= null;

	/**
	 * adds all slots of the player to your container
	 * 
	 * @param c the container where the player inventory should be bound to
	 * @param ip the inventory of the player
	 */
	public static void bindPlayerInventory(Container c, InventoryPlayer ip) {
		try {
			if (addSlotMethod == null)
				init();
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 9; j++)
					addSlotMethod.invoke(c, new Slot(ip, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

			for (int i = 0; i < 9; i++)
				addSlotMethod.invoke(c, new Slot(ip, i, 8 + i * 18, 142));

		} catch (Exception e) {
			e.printStackTrace();
			System.err
				.println("bindPlayerInventory with reflection failed, probably some GUI is not going to work.");
		}
	}

	/** the above method is the same as using the following inside the container: */
	// private void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
	// for (int i = 0; i < 3; i++)
	// for (int j = 0; j < 9; j++)
	// addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
	// for (int i = 0; i < 9; i++)
	// addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
	// }

	private static void init() throws NoSuchMethodException, SecurityException {
		boolean deobfsc = ((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")).booleanValue();
		if (deobfsc)
			addSlotMethod = Container.class.getDeclaredMethod("addSlotToContainer", Slot.class);
		else
			addSlotMethod = Container.class.getDeclaredMethod("func_75146_a", Slot.class);
		addSlotMethod.setAccessible(true);
	}
}
