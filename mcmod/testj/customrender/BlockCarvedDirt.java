package testj.customrender;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import testj.TutorialMod;
import testj.lib.Names;
import testj.lib.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCarvedDirt extends BlockContainer {

	public static final int RENDER_TYPE_CUSTOM = -1;

	public BlockCarvedDirt() {
		super(Material.grass);
		this.setBlockName(Names.CarvedDirt);
		this.setBlockTextureName(References.MOD_ID + ":" + Names.CarvedDirt);
		this.setCreativeTab(TutorialMod.modTab);
		this.setHardness(5);
		setLightOpacity(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return Blocks.grass.getBlockColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int p1) {
		return Blocks.grass.getRenderColor(p1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess p1, int p2, int p3, int p4) {
		return Blocks.grass.colorMultiplier(p1, p2, p3, p4);
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random r, int p_149650_3_) {
		this.setHardness(0.3f);
		return Blocks.dirt.getItemDropped(p_149650_1_, r, p_149650_3_);
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
