package ch.judos.mcmod.gui;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * @author judos
 * 
 */
public class BoxItem extends ItemBlock {

	/**
	 * @param block the block
	 */
	public BoxItem(Block block) {
		super(block);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean par4) {
		list.add(EnumChatFormatting.GRAY + "It's an ancient mystery.");
	}

}
