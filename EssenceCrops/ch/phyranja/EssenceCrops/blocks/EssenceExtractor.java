package ch.phyranja.EssenceCrops.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ch.modjam.generic.GenericGuiHandler;
import ch.modjam.generic.blocks.BlockGenericDualStateDirected;
import ch.modjam.generic.blocks.EnumFace;
import ch.phyranja.EssenceCrops.EssenceCrops;
import ch.phyranja.EssenceCrops.entities.TileEntityExtractor;
import ch.phyranja.EssenceCrops.lib.Names;
import ch.phyranja.EssenceCrops.lib.References;

public class EssenceExtractor extends BlockGenericDualStateDirected {
    

    public EssenceExtractor() {
        super(Material.rock);
        setBlockTextureName(References.MOD_ID + ":machine_basic");
        this.setBlockName(Names.Extractor);
		this.setBlockTextureName(References.MOD_ID + ":" + Names.Extractor);
        this.setCreativeTab(EssenceCrops.modTab);
    }
    
    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityExtractor();
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int i) {
       
        super.breakBlock(world, x, y, z, block, i);
    }
    
    @Override
    public void registerIcons() {
        this.setIcon(EnumFace.FRONT, References.MOD_ID + ":e_extractor_front_off",
        		References.MOD_ID + ":e_extractor_front_on");
        this.setIcon(EnumFace.BACK, References.MOD_ID + ":e_extractor_side");
        this.setIcon(EnumFace.TOP, References.MOD_ID + ":e_extractor_top");
        this.setIcon(EnumFace.LEFT, References.MOD_ID + ":e_extractor_side");
        this.setIcon(EnumFace.RIGHT, References.MOD_ID + ":e_extractor_side");
        this.setIcon(EnumFace.BOTTOM, References.MOD_ID + ":e_extractor_top");
    }
    
    @Override
    public String getDefaultIcon() {
        return References.MOD_ID + ":e_extractor_side";
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
            EntityPlayer player, int p_149727_6_, float p_149727_7_,
            float p_149727_8_, float p_149727_9_) {
       
        return GenericGuiHandler.openGUI(player, world, x, y, z);
    }
    
}
