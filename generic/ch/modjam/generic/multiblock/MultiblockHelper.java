package ch.modjam.generic.multiblock;

import net.minecraft.tileentity.TileEntity;
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
    
    private static void registerMB(String structureID, World w, int x, int y,
            int z) {
        if (w.isRemote)
            return;
        System.out.println("register new MultiBlock:" + "\nType: "
                + structureID + "\nPosX: " + x + "\nPosY: " + y + "\nPosY: "
                + z + "\nDim:  " + w.provider.dimensionId);
        TileEntity te = w.getTileEntity(x, y, z);
        if (te instanceof MultiblockTileEntity) {
            MultiblockRegistry.instance().registerMultiblockInstance(
                    structureID, (MultiblockTileEntity) te);
        } else
            throw new IllegalStateException(
                    "Multiblock cannot be instantitated - a MultiblockTileEntity is required in the Multiblock Root");
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
        if (w.isRemote)
            return;
        System.out.println("remove MultiBlock:" + "\nPosX: " + x + "\nPosY: "
                + y + "\nPosY: " + z + "\nDim:  " + w.provider.dimensionId);
        TileEntity te = w.getTileEntity(x, y, z);
        if (te instanceof MultiblockTileEntity) {
            MultiblockRegistry.instance().unregisterMultiblockInstance(
                    ((MultiblockTileEntity) te).getInstanceID());
        }
    }
    
}
