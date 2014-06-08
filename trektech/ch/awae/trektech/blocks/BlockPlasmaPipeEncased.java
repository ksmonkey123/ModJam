package ch.awae.trektech.blocks;

import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.TrekTech;
import ch.awae.trektech.entities.TileEntityPlasmaPipe;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPlasmaPipeEncased extends BlockContainer {

	public BlockPlasmaPipeEncased() {
		super(Material.rock);
		setHardness(10);
		setBlockName("plasmaPipeEncased");
		setCreativeTab(TrekTech.tabCustom);
		useNeighborBrightness = true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPlasmaPipe(EnumPlasmaTypes.NEUTRAL, 8, 8);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

}
