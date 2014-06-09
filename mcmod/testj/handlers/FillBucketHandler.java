package testj.handlers;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FillBucketHandler {

	public static FillBucketHandler INSTANCE = new FillBucketHandler();
	public Map<Block, Item> buckets = new HashMap<Block, Item>();

	private FillBucketHandler() {
	}

	public static void add(Block b, Item i) {
		INSTANCE.buckets.put(b, i);
	}

	@SubscribeEvent
	public void onBucketFill(FillBucketEvent event) {

		ItemStack result = fillCustomBucket(event.world, event.target);
		System.out.println("result: " + result);
		if (result == null)
			return;

		event.result = result;
		event.setResult(Result.ALLOW);
	}

	private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {

		Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
		System.out.println("target: " + block);
		Item bucket = buckets.get(block);
		if (bucket != null
				&& world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
			world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
			return new ItemStack(bucket);
		} else
			return null;

	}
}
