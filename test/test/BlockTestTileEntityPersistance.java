package test;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTestTileEntityPersistance extends BlockContainer {

	protected BlockTestTileEntityPersistance() {
		super(Material.rock);
		this.setBlockName("persTest");
		this.setCreativeTab(Test.tabCustom);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPersistanceTest();
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}
	
	
}
