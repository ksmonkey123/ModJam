package ch.judos.mcmod.gui;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;

public class Box extends BlockContainer {

	public Box() {
		super(Material.iron);

		this.setCreativeTab(MCMod.modTab);
		this.setBlockName(Names.Box);
		this.setBlockTextureName(References.MOD_ID + ":" + Names.Box);
		this.setHardness(0.2f);
		this.setHarvestLevel("pickaxe", 0);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new BoxTE();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
			int side, float sideX, float sideY, float sideZ) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking())
			return false;

		player.openGui(MCMod.instance, References.GUI_BOX, world, x, y, z);
		return true;
	}

}
