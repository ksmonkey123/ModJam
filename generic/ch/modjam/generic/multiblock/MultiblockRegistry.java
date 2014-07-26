package ch.modjam.generic.multiblock;

import java.util.HashMap;

import net.minecraft.tileentity.TileEntity;
import ch.modjam.generic.identification.DuplicateByteFilter;
import ch.modjam.generic.identification.IUniqueIdProvider;

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
    private HashMap<Integer, ActiveSet> activeMultiblocks = new HashMap<Integer, ActiveSet>();
    // TODO: use real provider
    private IUniqueIdProvider           idProvider        = new DuplicateByteFilter();
    
    /**
     * register a multiblock structure as a valid multiblock
     * 
     * @param structure
     * @param id
     */
    public void registerMultiblock(Multiblock structure, String id) {
        if (this.multiblocks.containsKey(id))
            throw new IllegalStateException(
                    "a multiblock identifier can only be used once");
        this.multiblocks.put(id, structure);
    }
    
    /**
     * retrieves an active multiblock over its ID
     * 
     * @param id
     * @return the multiblock
     */
    public Multiblock getMultiblockByInstanceID(int id) {
        return this.multiblocks.get(this.activeMultiblocks.get(
                Integer.valueOf(id)).getMultiblock());
    }
    
    /**
     * retrieves the master tile entity of an active multiblock over its ID
     * 
     * @param id
     * @return the master tile entity
     */
    public MultiblockTileEntity getMultiblockTileEntityByInstanceID(int id) {
        return this.activeMultiblocks.get(Integer.valueOf(id)).getTileEntity();
    }
    
    /**
     * register a new multiblock instance with an internally generated id and
     * activate the Tile Entities
     * 
     * @param structure
     * @param te
     *            the multiblock's master tile-entity
     */
    public void registerMultiblockInstance(String structure,
            MultiblockTileEntity te) {
        synchronized (MultiblockRegistry.SYNC_TOKEN) {
            if (!this.idProvider.hasFreeID())
                throw new IllegalStateException(
                        "no id's remaining. cannot register instance");
            int id = this.idProvider.getFreeID();
            this.idProvider.useID(id);
            if (!te.activateAsMaster(id, structure)) {
                this.idProvider.freeID(id);
                return;
            }
            this.activeMultiblocks.put(Integer.valueOf(id), new ActiveSet(
                    structure, te));
        }
    }
    
    /**
     * removes a multiblock from the world
     * 
     * @param id
     */
    public void unregisterMultiblockInstance(int id) {
        Multiblock structure = this.getMultiblockByInstanceID(id);
        MultiblockTileEntity ent = this.getMultiblockTileEntityByInstanceID(id);
        for (MultiblockPoint pt : structure.getMultiblockPoints()) {
            TileEntity te = ent.getWorldObj().getTileEntity(
                    pt.getX(ent.xCoord), pt.getY(ent.yCoord),
                    pt.getZ(ent.zCoord));
            if (te instanceof MultiblockTileEntity) {
                ((MultiblockTileEntity) te).resetToIdle();
            }
        }
        this.activeMultiblocks.remove(Integer.valueOf(id));
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
        return this.multiblocks.keySet().toArray(new String[] {});
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
        this.idProvider.useID(instanceID);
        this.activeMultiblocks.put(Integer.valueOf(instanceID), new ActiveSet(
                structure, te));
    }
    
    @SuppressWarnings("javadoc")
    public Multiblock[] getRegisteredMultiblocks() {
        return this.multiblocks.values().toArray(new Multiblock[] {});
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
