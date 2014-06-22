package ch.judos.mcmod.gui;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;

public class Box extends BlockContainer {

	public Box() {
		super(Material.ground);

		this.setCreativeTab(MCMod.modTab);
		this.setBlockName(Names.Box);
		this.setBlockTextureName(References.MOD_ID + ":" + Names.Box);
		this.setHardness(1f);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new BoxTE();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
			int side, float sideX, float sideY, float sideZ) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking())
			return false;

		player.openGui(MCMod.instance, getGuiIndex(), world, x, y, z);
		return true;
	}

	protected int getGuiIndex() {
		return References.GUI_BOX;
	}

	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		IInventory te = (IInventory) world.getTileEntity(x, y, z);
		if (te != null) {
			for (int i1 = 0; i1 < te.getSizeInventory(); ++i1) {
				ItemStack itemstack = te.getStackInSlot(i1);
				if (itemstack != null) {
					EntityItem entityitem = new EntityItem(world, x + 0.5, y + 1, z + 0.5,
						new ItemStack(itemstack.getItem(), itemstack.stackSize, itemstack
							.getItemDamage()));
					entityitem.motionX = 0;
					entityitem.motionZ = 0;
					entityitem.motionY = 0.2f;

					if (itemstack.hasTagCompound()) {
						entityitem.getEntityItem().setTagCompound(
							(NBTTagCompound) itemstack.getTagCompound().copy());
					}
					world.spawnEntityInWorld(entityitem);
				}

				world.func_147453_f(x, y, z, block);
			}
			super.breakBlock(world, x, y, z, block, meta);
		}
	}
}
