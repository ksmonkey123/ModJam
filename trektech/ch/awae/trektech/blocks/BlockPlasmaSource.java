package ch.awae.trektech.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import test.Test;
import ch.awae.trektech.TrekTech;
import ch.awae.trektech.entities.TileEntityPlasmaSource;
import ch.modjam.generic.BlockGenericDualStateDirected;
import ch.modjam.generic.EnumFace;
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

public class BlockPlasmaSource extends BlockGenericDualStateDirected {

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
		player.openGui(TrekTech.instance, 0, world, x, y, z);
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int i) {
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
									(NBTTagCompound) itemstack.getTagCompound()
											.copy());
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
		super.breakBlock(world, x, y, z, block, i);
	}

	@Override
	public void registerIcons() {
		this.setIcon(EnumFace.FRONT, TrekTech.MODID + ":plasma_gen_front_off",
				TrekTech.MODID + ":plasma_gen_front_on");
		this.setIcon(EnumFace.BACK, TrekTech.MODID + ":plasma_gen_back");
	}

	@Override
	public String getDefaultIcon() {
		return TrekTech.MODID + ":machine_basic";
	}

}
