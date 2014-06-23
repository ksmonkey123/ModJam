package ch.phyranja.EssenceCrops.plants;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class AbstractEssencePlant extends BlockBush implements IGrowable {

	@SideOnly(Side.CLIENT)
    private IIcon[] icon;
	private boolean idealEnvironment=false;

    protected AbstractEssencePlant()
    {
        this.setTickRandomly(true);
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.setCreativeTab((CreativeTabs)null);
        this.setHardness(0.0F);
        this.setStepSound(soundTypeGrass);
        this.disableStats();
    }

    /**
     * is the block grass, dirt or farmland
     */
    protected boolean canPlaceBlockOn(Block ground)
    {
        return ground == Blocks.farmland;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        super.updateTick(world, x, y, z, rand);

        if (world.getBlockLightValue(x, y + 1, z) >= 9)
        {
            int meta = world.getBlockMetadata(x, y, z);

            if (meta < 4)
            {
                float f = 1.0f;

                if (rand.nextInt((int)(25.0F / f) + 1) == 0)
                {
                    ++meta;
                    world.setBlockMetadataWithNotify(x, y, z, meta, 2);
                }
            }
        }
    }

    public void func_149863_m(World world, int x, int y, int z)
    {
        int l = world.getBlockMetadata(x, y, z) + MathHelper.getRandomIntegerInRange(world.rand, 2, 5);

        if (l > 4)
        {
            l = 4;
        }

        world.setBlockMetadataWithNotify(x, y, z, l, 2);
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        if (meta < 0 || meta > 4)
        {
            meta = 4;
        }

        return this.icon[meta];
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 6;
    }

    protected Item func_149866_i()
    {
        return this.getSeedDrop();
    }

    protected abstract Item getSeedDrop();

	protected Item func_149865_P()
    {
        return this.getEssenceDrop();
    }

    protected abstract Item getEssenceDrop();

	/**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float dropChance, int fortune)
    {
        super.dropBlockAsItemWithChance(world, x, y, z, meta, dropChance, 0);
    }

    public Item getItemDropped(int meta, Random rand, int fortune)
    {
        return meta == 4 ? this.getEssenceDrop() : this.getSeedDrop();
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random rand)
    {
        return 1;
    }

    //checks if plant is fully grown
    public boolean func_149851_a(World world, int x, int y, int z, boolean bool)
    {
        return world.getBlockMetadata(x, y, z) !=4 ;
    }
    
    //checks if plant can be bonemealed
    public boolean func_149852_a(World world, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_)
    {
        return false;
    }

    /**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return this.getSeedDrop();
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        this.icon = new IIcon[5];

        for (int i = 0; i < this.icon.length; ++i)
        {
            this.icon[i] = register.registerIcon(this.getTextureName() + "_stage_" + i);
        }
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);

        if (metadata >= 4)
        {
            if(!environmentIsIdeal()){
            	ret.add(new ItemStack(this.getSeedDrop(), 1, 0));
            }
        }

        return ret;
    }

	private boolean environmentIsIdeal() {
		return idealEnvironment;
	}
	
	 public void func_149853_b(World world, Random rand, int x, int y, int z)
	    {
	        this.func_149863_m(world, x, y, z);
	    }
}
