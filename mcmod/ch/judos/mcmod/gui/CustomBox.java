package ch.judos.mcmod.gui;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CustomBox extends BlockContainer {

	public CustomBox() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		// TODO Auto-generated method stub
		return null;
	}

}
