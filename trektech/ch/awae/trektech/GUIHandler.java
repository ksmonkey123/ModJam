package ch.awae.trektech;

import ch.awae.trektech.entities.TileEntityPlasmaSource;
import ch.awae.trektech.gui.GuiPlasmaSource;
import ch.awae.trektech.gui.container.ContainerPlasmaSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
public class GUIHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		switch (id) {
		case 0: {
			if (tileEntity instanceof TileEntityPlasmaSource) {
				return new ContainerPlasmaSource(player.inventory,
						(TileEntityPlasmaSource) tileEntity);
			}
		}
			break;
		default:
			return null;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		switch (id) {
		case 0: {
			if (tileEntity instanceof TileEntityPlasmaSource) {
				return new GuiPlasmaSource(player.inventory,
						(TileEntityPlasmaSource) tileEntity);
			}
		}
			break;
		default:
			return null;
		}
		return null;
	}
}
