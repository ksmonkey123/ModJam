package ch.modjam.generic.multiblock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.world.World;

/**
 * This class describes a MultiBlock structure and provides methods to check for
 * the validity.
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 * @version 1
 */
public class Multiblock {
    
    private ArrayList<Block>                  usedBlocks;
    private HashMap<MultiblockPoint, Block[]> blockMap;
    
    /**
     * initializes an empty MultiBlock
     * 
     * @param tileEntity
     *            the MultiBlock's core TileEntity Class
     */
    public Multiblock() {
        this.usedBlocks = new ArrayList<Block>();
        this.blockMap = new HashMap<MultiblockPoint, Block[]>();
    }
    
    /**
     * Add a Block to the MultiBlock structure.
     * 
     * @param x
     *            the block-relative x coordinate. the structure origin can be
     *            placed arbitrarily
     * @param y
     *            the block-relative y coordinate. the structure origin can be
     *            placed arbitrarily
     * @param z
     *            the block-relative z coordinate. the structure origin can be
     *            placed arbitrarily
     * @param blocks
     *            the valid blocks for the given position in the structure
     */
    public void addBlock(short x, short y, short z, Block... blocks) {
        this.blockMap.put(new MultiblockPoint(x, y, z), blocks);
        for (Block block : blocks)
            if (!this.usedBlocks.contains(block)) {
                this.usedBlocks.add(block);
            }
    }
    
    /**
     * retrieves the valid blocks for a certain position in the structure
     * 
     * @param x
     *            the block-relative x coordinate
     * @param y
     *            the block-relative y coordinate
     * @param z
     *            the block-relative z coordinate
     * @return the valid blocks for the given position in the structure
     */
    public Block[] getBlocksForPosition(short x, short y, short z) {
        return this.blockMap.get(new MultiblockPoint(x, y, z));
    }
    
    /**
     * indicates the presence of a certain block in the structure
     * 
     * @param block
     *            the block to check
     * @return <tt>true</tt> if the block is or may be present in the structure,
     *         <tt>false</tt> otherwise
     */
    public boolean isBlockPresent(Block block) {
        return this.usedBlocks.contains(block);
    }
    
    public MultiblockPoint[] getValidBlockPositions(Block block) {
        if (!this.isBlockPresent(block))
            return new MultiblockPoint[0];
        ArrayList<MultiblockPoint> points = new ArrayList<MultiblockPoint>();
        Iterator<Entry<MultiblockPoint, Block[]>> it = this.blockMap.entrySet()
                .iterator();
        while (it.hasNext()) {
            Map.Entry<MultiblockPoint, Block[]> pairs = it.next();
            for (Block b : pairs.getValue())
                if (b.equals(block)) {
                    points.add(pairs.getKey());
                }
        }
        return points.toArray(new MultiblockPoint[0]);
    }
    
    /**
     * @return all points of a structure
     */
    public MultiblockPoint[] getMultiblockPoints() {
        return this.blockMap.keySet().toArray(new MultiblockPoint[0]);
    }
    
    private boolean isBlockValidForPosition(Block block, MultiblockPoint point) {
        Block blocks[] = this.blockMap.get(point);
        for (Block block1 : blocks)
            if (block1.equals(block))
                return true;
        return false;
    }
    
    /**
     * indicates if there exists a valid MultiBlock structure with the given
     * root coordinates
     * 
     * @param w
     * @param x
     * @param y
     * @param z
     * @return <tt>true</tt> if there exists a MultiBlock, <tt>false</tt>
     *         otherwise
     */
    public boolean isValidStructure(World w, int x, int y, int z) {
        MultiblockPoint points[] = this.getMultiblockPoints();
        for (MultiblockPoint point : points) {
            int px = point.getX(x);
            int py = point.getY(y);
            int pz = point.getZ(z);
            if (!this.isBlockValidForPosition(w.getBlock(px, py, pz), point))
                return false;
        }
        return true;
    }
}
