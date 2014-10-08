package ch.judos.at.mast;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ch.judos.at.ATMain;
import ch.judos.at.lib.ATNames;
import ch.modjam.generic.rendering.customRenderer.RenderType;

public class BlockMast extends BlockContainer {

	public BlockMast() {
		super(Material.wood);
		this.setUnlocalizedName(ATNames.mast);
		this.setTextureName(ATMain.MOD_ID + ":" + ATNames.station + "_particles");
		this.setCreativeTab(ATMain.modTab);
		this.setHardness(0.4f);
		this.setHarvestLevel("axe", 0);
		// float b = 0.1f;
		// this.setBlockBounds(b, 0, b, 1 - b, 1, 1 - b);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderType.CUSTOM;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TEMast();
	}

}
