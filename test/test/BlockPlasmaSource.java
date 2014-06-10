package test;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPlasmaSource extends BlockContainer {

	public BlockPlasmaSource() {
		super(Material.rock);
		setBlockName("plasmaTestSource");
		setCreativeTab(Test.tabCustom);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPlasmaSource();
	}

}
