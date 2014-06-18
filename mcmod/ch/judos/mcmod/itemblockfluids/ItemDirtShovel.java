package ch.judos.mcmod.itemblockfluids;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import ch.judos.mcmod.TutorialMod;
import ch.judos.mcmod.events.DirtShovelEvent;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;
import cpw.mods.fml.common.eventhandler.Event.Result;

public class ItemDirtShovel extends Item {

	public static HashMap<Block, Block> transforms;

	static {
		initialize();
	}

	private static void initialize() {
		transforms = new HashMap<Block, Block>();
		transforms.put(Blocks.dirt, TutorialMod.blockCarvedDirt);
		transforms.put(Blocks.grass, TutorialMod.blockCarvedDirt);
		transforms.put(TutorialMod.blockCarvedDirt, Blocks.dirt);
	}

	public ItemDirtShovel() {
		this.maxStackSize = 1;
		this.setMaxDamage(32);
		this.setUnlocalizedName(Names.DirtShovel);
		this.setTextureName(References.MOD_ID + ":" + Names.DirtShovel);
		this.setCreativeTab(TutorialMod.modTab);
	}

	public boolean onItemUse(ItemStack item, EntityPlayer player, World world,
			int x, int y, int z, int blockSide, float blockX, float blockY,
			float blockZ) {

		if (!player.canPlayerEdit(x, y, z, blockSide, item)) {
			return false;
		} else {
			DirtShovelEvent event =
					new DirtShovelEvent(player, item, world, x, y, z);
			if (MinecraftForge.EVENT_BUS.post(event)) {
				return false;
			}

			if (event.getResult() == Result.ALLOW) {
				item.damageItem(1, player);
				return true;
			}

			Block block = world.getBlock(x, y, z);

			if (blockSide != 0
					&& world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z)
					&& (transforms.containsKey(block))) {
				Block block1 = transforms.get(block);
				world.playSoundEffect((double) ((float) x + 0.5F),
						(double) ((float) y + 0.5F),
						(double) ((float) z + 0.5F),
						block1.stepSound.getStepResourcePath(),
						(block1.stepSound.getVolume() + 1.0F) / 2.0F,
						block1.stepSound.getPitch() * 0.8F);

				if (world.isRemote) {
					return true;
				} else {
					world.setBlock(x, y, z, block1);
					item.damageItem(1, player);
					return true;
				}
			} else {
				return false;
			}
		}
	}
}