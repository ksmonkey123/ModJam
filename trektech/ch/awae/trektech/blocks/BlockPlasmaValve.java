package ch.awae.trektech.blocks;

import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.Handler;
import ch.awae.trektech.TrekTech;
import ch.awae.trektech.entities.TileEntityPlasmaValve;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Pressure Valve Block
 * 
 * @see TileEntityPlasmaValve
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
public class BlockPlasmaValve extends BlockContainer {

	private final EnumPlasmaTypes plasmaType;

	/**
	 * Basic Constructor
	 * 
	 * @param id
	 * @param plasmaType
	 */
	public BlockPlasmaValve(String id, EnumPlasmaTypes plasmaType) {
		super(Material.rock);
		setBlockTextureName(TrekTech.MODID + ":" + id);
		setBlockName(id);
		setCreativeTab(TrekTech.tabCustom);
		this.plasmaType = plasmaType;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPlasmaValve(this.plasmaType, 10);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int face, float p_149727_7_,
			float p_149727_8_, float p_149727_9_) {
		if (Handler.handleToolRight(player, world, x, y, z))
			return true;
		player.openGui(TrekTech.instance, 1, world, x, y, z);
		return true;
	}

}
