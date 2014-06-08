package ch.awae.trektech.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.TrekTech;
import ch.awae.trektech.entities.TileEntityPlasmaPipe;

public class BlockPlasmaPipeMediumEnergy extends BlockContainer {

	public BlockPlasmaPipeMediumEnergy() {
		super(Material.rock);
		setHardness(10);
		setBlockName("plasmaPipeMediumEnergy");
		setCreativeTab(TrekTech.tabCustom);
		useNeighborBrightness = true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPlasmaPipe(EnumPlasmaTypes.MEDIUM, 3, 6);
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
