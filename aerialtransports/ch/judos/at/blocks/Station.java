package ch.judos.at.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import ch.judos.at.ModMain;
import ch.judos.at.lib.ATNames;
import ch.judos.at.lib.ATReferences;
import ch.judos.at.te.TEStation;
import ch.modjam.generic.rendering.customRenderer.RenderType;

public class Station extends BlockContainer {

	public Station() {
		super(Material.wood);
		this.setBlockName(ATNames.station);
		this.setBlockTextureName(ATReferences.MOD_ID + ":" + ATNames.station);
		this.setCreativeTab(ModMain.modTab);
		this.setHardness(0.3f);
		this.setHarvestLevel("axe", 0);
		// this.setLightOpacity(0);
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side,
			float hitX, float hitY, float hitZ) {
		TileEntity te = w.getTileEntity(x, y, z);
		if (te instanceof TEStation) {
			ModMain.logger.error("onBlockActivated: " + player);
			TEStation te2 = (TEStation) te;
			te2.onBlockActivated(player);
			return true;
		}
		return false;
	}

	public IIcon getBlockIcon() {
		return this.blockIcon;
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
