package ch.judos.mcmod.customrender;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;
import ch.modjam.generic.GenericTileEntity;

@SuppressWarnings("javadoc")
public class TECarvedDirt extends GenericTileEntity implements IConnecting,
		ICanalConnection {

	public TECarvedDirt() {

	}

	@Override
	public void tick() {
		// not required
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		// nothing to save
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		// nothing to restore
	}

	public static String getTexture() {
		return References.MOD_ID + ":textures/blocks/" + Names.CarvedDirt
				+ ".png";
	}

	@Override
	public boolean connectsTo(ForgeDirection dir) {
		int x = this.xCoord + dir.offsetX;
		int y = this.yCoord + dir.offsetY;
		int z = this.zCoord + dir.offsetZ;
		TileEntity t = this.worldObj.getTileEntity(x, y, z);
		if (t == null) {
			Block b = this.worldObj.getBlock(x, y, z);
			if (b instanceof BlockStaticLiquid)
				if (b == Blocks.water) {
					return this.worldObj.getBlockMetadata(x, y, z) == 1;
				}
		}
		if (t == null || !(t instanceof ICanalConnection))
			return false;
		ICanalConnection te = (ICanalConnection) t;
		return te.acceptsLiquid() || te.providesLiquid();
	}

	@Override
	public boolean acceptsLiquid() {
		return true;
	}

	@Override
	public boolean providesLiquid() {
		return false;
	}

	@Override
	public int getMaxLiquidAmount() {
		return 100;
	}

	@Override
	public int getCurrentLiquidAmount() {
		return 0;
	}

	@Override
	public int fillLiquid(int amount) {
		return 0;
	}

}
