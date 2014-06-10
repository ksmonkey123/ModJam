package test;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPlasmaDrain extends BlockContainer {

	public BlockPlasmaDrain() {
		super(Material.rock);
		setBlockName("testPlasmaDrain");
		setCreativeTab(Test.tabCustom);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPlasmaDrain();
	}

}
