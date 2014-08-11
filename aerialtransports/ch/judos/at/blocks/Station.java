package ch.judos.at.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import ch.judos.at.ModMain;
import ch.judos.at.lib.ModNames;
import ch.judos.at.lib.References;
import ch.judos.at.te.TEStation;
import ch.modjam.generic.blocks.customRenderer.RenderType;

public class Station extends BlockContainer {

	public Station() {
		super(Material.wood);
		this.setBlockName(ModNames.station);
		this.setBlockTextureName(References.MOD_ID + ":" + ModNames.station);
		this.setCreativeTab(ModMain.modTab);
		this.setHardness(0.3f);
		this.setHarvestLevel("axe", 0);
		this.setLightOpacity(255);
	}

	public IIcon getBlockIcon() {
		return this.blockIcon;
	}

	@Override
	public String getTextureName() {
		return super.getTextureName();
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderType.CUSTOM;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TEStation();
	}

}
