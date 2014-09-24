package ch.awae.trektech.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.Handler;
import ch.awae.trektech.TrekTech;
import ch.awae.trektech.entities.TileEntityPlasmaPipe;

/**
 * General Plasma Pipe
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class BlockPlasmaPipe extends BlockContainer {

	private EnumPlasmaTypes	plasma;
	private String			texture;
	private float			radius;
	private boolean			useRenderer;

	/**
	 * Constructor for normal pipes
	 * 
	 * @param id the pipe id
	 * @param plasma the plasma type of the pipe
	 * @param radius the render radius of the pipe
	 */
	public BlockPlasmaPipe(String id, EnumPlasmaTypes plasma, float radius) {
		super(Material.rock);
		setHardness(10);
		setUnlocalizedName(id);
		setCreativeTab(TrekTech.tabCustom);
		// useNeighborBrightness = true;
		this.texture = id;
		this.radius = radius;
		this.plasma = plasma;
		this.useRenderer = true;
		setTextureName(TrekTech.MODID + ":" + this.texture);
		setLightOpacity(0);
	}

	/**
	 * Constructor for encased pipes
	 * 
	 * @param id the pipe id
	 * @param plasma the plasma type of the pipe
	 */
	public BlockPlasmaPipe(String id, EnumPlasmaTypes plasma) {
		super(Material.rock);
		setHardness(15);
		setUnlocalizedName(id);
		setCreativeTab(TrekTech.tabCustom);
		this.useRenderer = false;
		this.plasma = plasma;
		this.texture = null;
		this.radius = 0;
		setTextureName(TrekTech.MODID + ":" + id);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPlasmaPipe(this.plasma, this.texture, this.radius);
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

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side,
			float f1, float f2, float f3) {
		return Handler.handleWrenching(player, w, x, y, z);
	}
}
