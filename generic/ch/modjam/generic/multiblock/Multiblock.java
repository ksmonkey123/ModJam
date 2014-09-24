package ch.modjam.generic.multiblock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import ch.modjam.generic.helper.ArrayHelper;

/**
 * This class describes a MultiBlock structure and provides methods to check for the validity.
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 * @version 1
 */
public class Multiblock {

	// XXX: a hashset could be used instead since no particular order is required and it is more
	// efficient
	private ArrayList<Block>					usedBlocks;

	/**
	 * if the array contains multiple blocks then it's possible to choose between one of them
	 */
	private HashMap<MultiblockPoint, Block[]>	blockMap;

	/**
	 * initializes an empty MultiBlock
	 * 
	 * @param tileEntity the MultiBlock's core TileEntity Class
	 */
	public Multiblock() {
		this.usedBlocks = new ArrayList<Block>();
		this.blockMap = new HashMap<MultiblockPoint, Block[]>();
	}

	/**
	 * Add a Block to the MultiBlock structure.
	 * 
	 * @param x the block-relative x coordinate. the structure origin can be placed arbitrarily
	 * @param y the block-relative y coordinate. the structure origin can be placed arbitrarily
	 * @param z the block-relative z coordinate. the structure origin can be placed arbitrarily
	 * @param blocks the valid blocks for the given position in the structure
	 */
	public void setPossibleBlocksForPos(short x, short y, short z, Block... blocks) {
		MultiblockPoint point = new MultiblockPoint(x, y, z);
		if (this.blockMap.containsKey(point))
			throw new RuntimeException(
				"The multiblocks for the point " + point + " were already defined. This exception is thrown because Multiblock.usedBlocks will get invalid otherwise");
		this.blockMap.put(point, blocks);
		for (Block block : blocks)
			if (!this.usedBlocks.contains(block))
				this.usedBlocks.add(block);
	}

	/**
	 * retrieves the valid blocks for a certain position in the structure
	 * 
	 * @param x the block-relative x coordinate
	 * @param y the block-relative y coordinate
	 * @param z the block-relative z coordinate
	 * @return the valid blocks for the given position in the structure
	 */
	public Block[] getBlocksForPosition(short x, short y, short z) {
		return this.blockMap.get(new MultiblockPoint(x, y, z));
	}

	/**
	 * indicates the presence of a certain block in the structure
	 * 
	 * @param block the block to check
	 * @return <tt>true</tt> if the block is or may be present in the structure, <tt>false</tt>
	 *         otherwise
	 */
	public boolean isBlockPresent(Block block) {
		return this.usedBlocks.contains(block);
	}

	/**
	 * @param block
	 * @return the valid relative positions where the given block fits into this multiblock
	 *         structure
	 */
	public MultiblockPoint[] getValidBlockPositions(Block block) {
		if (!this.isBlockPresent(block))
			return new MultiblockPoint[0];
		ArrayList<MultiblockPoint> points = new ArrayList<MultiblockPoint>();
		for (Entry<MultiblockPoint, Block[]> blocksOnPoint : this.blockMap.entrySet()) {
			Block[] validBlocksHere = blocksOnPoint.getValue();
			if (ArrayHelper.contains(validBlocksHere, block))
				points.add(blocksOnPoint.getKey());
		}
		return points.toArray(new MultiblockPoint[0]);
	}

	/**
	 * @return all points of the structure that belong to it
	 */
	public MultiblockPoint[] getMultiblockPoints() {
		return this.blockMap.keySet().toArray(new MultiblockPoint[0]);
	}

	private boolean isBlockValidForPosition(Block block, MultiblockPoint point) {
		Block validBlocks[] = this.blockMap.get(point);
		if (ArrayHelper.contains(validBlocks, block))
			return true;
		return false;
	}

	/**
	 * indicates if there exists a valid MultiBlock structure with the given root coordinates
	 * 
	 * @param w World
	 * @param originX
	 * @param originY
	 * @param originZ
	 * @return <tt>true</tt> if there exists a MultiBlock, <tt>false</tt> otherwise
	 */
	public boolean isValidStructure(World w, int originX, int originY, int originZ) {
		MultiblockPoint points[] = this.getMultiblockPoints();
		for (MultiblockPoint point : points) {
			int absoluteX = point.getX(originX);
			int absoluteY = point.getY(originY);
			int absoluteZ = point.getZ(originZ);
			if (!this.isBlockValidForPosition(w.getBlock(absoluteX, absoluteY, absoluteZ), point))
				return false;
		}
		return true;
	}
}
