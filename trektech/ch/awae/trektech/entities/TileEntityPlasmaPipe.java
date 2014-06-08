package ch.awae.trektech.entities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPlasmaPipe extends TileEntity {

	public final static float WIDTH = 8F;
	public final static int TEXTURE_ID = 0;
	
	public TileEntityPlasmaPipe() {
	}

	public boolean connectsTo(ForgeDirection d) {
		TileEntity t = this.worldObj.getTileEntity(this.xCoord + d.offsetX,
				this.yCoord + d.offsetY, this.zCoord + d.offsetZ);
		return t != null && t instanceof TileEntityPlasmaPipe;
	}

}
