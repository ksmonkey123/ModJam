package ch.judos.mcmod.itemblockfluids;

import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;

@SuppressWarnings("javadoc")
public class ItemTarBucket extends ItemBucket {

	public ItemTarBucket(Block tarBlock) {
		super(tarBlock);
		this.setUnlocalizedName(Names.TarBucket);
		this.setCreativeTab(MCMod.modTab);
		this.setTextureName(References.MOD_ID + ":" + Names.TarBucket);
		this.setContainerItem(Items.bucket);
	}

}
