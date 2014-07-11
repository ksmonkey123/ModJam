package ch.judos.mcmod.gas;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.basemod.EntityUpdateListener;

/**
 * @author judos
 *
 */
public class GasCreator implements EntityUpdateListener {

	@Override
	public void updateEntity(TileEntity te) {
		TileEntityFurnace f = (TileEntityFurnace) te;
		if (f.isBurning()) {
			World w = te.getWorldObj();
			if (w.isAirBlock(te.xCoord, te.yCoord + 1, te.zCoord)) {
				w.setBlock(te.xCoord, te.yCoord + 1, te.zCoord, MCMod.gas, 0, 3);
			}
		}
	}

}
