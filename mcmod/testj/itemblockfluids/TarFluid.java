package testj.itemblockfluids;

import net.minecraftforge.fluids.Fluid;
import testj.lib.Names;

public class TarFluid extends Fluid {

	public TarFluid() {
		super(Names.TarFluid);
		this.setLuminosity(0);
		this.setDensity(2000);
		this.setViscosity(7000);
		this.setGaseous(false);
		this.setUnlocalizedName(Names.TarFluid);
	}
}
