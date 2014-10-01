package ch.judos.at.gondola;

import net.minecraft.item.Item;
import ch.judos.at.ATMain;
import ch.judos.at.lib.ATNames;

public class ItemGondola extends Item {
	public ItemGondola() {
		super();

		this.setUnlocalizedName(ATNames.gondola);
		this.setTextureName(ATMain.MOD_ID + ":" + ATNames.gondola);
		this.setCreativeTab(ATMain.modTab);
		this.setMaxStackSize(32);
	}
}
