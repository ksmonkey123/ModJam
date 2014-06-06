package ch.awae.trektech.blocks;

import ch.awae.trektech.TrekTech;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockDuraniumWall extends Block {

    public BlockDuraniumWall() {
	super(Material.rock);
	setHardness(15F);
	setBlockName("duraniumWall");
	setCreativeTab(TrekTech.tabCustom);
	setHarvestLevel("pickaxe", 2);
	setBlockTextureName(TrekTech.MODID + ":duranium_block");
    }
}
