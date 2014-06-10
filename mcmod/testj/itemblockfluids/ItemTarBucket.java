package testj.itemblockfluids;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import testj.TutorialMod;
import testj.lib.Names;
import testj.lib.References;

public class ItemTarBucket extends ItemBucket {

	public ItemTarBucket(Block tarBlock) {
		super(tarBlock);
		this.setUnlocalizedName(Names.TarBucket);
		this.setCreativeTab(TutorialMod.modTab);
		this.setTextureName(References.MOD_ID + ":" + Names.TarBucket);
		this.setContainerItem(Items.bucket);
	}

}
