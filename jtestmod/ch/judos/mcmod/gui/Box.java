package ch.judos.mcmod.gui;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;
import ch.modjam.generic.gui.GenericGuiHandler;
import ch.modjam.generic.inventory.GenericInventory;

/**
 * @author judos
 */
public class Box extends BlockContainer {

	/**
	 * 
	 */
	public Box() {
		super(Material.ground);

		this.setCreativeTab(MCMod.modTab);
		this.setUnlocalizedName(Names.Box);
		this.setTextureName(References.MOD_ID + ":" + Names.Box);
		this.setHardness(1f);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new BoxTE();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
			int side, float sideX, float sideY, float sideZ) {
		return GenericGuiHandler.openGUI(player, world, x, y, z);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		BoxTE te = (BoxTE) world.getTileEntity(x, y, z);
		if (te != null) {
			GenericInventory inv = te.inventory;
			for (int i1 = 0; i1 < inv.getSizeInventory(); ++i1) {
				ItemStack itemstack = inv.getStackInSlot(i1);
				if (itemstack != null) {
					EntityItem entityitem = new EntityItem(world, x + 0.5, y + 1, z + 0.5,
						new ItemStack(itemstack.getItem(), itemstack.stackSize, itemstack
							.getMetadata()));
					entityitem.motionX = 0;
					entityitem.motionZ = 0;
					entityitem.motionY = 0.2f;

					if (itemstack.hasTagCompound()) {
						entityitem.getEntityItem().setTagCompound(
							(NBTTagCompound) itemstack.getTagCompound().copy());
					}
					world.spawnEntityInWorld(entityitem);
				}

				world.updateNeighborsAboutBlockChange(x, y, z, block);
			}
			super.breakBlock(world, x, y, z, block, meta);
		}
	}
}
