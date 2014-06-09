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
	private String texture;
	private float radius;
	private boolean useRenderer;

	public BlockPlasmaPipe(String id, EnumPlasmaTypes plasma, String texture,
			float radius) {
		super(Material.rock);
		setHardness(10);
		setBlockName(id);
		setCreativeTab(TrekTech.tabCustom);
		//useNeighborBrightness = true;
		this.texture = texture;
		this.radius = radius;
		this.plasma = plasma;
		this.useRenderer = true;
		setBlockTextureName(TrekTech.MODID + ":" + texture);
		setLightOpacity(0);
	}

	public BlockPlasmaPipe(String id, EnumPlasmaTypes plasma, String texture) {
		super(Material.rock);
		setHardness(15);
		setBlockName(id);
		setCreativeTab(TrekTech.tabCustom);
		this.useRenderer = false;
		this.plasma = plasma;
		this.texture = null;
		this.radius = 0;
		setBlockTextureName(TrekTech.MODID + ":" + texture);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPlasmaPipe(this.plasma, this.texture,
				this.radius);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return !this.useRenderer;
	}

	@Override
	public int getRenderType() {
		return this.useRenderer ? -1 : super.getRenderType();
	}

	@Override
	public boolean isOpaqueCube() {
		return !this.useRenderer;
	}

}
