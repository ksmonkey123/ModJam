package ch.modjam.generic.helper;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Helper Methods for easier Registration of stuff
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 * @version 1
 */
public class RegistryUtil {
    
    /**
     * Register a simple block. The block id is extracted from the blocks
     * unlocalized name
     * 
     * @param block
     */
    public static void registerBlock(Block block) {
        String blockName = block.getUnlocalizedName().substring(5);
        GameRegistry.registerBlock(block, blockName);
    }
    
    /**
     * Register a block with a tile entity. The block id is extracted from the
     * blocks unlocalized name. The block id is then also used for the tile
     * entity by appending <tt>TE</tt>. For easier registration in looped block
     * generation, the duplicate tile entity id errors are ignored and the tile
     * entity is therefore not registered if a tile entity exists under the same
     * id.
     * 
     * @param block
     * @param tileEntityClass
     */
    public static void registerBlock(Block block,
            Class<? extends TileEntity> tileEntityClass) {
        String blockName = block.getUnlocalizedName().substring(5);
        GameRegistry.registerBlock(block, blockName);
        try {
            GameRegistry.registerTileEntity(tileEntityClass, blockName + "TE");
        } catch (IllegalArgumentException ex) {
            // Ignore duplicate
        }
    }
    
    /**
     * Register a simple item. The item id is extracted from the items
     * unlocalized name.
     * 
     * @param item
     */
    public static void registerItem(Item item) {
        String itemName = item.getUnlocalizedName().substring(5);
        GameRegistry.registerItem(item, itemName);
    }
}
