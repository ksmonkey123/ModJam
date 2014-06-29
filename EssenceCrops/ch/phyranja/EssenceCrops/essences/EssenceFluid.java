package ch.phyranja.EssenceCrops.essences;

import net.minecraftforge.fluids.Fluid;

public class EssenceFluid extends Fluid {

	private EssenceType type;
	
	public EssenceFluid(String fluidName, EssenceType type) {
		super(fluidName);
		
	}

}
