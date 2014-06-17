package ch.judos.mcmod.itemblockfluids;

import ch.judos.mcmod.TutorialMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

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
