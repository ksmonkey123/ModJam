package ch.modjam.generic;

import ch.modjam.generic.tileEntity.IHasGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * This GUI handler is used for interaction with TileEntities that implement the
 * <tt>ch.modjam.tileEntity.IHasGui</tt> interface.
 * 
 * @author andreas
 * 
 */
public class GenericGuiHandler implements IGuiHandler {

	/**
	 * This method manages the opening of GUI's
	 * 
	 * @param player
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return <tt>true</tt> if the tileEntity is actually marked to possess a
	 *         GUI, <tt>false</tt> otherwise.
	 */
	public static boolean openGUI(EntityPlayer player, World world, int x,
			int y, int z) {
		if (!(world.getTileEntity(x, y, z) instanceof IHasGui))
			return false;
		player.openGui(GenericMod.instance, 0, world, x, y, z);
		return true;
	}

	private static Object getGuiElement(EntityPlayer player, World world,
			int x, int y, int z, boolean isClientSide) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (!(te instanceof IHasGui))
			return null;
		IHasGui entity = (IHasGui) te;
		if (isClientSide)
			return entity.getGuiClient(player.inventory);
		return entity.getGuiServer(player.inventory);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return getGuiElement(player, world, x, y, z, true);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return getGuiElement(player, world, x, y, z, false);
	}
}
