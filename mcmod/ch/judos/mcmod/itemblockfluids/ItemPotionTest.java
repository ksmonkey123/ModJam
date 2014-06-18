package ch.judos.mcmod.itemblockfluids;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ch.judos.mcmod.TutorialMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;

public class ItemPotionTest extends Item {

	public ItemPotionTest() {
		this.maxStackSize = 10;
		this.setMaxDamage(1);
		this.setUnlocalizedName(Names.PotionTest);
		this.setTextureName(References.MOD_ID + ":" + Names.PotionTest);
		this.setCreativeTab(TutorialMod.modTab);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world,
			EntityPlayer player) {
		item.damageItem(2, player);
		player.addPotionEffect(new PotionEffect(TutorialMod.potionTest.id,
				10 * 20, 0));

		return super.onItemRightClick(item, world, player);
	}

}
