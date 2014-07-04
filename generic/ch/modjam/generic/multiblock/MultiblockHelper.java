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
        String[] structureIDs = MultiblockRegistry.instance()
                .getMultiblockStructureIDs();
        for (String structureID : structureIDs) {
            Multiblock structure = MultiblockRegistry.instance()
                    .getMultiblockByStructureID(structureID);
            MultiblockPoint[] blockPoints = structure.getValidBlockPositions(w
                    .getBlock(x, y, z));
            for (MultiblockPoint blockPoint : blockPoints) {
                int rootX = x - blockPoint.getX();
                int rootY = y - blockPoint.getY();
                int rootZ = z - blockPoint.getZ();
                if (structure.isValidStructure(w, rootX, rootY, rootZ)) {
                    MultiblockHelper.registerMB(structureID, w, rootX, rootY,
                            rootZ);
                    return;
                }
            }
        }
    }
    
    private static final Object SYNC_KEY = new Object();
    
    private static void registerMB(String structureID, World w, int x, int y,
            int z) {
        System.out.println("register new MultiBlock:\n Type: " + structureID
                + "\nPosX: " + x + "\nPosY: " + y + "\nPosY: " + z + "\nDim:  "
                + w.provider.dimensionId);
        // TODO: implement
        synchronized (MultiblockHelper.SYNC_KEY) {
            long id = MultiblockRegistry.instance().getNextID();
            
        }
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
