package ch.phyranja.EssenceCrops.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ch.modjam.generic.blocks.BlockGenericDualStateDirected;
import ch.modjam.generic.blocks.EFace;
import ch.modjam.generic.gui.GenericGuiHandler;
import ch.phyranja.EssenceCrops.EssenceCrops;
import ch.phyranja.EssenceCrops.entities.TileEntityExtractor;
import ch.phyranja.EssenceCrops.lib.Names;
import ch.phyranja.EssenceCrops.lib.References;

public class EssenceExtractor extends BlockGenericDualStateDirected {

	public EssenceExtractor() {
		super(Material.rock);
		// XX: texture name is set twice
		this.setTextureName(References.MOD_ID + ":machine_basic");
		this.setUnlocalizedName(Names.Extractor);
		this.setTextureName(References.MOD_ID + ":" + Names.Extractor);
		this.setCreativeTab(EssenceCrops.modTab);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityExtractor();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int i) {

		super.breakBlock(world, x, y, z, block, i);
	}

	@Override
	public void registerIcons() {
		this.setIcon(EFace.FRONT, References.MOD_ID + ":e_extractor_front_off",
			References.MOD_ID + ":e_extractor_front_on");
		this.setIcon(EFace.BACK, References.MOD_ID + ":e_extractor_side");
		this.setIcon(EFace.TOP, References.MOD_ID + ":e_extractor_top");
		this.setIcon(EFace.LEFT, References.MOD_ID + ":e_extractor_side");
		this.setIcon(EFace.RIGHT, References.MOD_ID + ":e_extractor_side");
		this.setIcon(EFace.BOTTOM, References.MOD_ID + ":e_extractor_top");
	}

	@Override
	public String getDefaultIcon() {
		return References.MOD_ID + ":e_extractor_side";
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
			int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {

		return GenericGuiHandler.openGUI(player, world, x, y, z);
	}

}
