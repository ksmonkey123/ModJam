package ch.awae.trektech.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ch.awae.trektech.Handler;
import ch.awae.trektech.TrekTech;
import ch.awae.trektech.entities.TileEntityPlasmaEnergizerLow;
import ch.modjam.generic.blocks.BlockGenericDualStateDirected;
import ch.modjam.generic.blocks.EFace;
import ch.modjam.generic.gui.GenericGuiHandler;

/**
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class BlockPlasmaEnergizerLow extends BlockGenericDualStateDirected {

	/**
	 * Basic Constructor
	 */
	public BlockPlasmaEnergizerLow() {
		super(Material.rock);
		setTextureName(TrekTech.MODID + ":machine_basic");
		setUnlocalizedName("plasmaEnergizerLow");
		setCreativeTab(TrekTech.tabCustom);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPlasmaEnergizerLow();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int i) {
		TileEntityPlasmaEnergizerLow tileEntity = (TileEntityPlasmaEnergizerLow) world
			.getTileEntity(x, y, z);

		if (tileEntity != null) {
			for (int i1 = 0; i1 < tileEntity.getSizeInventory(); ++i1) {
				ItemStack itemstack = tileEntity.getStackInSlot(i1);

				if (itemstack != null) {
					float f0 = TrekTech.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = TrekTech.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = TrekTech.rand.nextFloat() * 0.8F + 0.1F;

					while (itemstack.stackSize > 0) {
						int j1 = TrekTech.rand.nextInt(21) + 10;

						if (j1 > itemstack.stackSize)
							j1 = itemstack.stackSize;

						itemstack.stackSize -= j1;
						EntityItem entityitem = new EntityItem(world, x + f0, y + f1, z + f2,
							new ItemStack(itemstack.getItem(), j1, itemstack.getMetadata()));

						if (itemstack.hasTagCompound())
							entityitem.getEntityItem().setTagCompound(
								(NBTTagCompound) itemstack.getTagCompound().copy());

						float f3 = 0.05F;
						entityitem.motionX = (float) TrekTech.rand.nextGaussian() * f3;
						entityitem.motionY = (float) TrekTech.rand.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) TrekTech.rand.nextGaussian() * f3;
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
		this.setIcon(EFace.FRONT, TrekTech.MODID + ":plasma_gen_front_off",
			TrekTech.MODID + ":plasma_gen_front_on");
		this.setIcon(EFace.BACK, TrekTech.MODID + ":plasma_gen_front_off",
			TrekTech.MODID + ":plasma_gen_front_on");
		this.setIcon(EFace.TOP, TrekTech.MODID + ":plasma_gen_front_off",
			TrekTech.MODID + ":plasma_gen_front_on");
		this.setIcon(EFace.LEFT, TrekTech.MODID + ":plasma_gen_front_off",
			TrekTech.MODID + ":plasma_gen_front_on");
		this.setIcon(EFace.RIGHT, TrekTech.MODID + ":plasma_gen_front_off",
			TrekTech.MODID + ":plasma_gen_front_on");
		this.setIcon(EFace.BOTTOM, TrekTech.MODID + ":plasma_gen_front_off",
			TrekTech.MODID + ":plasma_gen_front_on");
	}

	@Override
	public String getDefaultIcon() {
		return TrekTech.MODID + ":machine_basic";
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player,
			int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if (Handler.handleWrenching(player, w, x, y, z))
			return true;
		return GenericGuiHandler.openGUI(player, w, x, y, z);
	}

}
