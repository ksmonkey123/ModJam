package ch.awae.trektech.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import ch.awae.trektech.TrekTech;

/**
 * Duranium Wall: Highly Explosion resistant wall. A wall 1 block thick can resist a TNT explosion
 * in close proximity with relatively low damage
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class BlockDuraniumWall extends Block {

	/**
	 * Basic constructor
	 */
	public BlockDuraniumWall() {
		super(Material.rock);
		setHardness(15F);
		setUnlocalizedName("duraniumWall");
		setCreativeTab(TrekTech.tabCustom);
		setHarvestLevel("pickaxe", 2);
		setTextureName(TrekTech.MODID + ":duranium_block");
	}
}
