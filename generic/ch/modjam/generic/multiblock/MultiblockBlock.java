package ch.modjam.generic.multiblock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public abstract class MultiblockBlock extends BlockContainer {

	protected MultiblockBlock(Material material) {
		super(material);
	}

	@Override
	public final TileEntity createNewTileEntity(World var1, int var2) {
		return this.getCustomTileEntity(var1, var2);
	}

	/**
	 * @param var1
	 * @param var2
	 * @return a custom TileEntity class for the block.
	 */
	public abstract MultiblockTileEntity getCustomTileEntity(World var1, int var2);

	/**
	 * set the activation state of the block
	 * 
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 * @param isActive
	 */
	public static void setActivationState(World w, int x, int y, int z, boolean isActive) {
		w.setBlockMetadataWithNotify(x, y, z, isActive ? 1 : 0, 0);
		MultiblockBlock block = (MultiblockBlock) w.getBlock(x, y, z);
		if (isActive) {
			block.onActivation(w, x, y, z);
		} else {
			block.onDeactivation(w, x, y, z);
		}
	}

	/**
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 * @return <tt>true</tt> if the block is registered as active, <tt>false</tt> otherwise
	 */
	public static boolean getActivationState(World w, int x, int y, int z) {
		if (!(w.getBlock(x, y, z) instanceof MultiblockBlock)) {
			System.err.println("getActivationState for non MultiblockBlock was called.");
			return false;
		}
		return w.getBlockMetadata(x, y, z) > 1;
	}

	/**
	 * this method is invoked whenever the block is activated
	 * 
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 */
	public abstract void onActivation(World w, int x, int y, int z);

	/**
	 * this method is invoked whenever the block is activated
	 * 
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 */
	public abstract void onDeactivation(World w, int x, int y, int z);

	@Override
	public void onPostBlockPlaced(World w, int x, int y, int z, int meta) {
		MultiblockHelper.constructMultiblock(w, x, y, z);
	}

	@Override
	public void breakBlock(World w, int x, int y, int z, Block b, int par6) {
		MultiblockHelper.deconstructMultiblock(w, x, y, z);
		super.breakBlock(w, x, y, z, b, par6);
	}
}
