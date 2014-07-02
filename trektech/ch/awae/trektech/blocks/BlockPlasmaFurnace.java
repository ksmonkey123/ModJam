package ch.awae.trektech.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ch.awae.trektech.TrekTech;
import ch.awae.trektech.entities.TileEntityPlasmaFurnace;
import ch.modjam.generic.gui.GenericGuiHandler;

/**
 * @author andreas
 * 
 */
public class BlockPlasmaFurnace extends BlockContainer {

	/**
     * 
     */
	public BlockPlasmaFurnace() {
		super(Material.rock);
		this.setCreativeTab(TrekTech.tabCustom);
		this.setBlockName("plasmaFurnace");
		this.setBlockTextureName("trektech:plasma_furnace");
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPlasmaFurnace();
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int s, float fx,
			float fy, float fz) {
		return GenericGuiHandler.openGUI(p, w, x, y, z);
	}

}
