package testj.customrender;

import testj.TutorialMod;
import testj.lib.Names;
import testj.lib.References;
import ch.awae.trektech.TrekTech;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCarvedDirt extends BlockContainer {
	
	public static final int RENDER_TYPE_CUSTOM = -1;

	public BlockCarvedDirt() {
		super(Material.ground);
		this.setBlockName(Names.CarvedDirt);
		this.setBlockTextureName(References.MOD_ID + ":" + Names.CarvedDirt);
		this.setCreativeTab(TutorialMod.modTab);
		setLightOpacity(0);
	}

	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RENDER_TYPE_CUSTOM;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}


	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TECarvedDirt();
	}

}
