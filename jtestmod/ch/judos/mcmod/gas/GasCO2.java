package ch.judos.mcmod.gas;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author j
 *
 */
public class GasCO2 extends BlockContainer {

	/**
	 * 
	 */
	public GasCO2() {
		super(Material.rock);
		this.setTextureName(References.MOD_ID + ":" + Names.CO2);
		this.setCreativeTab(MCMod.modTab);
		this.setBlockUnbreakable();
		this.setUnlocalizedName(Names.CO2);
		this.setLightOpacity(1);
	}

	/**
	 * @return the name and path of the texture used
	 */
	public static String getTexture() {
		return References.MOD_ID + ":textures/blocks/" + Names.CO2 + ".png";
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	/**
	 * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public int getRenderType() {
		return -1; // custom render type
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new GasCO2TileEntity();
	}

}
