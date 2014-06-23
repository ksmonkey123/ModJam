package ch.phyranja.EssenceCrops.items;

import ch.phyranja.EssenceCrops.EssenceCrops;
import ch.phyranja.EssenceCrops.lib.References;
import ch.phyranja.EssenceCrops.lib.Names;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class NeutralEssenceSeed extends AbstractEssenceSeeds{

	public NeutralEssenceSeed(){
		this.setUnlocalizedName(Names.NeutralES);
		this.setTextureName(References.MOD_ID + ":" + Names.NeutralES);
		this.plant=EssenceCrops.neutralPlant;
	}

	
}
