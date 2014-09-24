package ch.phyranja.EssenceCrops.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ch.phyranja.EssenceCrops.EssenceCrops;
import ch.phyranja.EssenceCrops.essences.EssenceType;
import ch.phyranja.EssenceCrops.lib.Names;
import ch.phyranja.EssenceCrops.lib.References;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EssencePlant extends BlockBush implements IGrowable {

	@SideOnly(Side.CLIENT)
	private IIcon[]		icon;
	private boolean		idealEnvironment	= true;
	private EssenceType	type;

	public EssencePlant(EssenceType type) {

		super(Material.plants);
		this.setTickRandomly(true);
		float f = 0.4F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
		this.setCreativeTab((CreativeTabs) null);
		this.setHardness(0.0F);
		this.setStepSound(soundTypeGrass);

		this.type = type;
		this.setUnlocalizedName(Names.plants[type.ordinal()]);
		this.setTextureName(References.MOD_ID + ":" + Names.plants[type.ordinal()]);
	}

	/**
	 * is the block grass, dirt or farmland
	 */
	protected boolean canPlaceBlockOn(Block ground) {
		return ground == Blocks.farmland;
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	public void updateTick(World world, int x, int y, int z, Random rand) {
		super.updateTick(world, x, y, z, rand);

		if (world.getBlockLightValue(x, y + 1, z) >= 9) {
			int meta = world.getBlockMetadata(x, y, z);

			// if (meta < 4)
			// {
			// float f = 1.0f;
			//
			// if (rand.nextInt((int)(25.0F / f) + 1) == 0)
			// {
			// ++meta;
			// world.setBlockMetadataWithNotify(x, y, z, meta, 2);
			// }
			// }
			if (meta < 4) {
				++meta;
				world.setBlockMetadataWithNotify(x, y, z, meta, 2);
			} else {
				if (meta == 4 && this.environmentIsIdeal()) {
					++meta;
					world.setBlockMetadataWithNotify(x, y, z, meta, 2);
				}

			}

		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
			int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {

		this.updateTick(world, x, y, z, null);
		return super.onBlockActivated(world, x, y, z, player, p_149727_6_, p_149727_7_,
			p_149727_8_, p_149727_9_);
	}

	public void func_149863_m(World world, int x, int y, int z) {
		int l = world.getBlockMetadata(x, y, z) + MathHelper.getRandomIntegerInRange(world.rand, 2,
			5);

		if (l > 4) {
			l = 4;
		}

		world.setBlockMetadataWithNotify(x, y, z, l, 2);
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		// if (meta < 0 || meta > 4)
		// {
		// meta = 4;
		// }

		if (meta >= this.icon.length) {
			meta = 0;
		}

		return this.icon[meta];

	}

	/**
	 * The type of render function that is called for this block
	 */
	public int getRenderType() {
		return 1;
	}

	protected Item func_149866_i() {
		return this.getSeedDrop();
	}

	protected Item getSeedDrop() {
		return EssenceCrops.seeds[type.ordinal()];
	}

	protected Item func_149865_P() {
		return this.getEssenceDrop();
	}

	protected Item getEssenceDrop() {
		return EssenceCrops.petals[this.type.ordinal()];
	}

	/**
	 * Drops the block items with a specified chance of dropping the specified items
	 */
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta,
			float dropChance, int fortune) {
		super.dropBlockAsItemWithChance(world, x, y, z, meta, dropChance, 0);
	}

	public Item getItemDropped(int meta, Random rand, int fortune) {
		return meta == 4 ? this.getEssenceDrop() : this.getSeedDrop();
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	public int quantityDropped(Random rand) {
		return 1;
	}

	// checks if plant is not fully grown
	public boolean func_149851_a(World world, int x, int y, int z, boolean bool) {
		return world.getBlockMetadata(x, y, z) >= 4;
	}

	// checks if plant can be bonemealed
	public boolean func_149852_a(World world, Random p_149852_2_, int p_149852_3_, int p_149852_4_,
			int p_149852_5_) {
		return false;
	}

	/**
	 * Gets an item for the block being called on. Args: world, x, y, z
	 */
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return this.getSeedDrop();
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		this.icon = new IIcon[6];

		for (int i = 0; i < this.icon.length; ++i) {
			this.icon[i] = register.registerIcon(this.getTextureName() + "_stage_" + i);
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		if (meta == 4) {
			ret.add(new ItemStack(this.getSeedDrop(), 1, 0));
			ret.add(new ItemStack(this.getEssenceDrop(), 1, 0));
		}
		if (meta == 5) {
			if (environmentIsIdeal()) {
				ret.add(new ItemStack(this.getSeedDrop(), 2, 0));
				ret.add(new ItemStack(this.getEssenceDrop(), 1, 0));
			}
		}

		return ret;
	}

	private boolean environmentIsIdeal() {
		return idealEnvironment;
	}

	public void func_149853_b(World world, Random rand, int x, int y, int z) {
		this.func_149863_m(world, x, y, z);
	}

	@Override
	public boolean canFertilize(World worldIn, int x, int y, int z, boolean isClient) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shouldFertilize(World worldIn, Random random, int x, int y, int z) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void fertilize(World worldIn, Random random, int x, int y, int z) {
		// TODO Auto-generated method stub

	}
}
