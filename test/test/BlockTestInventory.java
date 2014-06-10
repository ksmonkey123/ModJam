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

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int metadata, float what, float these,
			float are) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking()) {
			return false;
		}
		// code to open gui explained later
		player.openGui(Test.instance, 0, world, x, y, z);
		return true;
	}

}
