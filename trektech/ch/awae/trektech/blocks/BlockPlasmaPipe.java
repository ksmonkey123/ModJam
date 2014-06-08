package ch.awae.trektech.blocks;

import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.TrekTech;
import ch.awae.trektech.entities.TileEntityPlasmaPipe;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPlasmaPipe extends BlockContainer {

	private EnumPlasmaTypes plasma;
	private int textureID;
	private float radius;

	public BlockPlasmaPipe(String id, EnumPlasmaTypes plasma, int textureID,
			float radius) {
		super(Material.rock);
		setHardness(10);
		setBlockName(id);
		setCreativeTab(TrekTech.tabCustom);
		useNeighborBrightness = true;
		this.textureID = textureID;
		this.radius = radius;
		this.plasma = plasma;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPlasmaPipe(this.plasma, this.textureID,
				this.radius);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

}
