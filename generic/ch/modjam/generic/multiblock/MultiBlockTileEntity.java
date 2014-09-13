package ch.modjam.generic.multiblock;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import ch.modjam.generic.tileEntity.GenericTileEntity;

/**
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public abstract class MultiblockTileEntity extends GenericTileEntity {
    
    private EnumTileEntityState state;
    private int                 instanceID;
    private String              multiblockID;
    private boolean             isRegistered;
    
    /**
     * instantiate a new MultiblockTileEntity
     */
    public MultiblockTileEntity() {
        this.state = EnumTileEntityState.IDLE;
        this.multiblockID = null;
        this.isRegistered = false;
    }
    
    @Override
    public final void tick() {
        switch (this.state) {
            case MASTER:
                if (!this.isRegistered) {
                    this.register();
                }
                this.masterTick();
                break;
            case SLAVE:
                this.slaveTick();
                break;
            default:
                if (!this.isRegistered) {
                    this.isRegistered = false;
                }
                break;
        }
    }
    
    private void register() {
        MultiblockRegistry.instance().registerMultiblockInstance(
                this.instanceID, this.multiblockID, this);
    }
    
    /**
     * This ticking method is executed when the TileEntity is a master
     */
    public abstract void masterTick();
    
    /**
     * This ticking method is executed when the TileEntity is a slave
     */
    public abstract void slaveTick();
    
    /**
     * store custom data
     * 
     * @param tag
     */
    public abstract void writeCustomNBT(NBTTagCompound tag);
    
    /**
     * restore custom data
     * 
     * @param tag
     */
    public abstract void readCustomNBT(NBTTagCompound tag);
    
    @Override
    public final void writeNBT(NBTTagCompound tag) {
        tag.setInteger("EntityState", this.state.ordinal());
        tag.setInteger("InstanceID", this.instanceID);
        this.writeCustomNBT(tag);
    }
    
    @Override
    public final void readNBT(NBTTagCompound tag) {
        this.state = EnumTileEntityState.values()[tag.getInteger("EntityState")];
        this.instanceID = tag.getInteger("InstanceID");
        this.readCustomNBT(tag);
    }
    
    /**
     * activate this TileEntity as a slave of a given MultiBlock
     * 
     * @param instanceID
     *            the MultiBlock's instance ID
     * @throws AlreadyOwnedByMultiblockException
     *             whenever a TileEntity that is already registered to a
     *             structure is attempted to be registered to a new one
     */
    @SuppressWarnings("hiding")
    public final void activateAsSlave(int instanceID)
            throws AlreadyOwnedByMultiblockException {
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        if (this.state != EnumTileEntityState.IDLE)
            throw new AlreadyOwnedByMultiblockException();
        this.state = EnumTileEntityState.SLAVE;
        this.instanceID = instanceID;
        this.updateBlockActivationState(true);
    }
    
    private void updateBlockActivationState(boolean activationState) {
        if (this.blockType instanceof MultiblockBlock) {
            MultiblockBlock.setActivationState(this.worldObj, this.xCoord,
                    this.yCoord, this.zCoord, activationState);
        }
    }
    
    /**
     * resets the multiblock's internal state to idle and removes its
     * tile-entity registration.
     */
    public final void resetToIdle() {
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        this.state = EnumTileEntityState.IDLE;
        this.updateBlockActivationState(false);
    }
    
    /**
     * @return <tt>true</tt> if the tile-entity is idle and free to be
     *         registered, <tt>false</tt> otherwise
     */
    public boolean isIdle() {
        return this.state == EnumTileEntityState.IDLE;
    }
    
    /**
     * activates the tile-entity as the master of the multiblock instance with
     * the given ID. This also registers all the multiblock slaves to this
     * entity. In case the multiblock can not be completely registered (e.g.
     * because one of its TileEntities is already registered to another
     * structure), the slave registration is reverted. However this is not
     * protected against multiple access. The <i>later</i> master will simply
     * fail to register overlapping Entities, even if the <i>first</i> fails
     * too, which would make the <i>later</i> one valid.
     * 
     * @param instanceID
     * @param multiblockID
     * @return <tt>true</tt> if structure registration was successful,
     *         <tt>false</tt> otherwise.
     */
    @SuppressWarnings("hiding")
    public boolean activateAsMaster(int instanceID, String multiblockID) {
        this.markDirty();
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        if (this.state != EnumTileEntityState.IDLE)
            return false;
        MultiblockPoint points[] = MultiblockRegistry.instance()
                .getMultiblockByStructureID(multiblockID).getMultiblockPoints();
        int counter = 0;
        try {
            for (MultiblockPoint pt : points) {
                int x = pt.getX(this.xCoord);
                int y = pt.getY(this.yCoord);
                int z = pt.getZ(this.zCoord);
                TileEntity te = this.worldObj.getTileEntity(x, y, z);
                if (te instanceof MultiblockTileEntity) {
                    ((MultiblockTileEntity) te).activateAsSlave(instanceID);
                }
                counter++;
            }
            this.state = EnumTileEntityState.MASTER;
            this.instanceID = instanceID;
            this.multiblockID = multiblockID;
            this.isRegistered = true;
            this.updateBlockActivationState(true);
            return true;
        } catch (AlreadyOwnedByMultiblockException ex) {
            // reset previously set slaves
            for (MultiblockPoint pt : points) {
                if (counter == 0) {
                    break;
                }
                int x = pt.getX(this.xCoord);
                int y = pt.getY(this.yCoord);
                int z = pt.getZ(this.zCoord);
                TileEntity te = this.worldObj.getTileEntity(x, y, z);
                if (te instanceof MultiblockTileEntity) {
                    ((MultiblockTileEntity) te).resetToIdle();
                }
                counter--;
            }
            this.updateBlockActivationState(false);
        }
        return false;
    }
    
    /**
     * @return the instanceID
     */
    public int getInstanceID() {
        return this.instanceID;
    }
    
}
