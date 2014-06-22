package ch.phyranja.EssenceCrops.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

@SuppressWarnings("javadoc")
public class WorldGenerator implements IWorldGenerator {

	/**
	 * block size per chunk
	 */
	public static final int BLOCKS_CHUNK = 16;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {

		switch (world.provider.dimensionId) {
		case -1:
			genNether(world, random, chunkX, chunkZ);
			break;
		case 0:
			genOverworld(world, random, chunkX, chunkZ);
			break;
		case 1:
			genEnd(world, random, chunkX, chunkZ);
			break;
		default:
			break;
		}
	}

	private void genOverworld(World world, Random random, int chunkX, int chunkZ) {
		
//		int veines = 50;
//		int blockPerVeine = 10;
//		
//		for(int i=0;i<veines;i++) {
//			int blockX = chunkX * BLOCKS_CHUNK + random.nextInt(BLOCKS_CHUNK);
//			int blockZ = chunkZ * BLOCKS_CHUNK + random.nextInt(BLOCKS_CHUNK);
//			int blockY = random.nextInt(64);
//			
//			new WorldGenMinable(TutorialMod.oreKryptonit, blockPerVeine).generate(world, random, blockX, blockY, blockZ);
//		}
	}

	private void genEnd(World world, Random random, int chunkX, int chunkZ) {
		
	}

	private void genNether(World world, Random random, int chunkX, int chunkZ) {
	}

}
