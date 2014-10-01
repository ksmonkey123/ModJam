package ch.judos.at.station.rope;

import java.util.ArrayList;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ch.judos.at.ATMain;
import ch.judos.at.lib.ATNames;
import ch.judos.at.station.BlockStation;
import ch.modjam.generic.blocks.BlockCoordinates;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRope extends Block {

	public BlockRope() {
		super(Material.cloth);
		this.setCreativeTab(ATMain.modTab);
		this.setUnlocalizedName(ATNames.ropeBlock);
		this.setTextureName(ATMain.MOD_ID + ":" + ATNames.ropeBlock);
		this.setLightOpacity(0);
		// this.setBlockBounds(.3f, .3f, .3f, .7f, .7f, .7f);
		this.setBlockUnbreakable();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		HashSet<BlockCoordinates> checked = new HashSet<BlockCoordinates>();
		ArrayList<BlockCoordinates> check = new ArrayList<BlockCoordinates>();
		check.add(new BlockCoordinates(x, y, z));
		while (!check.isEmpty()) {
			BlockCoordinates checkCoords = check.remove(0);
			checked.add(checkCoords);
			Block checkBlock = world.getBlock(checkCoords.x, checkCoords.y, checkCoords.z);
			if (checkBlock instanceof BlockRope) {
				for (BlockCoordinates c : checkCoords.neighbors())
					if (!checked.contains(c))
						check.add(c);
			} else if (checkBlock instanceof BlockStation) {
				// TODO: implement
			}
		}
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public int getRenderType() {
		return 0; // 0 = standard block render type
		// -1 = custom render type
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_,
			int p_149719_3_, int p_149719_4_) {
		super.setBlockBoundsBasedOnState(p_149719_1_, p_149719_2_, p_149719_3_, p_149719_4_);
	}

	@Override
	public boolean canStopRayTrace(int p_149678_1_, boolean p_149678_2_) {
		return false;
	}

}
