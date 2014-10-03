package ch.judos.mcmod.itemblockfluids;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;
import ch.modjam.generic.helper.ListedStuff;

public class BlockKryptoniteOre extends Block {

	public BlockKryptoniteOre() {
		super(Material.rock);
		this.setUnlocalizedName(Names.KryptonitBlock);
		this.setTextureName(References.MOD_ID + ":" + Names.KryptonitBlock);
		this.setCreativeTab(MCMod.modTab);
		this.blockHardness = 5;
		this.blockResistance = 20;
		this.setHarvestLevel("pickaxe", 2);

	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int cx, int cy, int cz, int metadata,
			int fortune) {
		float amountAvg = 1 + 0.33f * fortune;
		int amount = (int) Math.floor(amountAvg);
		float pro = amountAvg % 1;
		if (world.rand.nextFloat() < pro)
			amount++;

		ArrayList<String> ores = ListedStuff.getOreNames();
		for (int x = -1; x <= 1; x++)
			for (int y = -1; y <= 1; y++)
				for (int z = -1; z <= 1; z++) {
					Block find = world.getBlock(cx + x, cy + y, cz + z);
					String name = find.getUnlocalizedName();
					if (name.startsWith("tile.ore"))
						ores.add(name.substring(5));
				}

		ArrayList<ItemStack> result = new ArrayList<ItemStack>();
		for (int i = 0; i < amount; i++) {
			int index = world.rand.nextInt(ores.size());
			ArrayList<ItemStack> dropOre = OreDictionary.getOres(ores.get(index));
			ItemStack dropOreStack = dropOre.get(0).copy();
			dropOreStack.stackSize = 1;
			result.add(dropOreStack);
		}
		return result;
	}
}
