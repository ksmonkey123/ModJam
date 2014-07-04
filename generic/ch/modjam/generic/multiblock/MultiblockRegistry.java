package ch.modjam.generic.multiblock;

import java.util.HashMap;

/**
 * This Registry holds registered MultiBlocks
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 * @version 1
 */
public class MultiblockRegistry {
    
    private final static MultiblockRegistry INSTANCE   = new MultiblockRegistry();
    private final static Object             SYNC_TOKEN = new Object();
    
    /**
     * @return the registry instance
     */
    public static MultiblockRegistry instance() {
        return MultiblockRegistry.INSTANCE;
    }
    
    private HashMap<String, Multiblock> multiblocks       = new HashMap<String, Multiblock>();
    
    private HashMap<Integer, ActiveSet>    activeMultiblocks = new HashMap<Integer, ActiveSet>();
    
    private long                        maxActiveID       = 0;
    
    /**
     * register a multiblock structure as a valid multiblock
     * 
     * @param structure
     * @param id
     */
    public void registerMultiblock(Multiblock structure, String id) {
        // FIXME: prevent duplicate structure ID's
        this.multiblocks.put(id, structure);
    }
    
    /**
     * retrieves an active multiblock over its ID
     * 
     * @param instanceID
     * @return the multiblock
     */
    public Multiblock getMultiblockByInstanceID(long instanceID) {
        return this.multiblocks.get(this.activeMultiblocks.get(
                Long.valueOf(instanceID)).getMultiblock());
    }
    
    /**
     * retrieves the master tile entity of an active multiblock over its ID
     * 
     * @param instanceID
     * @return the master tile entity
     */
    public MultiblockTileEntity getMultiblockTileEntityByInstanceID(
            long instanceID) {
        return this.activeMultiblocks.get(Long.valueOf(instanceID))
                .getTileEntity();
    }
    
    /**
     * register a new multiblock instance with an internally generated id.
     * 
     * @param structure
     * @param te
     *            the multiblock's master tile-entity
     * @return the generated instance id. use this ID for multiblock references
     */
    public int registerMultiblockInstance(String structure,
            MultiblockTileEntity te) {
        int id;
        synchronized (MultiblockRegistry.SYNC_TOKEN) {
            //id = ++this.maxActiveID;
            //this.registerMultiblockInstance(id, structure, te);
        }
        return id;
    }
    
    /**
     * @param id
     * @return the multiblock registered under the given ID
     */
    public Multiblock getMultiblockByStructureID(String id) {
        return this.multiblocks.get(id);
    }
    
    /**
     * @return an array containing all registered multiblock structure IDs
     */
    public String[] getMultiblockStructureIDs() {
        return this.multiblocks.keySet().toArray(new String[0]);
    }
    
    /**
     * register a multiblock structure instance with a predefined key. This is
     * used for world reload to restore the registry. new instances should never
     * be registered with a preset ID!
     * 
     * @param instanceID
     * @param structure
     * @param te
     */
    public void registerMultiblockInstance(int instanceID, String structure,
            MultiblockTileEntity te) {
        this.activeMultiblocks.put(Integer.valueOf(instanceID), new ActiveSet(
                structure, te));
        if (this.maxActiveID < instanceID)
            this.maxActiveID = instanceID;
    }
    
    public int getNextID() {
        return 0; //this.maxActiveID + 1;
    }
    
    @SuppressWarnings("javadoc")
    public Multiblock[] getRegisteredMultiblocks() {
        return this.multiblocks.values().toArray(new Multiblock[0]);
    }
    
    class ActiveSet {
        private String               multiblock;
        private MultiblockTileEntity tileEntity;
        
        public ActiveSet(String multiblock, MultiblockTileEntity tileEntity) {
            super();
            this.multiblock = multiblock;
            this.tileEntity = tileEntity;
        }
        
        public String getMultiblock() {
            return this.multiblock;
        }
        
        public MultiblockTileEntity getTileEntity() {
            return this.tileEntity;
        }
        
    }
}
