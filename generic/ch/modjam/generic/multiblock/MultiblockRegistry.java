package ch.modjam.generic.multiblock;

import java.util.HashMap;

/**
 * This Registry holds registered MultiBlocks
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 * @version 1
 */
public class MultiblockRegistry {
    
    private final static MultiblockRegistry INSTANCE = new MultiblockRegistry();
    
    public static MultiblockRegistry instance() {
        return MultiblockRegistry.INSTANCE;
    }
    
    private HashMap<String, Multiblock> multiblocks       = new HashMap<String, Multiblock>();
    
    private HashMap<Integer, ActiveSet> activeMultiblocks = new HashMap<Integer, ActiveSet>();
    
    public void registerMultiblock(Multiblock structure, String id) {
        this.multiblocks.put(id, structure);
    }
    
    public Multiblock getMultiblockByInstanceID(int instanceID) {
        return this.multiblocks.get(this.activeMultiblocks.get(
                Integer.valueOf(instanceID)).getMultiblock());
    }
    
    public MultiBlockTileEntity getMultiblockTileEntityByInstanceID(
            int instanceID) {
        return this.activeMultiblocks.get(Integer.valueOf(instanceID))
                .getTileEntity();
    }
    
    class ActiveSet {
        private String               multiblock;
        private MultiBlockTileEntity tileEntity;
        
        public ActiveSet(String multiblock, MultiBlockTileEntity tileEntity) {
            super();
            this.multiblock = multiblock;
            this.tileEntity = tileEntity;
        }
        
        public String getMultiblock() {
            return this.multiblock;
        }
        
        public MultiBlockTileEntity getTileEntity() {
            return this.tileEntity;
        }
        
    }
    
}
