package ch.judos.at.station;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ch.judos.at.ATMain;
import ch.judos.at.lib.ATNames;

public class ItemGearBox extends Item {
	public ItemGearBox() {
		this.setUnlocalizedName(ATNames.gearbox);
		this.setTextureName(ATMain.MOD_ID + ":" + ATNames.gearbox);
		this.setCreativeTab(ATMain.modTab);
		this.setMaxStackSize(20);
	}

	@Override
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean unknown) {
		list.add("Mostly used for the construction");
		list.add("of gondolas and gondola stations.");
	}
}
