package ch.phyranja.EssenceCrops.essences;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import ch.phyranja.EssenceCrops.EssenceCrops;
import ch.phyranja.EssenceCrops.lib.Names;
import ch.phyranja.EssenceCrops.lib.References;

public class BlockEssenceFluid extends BlockFluidClassic {

	private IIcon		icon;
	private EssenceType	type;

	public BlockEssenceFluid(Fluid fluid, EssenceType type) {
		super(fluid, Material.water);
		this.setCreativeTab(EssenceCrops.modTab);
		this.setUnlocalizedName(Names.essenceFluidBlocks[type.ordinal()]);
		this.setTextureName(References.MOD_ID + ":" + Names.essenceFluidBlocks[type.ordinal()]);
		this.setHardness(0.5f);
		this.setResistance(0.5f);
		this.type = type;
	}

	@Override
	public void registerIcons(IIconRegister ir) {
		icon = ir.registerIcon(References.MOD_ID + ":" + Names.essenceFluidBlocks[type.ordinal()]);

	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return icon;
	}
}
