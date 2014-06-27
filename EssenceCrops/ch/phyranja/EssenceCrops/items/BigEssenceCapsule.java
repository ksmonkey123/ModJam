package ch.phyranja.EssenceCrops.items;

import ch.phyranja.EssenceCrops.EssenceCrops;
import ch.phyranja.EssenceCrops.essences.Essence;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BigEssenceCapsule extends Item {

	private Essence type;

    public BigEssenceCapsule(Essence type){
    	this.setCreativeTab(EssenceCrops.modTab);
    	this.type=type;
    }

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
        return false;
    }
	
	
    public Essence getEssenceType(){
    	return type;
    }
}
