package ch.modjam.generic.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * This Block can be placed in any of the 4 compass directions and possesses 2 states. It can also
 * possess different icons for each side and state.
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 * @author judos
 */
public abstract class BlockGenericDualStateDirected extends BlockContainer {

	IIcon			icons[][];
	IIconRegister	iconRegister;

	/**
	 * basic constructor
	 * 
	 * @param material
	 * @see Block
	 */
	public BlockGenericDualStateDirected(Material material) {
		super(material);
		this.icons = new IIcon[6][2]; // 2 entries per side (on / off)
	}

	@Override
	public final void onBlockAdded(World w, int x, int y, int z) {
		super.onBlockAdded(w, x, y, z);
		orient(w, x, y, z);
		this.onBlockAddedToWorld(w, x, y, z);
	}

	/**
	 * Override this method instead of onBlockAdded(). It will be invoked at the end of
	 * onBlockAdded()
	 * 
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 */
	public void onBlockAddedToWorld(World w, int x, int y, int z) {
		// Default: no actions required
	}

	private static void orient(World w, int x, int y, int z) {
		if (!w.isRemote) {
			Block block0 = w.getBlock(x, y, z - 1);
			Block block1 = w.getBlock(x, y, z + 1);
			Block block2 = w.getBlock(x - 1, y, z);
			Block block3 = w.getBlock(x + 1, y, z);

			byte meta = 3;

			if (block0.func_149730_j() && !block1.func_149730_j())
				meta = 3;
			if (block1.func_149730_j() && !block0.func_149730_j())
				meta = 2;
			if (block2.func_149730_j() && !block3.func_149730_j())
				meta = 5;
			if (block3.func_149730_j() && !block2.func_149730_j())
				meta = 4;

			w.setBlockMetadataWithNotify(x, y, z, meta, 2);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final IIcon getIcon(int side, int meta) {
		int flag = meta >= 10 ? 1 : 0;
		int metaRoot = meta % 10;
		if (metaRoot == 0)
			metaRoot = 3;
		return this.icons[getFaceOfSide(side, metaRoot).ordinal()][flag];
	}

	private static EFace getFaceOfSide(int side, int meta) {
		if (side == 0)
			return EFace.BOTTOM;
		if (side == 1)
			return EFace.TOP;
		if (side == meta)
			return EFace.FRONT;
		if (side == meta + (meta % 2 == 0 ? 1 : -1))
			return EFace.BACK;
		if ((meta == 2 && side == 5) || (meta == 3 && side == 4) || (meta == 4 && side == 2) || (meta == 5 && side == 3))
			return EFace.LEFT;

		return EFace.RIGHT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public final void registerBlockIcons(IIconRegister iconReg) {
		this.iconRegister = iconReg;
		IIcon icon = this.iconRegister.registerIcon(this.getDefaultIcon());
		for (int i = 0; i < this.icons.length; i++)
			for (int j = 0; j < this.icons[i].length; j++)
				this.icons[i][j] = icon;
		this.registerIcons();
	}

	/**
	 * This method is invoked once the block is ready to receive the textures. Use this method to
	 * load all necessary icons!
	 */
	public abstract void registerIcons();

	/**
	 * Returns the identifier for the string to use on all sides with no other icon defined.
	 * 
	 * @return the default icon
	 */
	public abstract String getDefaultIcon();

	/**
	 * Set the icon for a certain face independent from state.
	 * 
	 * @param face the face to set the icon for
	 * @param icon the icon to set for the face
	 */
	public final void setIcon(EFace face, String icon) {
		this.setIcon(face, icon, icon);
	}

	/**
	 * Set different icons for the different states for a certain side. Both icons must be given at
	 * once.
	 * 
	 * @param face the face to set the icon for
	 * @param iconOff the "off" state icon
	 * @param iconOn the "on" state icon
	 */
	public final void setIcon(EFace face, String iconOff, String iconOn) {
		this.icons[face.ordinal()][0] = this.iconRegister.registerIcon(iconOff);
		this.icons[face.ordinal()][1] = this.iconRegister.registerIcon(iconOn);
	}

	/**
	 * Set the current activation state for a given block
	 * 
	 * @param active whether or not the block should be rendered as active
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void setActive(boolean active, World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		int metaNew = meta % 10;
		metaNew += active ? 10 : 0;
		if (metaNew != meta)
			world.setBlockMetadataWithNotify(x, y, z, metaNew, 2);

	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public final void onBlockPlacedBy(World w, int x, int y, int z,
			EntityLivingBase entityLivingBase, ItemStack p_149689_6_) {
		int l = MathHelper.floor_double(entityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (l == 0)
			w.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if (l == 1)
			w.setBlockMetadataWithNotify(x, y, z, 5, 2);
		if (l == 2)
			w.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if (l == 3)
			w.setBlockMetadataWithNotify(x, y, z, 4, 2);

		this.onBlockPlacedIntoWorldBy(w, x, y, z, entityLivingBase, p_149689_6_);
	}

	/**
	 * Override this method for custom action on block placement
	 * 
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 * @param entityLivingBase
	 * @param itemStack
	 */
	public void onBlockPlacedIntoWorldBy(World w, int x, int y, int z,
			EntityLivingBase entityLivingBase, ItemStack itemStack) {
		// Default: no actions required
	}

	/**
	 * determines the ForgeDirection for a certain face given a certain metadata value
	 * 
	 * @param face
	 * @param metadata
	 * @return the direction of the face
	 */
	public static ForgeDirection getFaceDirectionForMeta(EFace face, int metadata) {
		int meta = metadata % 10;
		if (meta == 0 | meta == 1)
			meta = 3;
		return EFace.getDirectionOfFace(face, ForgeDirection.getOrientation(meta).getOpposite());
	}

	/**
	 * indicates whether or not a block is currently active given its metadata
	 * 
	 * @param metadata
	 * @return true if the block is active, false otherwise
	 */
	public static boolean isActive(int metadata) {
		return metadata >= 10;
	}

}
