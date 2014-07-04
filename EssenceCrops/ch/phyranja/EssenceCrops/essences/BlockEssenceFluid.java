package ch.phyranja.EssenceCrops.essences;


import ch.phyranja.EssenceCrops.EssenceCrops;
import ch.phyranja.EssenceCrops.lib.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockEssenceFluid extends BlockFluidClassic {
	
	private IIcon icon;
	private EssenceType type;

	public BlockEssenceFluid(Fluid fluid, EssenceType type) {
		super(fluid, Material.water);
		this.setCreativeTab(EssenceCrops.modTab);
		this.setBlockName(Names.essenceFluidBlocks[type.ordinal()]);
		this.setBlockTextureName(References.MOD_ID + ":" + Names.essenceFluidBlocks[type.ordinal()]);
		this.setHardness(0.5f);
		this.setResistance(0.5f);
		this.type=type;
	}

	
	@Override
	public void registerBlockIcons(IIconRegister ir)
	{
		icon = ir.registerIcon(References.MOD_ID +":"+ Names.essenceFluidBlocks[type.ordinal()]);
		
	}
	
	@Override
    public IIcon getIcon(int side, int meta) {
            return icon;
    }
}
