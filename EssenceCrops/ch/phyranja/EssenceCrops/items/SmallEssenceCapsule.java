package ch.phyranja.EssenceCrops.items;

import ch.phyranja.EssenceCrops.EssenceCrops;
import ch.phyranja.EssenceCrops.essences.EssenceType;
import ch.phyranja.EssenceCrops.lib.Names;
import ch.phyranja.EssenceCrops.lib.References;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class SmallEssenceCapsule extends Item {

	private EssenceType type;

    public SmallEssenceCapsule(EssenceType type){
    	this.type=type;
    	this.setCreativeTab(EssenceCrops.modTab);
    	this.setUnlocalizedName(Names.smallCapsules[type.ordinal()]);
		this.setTextureName(References.MOD_ID + ":" + Names.smallCapsules[type.ordinal()]);
    }

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10)
    {
        return false;
    }
	
	
    public EssenceType getEssenceType(){
    	return type;
    }
}
