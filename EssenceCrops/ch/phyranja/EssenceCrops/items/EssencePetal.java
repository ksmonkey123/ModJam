package ch.phyranja.EssenceCrops.items;

import ch.phyranja.EssenceCrops.EssenceCrops;
import ch.phyranja.EssenceCrops.essences.Essence;
import ch.phyranja.EssenceCrops.lib.Names;
import ch.phyranja.EssenceCrops.lib.References;
import net.minecraft.item.Item;

public class EssencePetal extends Item {
	
	private Essence type;
	public EssencePetal(Essence type){
    	this.setCreativeTab(EssenceCrops.modTab);
    	this.setUnlocalizedName(Names.petals[type.ordinal()]);
		this.setTextureName(References.MOD_ID + ":" + Names.petals[type.ordinal()]);
    }

}
