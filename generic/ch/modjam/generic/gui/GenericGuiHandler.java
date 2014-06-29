package ch.modjam.generic.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ch.modjam.generic.GenericMod;
import ch.modjam.generic.inventory.IHasGui;
import ch.modjam.generic.inventory.IItemHasGui;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * This GUI handler is used for interaction with TileEntities that implement the
 * <tt>ch.modjam.tileEntity.IHasGui</tt> interface.
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 * 
 */
public class GenericGuiHandler implements IGuiHandler {

	/**
	 * identifier which is sent for all gui's that are opened on tile entities
	 */
	public static final int	GUI_ID_TILE_ENTITY	= 0;
	/**
	 * identifier which is sent for all gui's that are opened for items
	 */
	public static final int	GUI_ID_ITEM			= 1;

	/**
	 * Manages the opening of a Gui for a tileEntity
	 * 
	 * @param player
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return <tt>true</tt> if the tileEntity is actually marked to possess a GUI, <tt>false</tt>
	 *         otherwise.
	 */
	public static boolean openGUI(EntityPlayer player, World world, int x, int y, int z) {
		if (!(world.getTileEntity(x, y, z) instanceof IHasGui))
			return false;
		player.openGui(GenericMod.instance, GUI_ID_TILE_ENTITY, world, x, y, z);
		return true;
	}

	/**
	 * Opens a gui for the provided item, if it implements IHasGui
	 * 
	 * @param player
	 * @param world
	 * @param item
	 * @return <tt>true</tt> when the gui could be found and opened
	 */
	public static boolean openItemGUI(EntityPlayer player, World world, ItemStack item) {
		int slot = player.inventory.currentItem;
		if (player.inventory.mainInventory[slot] != item)
			new Exception("Item provided was not found correctly").printStackTrace();
		if (!(item.getItem() instanceof IItemHasGui))
			return false;
		player.openGui(GenericMod.instance, GUI_ID_ITEM, world, slot, 0, 0);
		return true;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return getGuiElement(ID, player, world, x, y, z, true);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return getGuiElement(ID, player, world, x, y, z, false);
	}

	private Object getGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z,
			boolean isClientSide) {
		switch (ID) {
			case GUI_ID_TILE_ENTITY:
				return getGuiElement(player, world, x, y, z, isClientSide);
			case 1:
				return getItemGuiElement(player, world, x, isClientSide);
			default:
				return null;
		}
	}

	private Object getItemGuiElement(EntityPlayer player, World world, int slot,
			boolean isClientSide) {
		ItemStack stack = player.inventory.mainInventory[slot];
		if (!(stack.getItem() instanceof IItemHasGui))
			return null;
		IItemHasGui entity = (IItemHasGui) stack.getItem();
		if (isClientSide)
			return entity.getGuiClient(player.inventory, stack, slot);
		return entity.getGuiServer(player.inventory, stack, slot);
	}

	private static Object getGuiElement(EntityPlayer player, World world, int x, int y, int z,
			boolean isClientSide) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (!(te instanceof IHasGui))
			return null;
		IHasGui entity = (IHasGui) te;
		if (isClientSide)
			return entity.getGuiClient(player.inventory);
		return entity.getGuiServer(player.inventory);
	}

}
