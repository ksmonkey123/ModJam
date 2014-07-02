package ch.modjam.generic.multiblock;

import net.minecraft.world.World;

/**
 * This helper manages the loading and unloading of multiblocks
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class MultiblockHelper {
    
    /**
     * try to construct a multiblock
     * 
     * @param w
     * @param x
     * @param y
     * @param z
     */
    public static void constructMultiblock(World w, int x, int y, int z) {
        if (w.isRemote)
            return;
        Multiblock[] structures = MultiblockRegistry.instance()
                .getRegisteredMultiblocks();
        for (Multiblock structure : structures) {
            MultiblockPoint[] blockPoints = structure.getValidBlockPositions(w
                    .getBlock(x, y, z));
            for (MultiblockPoint blockPoint : blockPoints) {
                int rootX = x - blockPoint.getX();
                int rootY = y - blockPoint.getY();
                int rootZ = z - blockPoint.getZ();
                if (structure.isValidStructure(w, rootX, rootY, rootZ)) {
                    MultiblockHelper.registerMB(structure, w, rootX, rootY,
                            rootZ);
                    return;
                }
            }
        }
        // XXX: debugging
        System.out.println(Thread.currentThread().getName() + ": construct");
    }
    
    private static void registerMB(Multiblock structure, World w, int x, int y,
            int z) {
        // TODO: implement
    }
    
    /**
     * deconstructs a multiblock
     * 
     * @param w
     * @param x
     * @param y
     * @param z
     */
    public static void deconstructMultiblock(World w, int x, int y, int z) {
        // TODO: implement
        System.out.println(Thread.currentThread().getName() + ": deconstruct");
    }
    
}
