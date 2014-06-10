package ch.awae.trektech.blocks;

import ch.awae.trektech.TrekTech;
import ch.awae.trektech.entities.TileEntityPlasmaSource;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPlasmaSource extends BlockContainer {

	public BlockPlasmaSource() {
		super(Material.rock);
		setBlockTextureName(TrekTech.MODID + ":machine_basic");
		setBlockName("plasmaSource");
		setCreativeTab(TrekTech.tabCustom);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPlasmaSource();
	}
}
