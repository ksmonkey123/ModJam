package test;

import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.entities.IPlasmaConnection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPlasmaTest extends BlockContainer {

	public BlockPlasmaTest() {
		super(Material.rock);
		setCreativeTab(Test.tabCustom);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPlasma();
	}

}
