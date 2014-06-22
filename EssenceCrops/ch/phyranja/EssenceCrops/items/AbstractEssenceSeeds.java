package ch.phyranja.EssenceCrops.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import ch.phyranja.EssenceCrops.EssenceCrops;

/**
 * @author phyranja
 */
public abstract class AbstractEssenceSeeds extends Item implements IPlantable{
	
	private Block plant;
    /** BlockID of the block the seeds can be planted on. */
    private Block dirt; // FIXME: unused
    
    /**
     */
    public AbstractEssenceSeeds(){
    	this.setCreativeTab(EssenceCrops.modTab);
    }

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
        if (par7 != 1)
        {
            return false;
        }
        else if (player.canPlayerEdit(x, y, z, par7, itemStack) && player.canPlayerEdit(x, y + 1, z, par7, itemStack))
        {
            if (world.getBlock(x, y, z).canSustainPlant(world, x, y, z, ForgeDirection.UP, this) && world.isAirBlock(x, y + 1, z))
            {
                world.setBlock(x, y + 1, z, this.plant);
                --itemStack.stackSize;
                return true;
            }
			return false;
        }
        else
        {
            return false;
        }
    }
	
	@Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z)
    {
        return EnumPlantType.Crop;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z)
    {
        return this.plant;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z)
    {
        return 0;
    }
}
