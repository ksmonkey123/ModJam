package ch.awae.trektech.entities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPlasmaPipe extends TileEntity implements IPlasmaPipe {

	public TileEntityPlasmaPipe() {
	}

	public boolean connectsTo(ForgeDirection d) {
		TileEntity t = this.worldObj.getTileEntity(this.xCoord + d.offsetX,
				this.yCoord + d.offsetY, this.zCoord + d.offsetZ);
		return t != null && t instanceof TileEntityPlasmaPipe;
	}

	public int getTextureID() {
		return 0;
	}

	@Override
	public float getPipeRadius() {
		return 4;
	}

}
