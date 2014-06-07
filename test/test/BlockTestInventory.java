package test;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTestInventory extends BlockContainer {

	protected BlockTestInventory() {
		super(Material.rock);
		setCreativeTab(Test.tabCustom);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {

		return new TileEntityTestInventory();
	}
	
	
	
}
