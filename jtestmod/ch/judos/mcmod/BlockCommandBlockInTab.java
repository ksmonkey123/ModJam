package ch.judos.mcmod;

import net.minecraft.block.BlockCommandBlock;
import net.minecraft.creativetab.CreativeTabs;
import ch.judos.mcmod.lib.Names;

public class BlockCommandBlockInTab extends BlockCommandBlock {

	public BlockCommandBlockInTab() {
		super();
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setTextureName("minecraft:command_block");
		this.setUnlocalizedName(Names.COMMAND_BLOCK);
	}
}
