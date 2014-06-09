package ch.awae.trektech.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.TrekTech;
import ch.awae.trektech.entities.TileEntityPlasmaPipeCombined;

public class BlockPlasmaPipeCombined extends BlockContainer {

	private String texture;
	private float radius;
	private boolean useRenderer;

	public BlockPlasmaPipeCombined(boolean isEncased) {
		super(Material.rock);
		setHardness(10);
		setBlockName("plasmaPipeCombined" + (isEncased ? "Encased" : ""));
		setCreativeTab(TrekTech.tabCustom);
		this.texture = "conduit_combined" + (isEncased ? "_encased" : "");
		this.radius = isEncased ? 0 : 6;
		this.useRenderer = !isEncased;
		setBlockTextureName(TrekTech.MODID + ":" + texture);
		if (!isEncased)
			setLightOpacity(0);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPlasmaPipeCombined(this.texture, this.radius);
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
