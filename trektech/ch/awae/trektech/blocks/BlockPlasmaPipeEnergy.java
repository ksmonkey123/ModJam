package ch.awae.trektech.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.TrekTech;
import ch.awae.trektech.entities.TileEntityPlasmaPipe;

public class BlockPlasmaPipeEnergy extends BlockContainer {

	public BlockPlasmaPipeEnergy() {
		super(Material.rock);
		setHardness(10);
		setBlockName("plasmaPipeEnergy");
		setCreativeTab(TrekTech.tabCustom);
		useNeighborBrightness = true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPlasmaPipe(EnumPlasmaTypes.LOW, 1, 4);
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
