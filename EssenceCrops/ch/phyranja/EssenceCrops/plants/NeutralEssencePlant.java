package ch.phyranja.EssenceCrops.plants;

import java.util.Random;

import ch.phyranja.EssenceCrops.EssenceCrops;
import ch.phyranja.EssenceCrops.lib.Names;
import ch.phyranja.EssenceCrops.lib.References;

import net.minecraft.item.Item;
import net.minecraft.world.World;


public class NeutralEssencePlant extends AbstractEssencePlant {

	
	public NeutralEssencePlant(){
		this.setBlockName(Names.NeutralEP);
		this.setBlockTextureName(References.MOD_ID + ":" + Names.NeutralEP);
	}

	@Override
	protected Item getSeedDrop() {
		return EssenceCrops.neutralSeed;
	}

	@Override
	protected Item getEssenceDrop() {
		return EssenceCrops.neutralPetal;
	}

}
