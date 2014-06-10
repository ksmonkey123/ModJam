package test;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {
	// returns an instance of the Container you made earlier
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		switch (id) {
		case 0: {
			if (tileEntity instanceof TileEntityTestInventory) {
				return new ContainerTestInventory(player.inventory,
						(TileEntityTestInventory) tileEntity);
			}
		}
		default:
			return null;
		}
	}

	// returns an instance of the Gui you made earlier
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		switch (id) {
		case 0: {
			if (tileEntity instanceof TileEntityTestInventory) {
				return new GuiTestInventory(player.inventory,
						(TileEntityTestInventory) tileEntity);
			}
		}
		default:
			return null;
		}
	}
}
