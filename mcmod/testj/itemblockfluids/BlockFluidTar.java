package testj.itemblockfluids;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import testj.TutorialMod;
import testj.lib.Names;
import testj.lib.References;

public class BlockFluidTar extends BlockFluidClassic {

	public BlockFluidTar(Fluid f) {
		super(f, Material.water);
		this.setCreativeTab(TutorialMod.modTab);
		this.setBlockName(Names.TarBlock);
		this.setBlockTextureName(References.MOD_ID + ":" + Names.TarBlock);
		this.setHardness(0.5f);
		this.setResistance(0.5f);
	}

}
