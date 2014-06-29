package ch.phyranja.EssenceCrops.essences;

import ch.phyranja.EssenceCrops.lib.References;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraftforge.fluids.Fluid;

public class EssenceFluid extends Fluid {

	private EssenceType type;
	
	public EssenceFluid(String fluidName, EssenceType type) {
		super(fluidName);
		this.type=type;
	}
	
	
	

}
