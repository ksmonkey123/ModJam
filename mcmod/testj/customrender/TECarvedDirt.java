package testj.customrender;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import testj.TutorialMod;
import testj.lib.Names;
import testj.lib.References;
import ch.modjam.generic.GenericTileEntity;

public class TECarvedDirt extends GenericTileEntity implements IConnecting {
	
	public TECarvedDirt() {
		
	}

	@Override
	public void tick() {
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
	}

	public static String getTexture() {
		return References.MOD_ID + ":textures/blocks/"+Names.CarvedDirt+".png";
	}

	public boolean connectsTo(ForgeDirection dir) {
		TileEntity t = this.worldObj.getTileEntity(this.xCoord + dir.offsetX,
				this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
		if (t == null || !(t instanceof ICanalConnection))
			return false;
		ICanalConnection te = (ICanalConnection) t;
		return te.acceptsLiquid()
				|| te.providesLiquid();
	}

}
