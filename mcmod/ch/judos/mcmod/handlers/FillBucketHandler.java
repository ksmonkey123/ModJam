package ch.judos.mcmod.handlers;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("javadoc")
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
		EntityPlayer player = event.entityPlayer;
		if (player.getCurrentEquippedItem().getItem() != Items.bucket)
			dontAllowReplacingFluids(event, player);

		ItemStack result = fillCustomBucket(event.world, event.target);
		if (result == null)
			return;

		event.result = result;
		event.setResult(Result.ALLOW);
	}

	/**
	 * solves a bug: emptying a filled bucket into another liquid
	 * 
	 * @param event
	 * @param player
	 */
	private void dontAllowReplacingFluids(FillBucketEvent event,
			EntityPlayer player) {
		MovingObjectPosition target = event.target;
		Vec3 vec = event.target.hitVec;

		int[] t = { target.blockX, target.blockY, target.blockZ };
		double[] c = { vec.xCoord, vec.yCoord, vec.zCoord };
		double[] pc = { player.posX, player.posY, player.posZ };
		for (int i = 0; i < 3; i++) {
			if (c[i] % 1 == 0)
				t[i] += Math.signum(pc[i] - c[i]);
		}
		Block targetFill = event.world.getBlock(t[0], t[1], t[2]);
		int meta = event.world.getBlockMetadata(t[0], t[1], t[2]);
		if (FluidRegistry.lookupFluidForBlock(targetFill) != null && meta == 0)
			event.setCanceled(true);

	}

	private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {

		Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
		// System.out.println("target: " + block);
		Item bucket = buckets.get(block);
		if (bucket != null
				&& world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
			world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
			return new ItemStack(bucket);
		}
		return null;

	}
}
