package ch.modjam.generic;

import ch.awae.trektech.TrekTech;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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

public abstract class BlockGenericDualStateDirected extends BlockContainer {

	IIcon icons[][];
	IIconRegister iconRegister;

	public BlockGenericDualStateDirected(Material material) {
		super(material);
		this.icons = new IIcon[6][2]; // 2 entries per side (on / off)
	}

	@Override
	public final void onBlockAdded(World w, int x, int y, int z) {
		super.onBlockAdded(w, x, y, z);
		this.orient(w, x, y, z);
		this.onBlockAddedToWorld(w, x, y, z);
	}

	/**
	 * Override this method instead of onBlockAdded(). It will be invoked at the
	 * end of onBlockAdded()
	 * 
	 * @param w
	 * @param x
	 * @param y
	 * @param z
	 */
	public void onBlockAddedToWorld(World w, int x, int y, int z) {
	}

	private void orient(World w, int x, int y, int z) {
		if (!w.isRemote) {
			Block block0 = w.getBlock(x, y, z - 1);
			Block block1 = w.getBlock(x, y, z + 1);
			Block block2 = w.getBlock(x - 1, y, z);
			Block block3 = w.getBlock(x + 1, y, z);

			byte meta = 3;

			if (block0.func_149730_j() && !block1.func_149730_j()) {
				meta = 3;
			}

			if (block1.func_149730_j() && !block0.func_149730_j()) {
				meta = 2;
			}

			if (block2.func_149730_j() && !block3.func_149730_j()) {
				meta = 5;
			}

			if (block3.func_149730_j() && !block2.func_149730_j()) {
				meta = 4;
			}

			w.setBlockMetadataWithNotify(x, y, z, meta, 2);
		}
	}

	@SideOnly(Side.CLIENT)
	public final IIcon getIcon(int side, int meta) {
		int flag = meta >= 10 ? 1 : 0;
		meta %= 10;
		if (meta == 0)
			meta = 3;

		return this.icons[this.getFaceOfSide(side, meta).ordinal()][flag];
	}

	private EnumFace getFaceOfSide(int side, int meta) {
		if (side == 0)
			return EnumFace.BOTTOM;
		if (side == 1)
			return EnumFace.TOP;
		if (side == meta)
			return EnumFace.FRONT;
		if (side == meta + (meta % 2 == 0 ? 1 : -1))
			return EnumFace.BACK;
		if ((meta == 2 && side == 5) || (meta == 3 && side == 4)
				|| (meta == 4 && side == 2) || (meta == 5 && side == 3))
			return EnumFace.LEFT;
		return EnumFace.RIGHT;
	}

	private int getOpposite(int side) {
		return side + (side % 2 == 0 ? 1 : -1);
	}

	@SideOnly(Side.CLIENT)
	public final void registerBlockIcons(IIconRegister iconRegister) {
		this.iconRegister = iconRegister;
		IIcon icon = this.iconRegister.registerIcon(this.getDefaultIcon());
		for (int i = 0; i < this.icons.length; i++) {
			for (int j = 0; j < this.icons[i].length; j++) {
				this.icons[i][j] = icon;
			}
		}
		this.registerIcons();
	}

	/**
	 * This method is invoked once the block is ready to receive the textures.
	 * Use this method to load all necessary icons!
	 */
	public abstract void registerIcons();

	/**
	 * Returns the identifier for the string to use on all sides with no other
	 * icon defined.
	 * 
	 * @return
	 */
	public abstract String getDefaultIcon();

	public final void setIcon(EnumFace face, String icon) {
		this.setIcon(face, icon, icon);
	}

	public final void setIcon(EnumFace face, String iconOff, String iconOn) {
		this.icons[face.ordinal()][0] = this.iconRegister.registerIcon(iconOff);
		this.icons[face.ordinal()][1] = this.iconRegister.registerIcon(iconOn);
	}

	public static void updatePlasmaSourceState(boolean active, World w, int x,
			int y, int z) {
		int meta = w.getBlockMetadata(x, y, z);
		int metaNew = meta % 10;
		metaNew += active ? 10 : 0;
		if (metaNew != meta)
			w.setBlockMetadataWithNotify(x, y, z, metaNew, 2);

	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public final void onBlockPlacedBy(World w, int x, int y, int z,
			EntityLivingBase entityLivingBase, ItemStack p_149689_6_) {
		int l = MathHelper
				.floor_double((double) (entityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (l == 0) {
			w.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}

		if (l == 1) {
			w.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}

		if (l == 2) {
			w.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}

		if (l == 3) {
			w.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		this.onBlockPlacedIntoWorldBy(w, x, y, z, entityLivingBase, p_149689_6_);
	}

	public void onBlockPlacedIntoWorldBy(World w, int x, int y, int z,
			EntityLivingBase entityLivingBase, ItemStack p_149689_6_) {

	}

	public static ForgeDirection getFaceDirectionForMeta(EnumFace face,
			int metadata) {
		int meta = metadata % 10;
		if (meta == 0 | meta == 1)
			meta = 3;
		return EnumFace.getDirectionOfFace(face,
				ForgeDirection.getOrientation(meta).getOpposite());
	}

	public static boolean isOn(int metadata) {
		return metadata >= 10;
	}
}
