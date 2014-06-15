package ch.awae.trektech.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import test.Test;
import ch.awae.trektech.TrekTech;
import ch.awae.trektech.entities.TileEntityPlasmaSource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockPlasmaSource extends BlockContainer {

	private IIcon iconFront;
	private IIcon iconFrontOn;
	private IIcon iconTop;
	private IIcon iconBack;
	private static boolean switching;

	public BlockPlasmaSource() {
		super(Material.rock);
		setBlockTextureName(TrekTech.MODID + ":machine_basic");
		setBlockName("plasmaSource");
		setCreativeTab(TrekTech.tabCustom);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPlasmaSource();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int metadata, float what, float these,
			float are) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking()) {
			return false;
		}
		// code to open gui explained later
		player.openGui(TrekTech.instance, 0, world, x, y, z);
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int i) {
		if (!switching) {
			TileEntityPlasmaSource tileEntity = (TileEntityPlasmaSource) world
					.getTileEntity(x, y, z);

			if (tileEntity != null) {
				for (int i1 = 0; i1 < tileEntity.getSizeInventory(); ++i1) {
					ItemStack itemstack = tileEntity.getStackInSlot(i1);

					if (itemstack != null) {
						float f = TrekTech.rand.nextFloat() * 0.8F + 0.1F;
						float f1 = TrekTech.rand.nextFloat() * 0.8F + 0.1F;
						float f2 = TrekTech.rand.nextFloat() * 0.8F + 0.1F;

						while (itemstack.stackSize > 0) {
							int j1 = TrekTech.rand.nextInt(21) + 10;

							if (j1 > itemstack.stackSize) {
								j1 = itemstack.stackSize;
							}

							itemstack.stackSize -= j1;
							EntityItem entityitem = new EntityItem(world,
									(double) ((float) x + f),
									(double) ((float) y + f1),
									(double) ((float) z + f2), new ItemStack(
											itemstack.getItem(), j1,
											itemstack.getItemDamage()));

							if (itemstack.hasTagCompound()) {
								entityitem.getEntityItem().setTagCompound(
										(NBTTagCompound) itemstack
												.getTagCompound().copy());
							}

							float f3 = 0.05F;
							entityitem.motionX = (double) ((float) TrekTech.rand
									.nextGaussian() * f3);
							entityitem.motionY = (double) ((float) TrekTech.rand
									.nextGaussian() * f3 + 0.2F);
							entityitem.motionZ = (double) ((float) TrekTech.rand
									.nextGaussian() * f3);
							world.spawnEntityInWorld(entityitem);
						}
					}
				}

				world.removeTileEntity(x, y, z);
			}
		}
		super.breakBlock(world, x, y, z, block, i);
	}

	public void onBlockAdded(World w, int x, int y, int z) {
		super.onBlockAdded(w, x, y, z);
		this.orient(w, x, y, z);
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

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (meta == 0)
			meta = 3;
		// return side == 1 ? this.iconTop : (side == 0 ? this.iconTop
		// : (side != meta ? this.blockIcon : this.iconFront));
		if (side == 1)
			return this.iconTop;
		if (side == meta % 10)
			return meta >= 10 ? this.iconFrontOn : this.iconFront;
		if (side == this.getOpposite(meta % 10))
			return this.iconBack;
		return this.blockIcon;
	}

	private int getOpposite(int side) {
		return side + (side % 2 == 0 ? 1 : -1);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(TrekTech.MODID
				+ ":machine_basic");
		this.iconFront = iconRegister.registerIcon(TrekTech.MODID
				+ ":plasma_gen_front_off");
		this.iconFrontOn = iconRegister.registerIcon(TrekTech.MODID
				+ ":plasma_gen_front_on");
		this.iconTop = iconRegister.registerIcon("furnace_top");
		this.iconBack = iconRegister.registerIcon(TrekTech.MODID
				+ ":plasma_gen_back");
	}

	/**
	 * Called when the block is placed in the world.
	 */
	public void onBlockPlacedBy(World w, int x, int y, int z,
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

		// if (p_149689_6_.hasDisplayName()) {
		// ((TileEntityFurnace) w.getTileEntity(x, y, z))
		// .func_145951_a(p_149689_6_.getDisplayName());
		// }

	}

	/**
	 * Update which block the furnace is using depending on whether or not it is
	 * burning
	 */
	public static void updatePlasmaSourceState(boolean active, World w, int x,
			int y, int z) {
		int meta = w.getBlockMetadata(x, y, z);
		// TileEntity tileentity = w.getTileEntity(x, y, z);

		// deactivates item drops for switch
		// switching = true;

		// if (active) {
		// w.setBlock(x, y, z, TrekTech.blockPlasmaSourceActive);
		// } else {
		// w.setBlock(x, y, z, TrekTech.blockPlasmaSource);
		// }

		// reactivates item drops after switch
		// switching = false;

		int metaNew = meta % 10;
		metaNew += active ? 10 : 0;

		System.out.println(meta + " / " + metaNew);

		if (metaNew != meta)
			w.setBlockMetadataWithNotify(x, y, z, metaNew, 2);

		// if (tileentity != null) {
		// tileentity.validate();
		// w.setTileEntity(x, y, z, tileentity);
		// }
	}

}
