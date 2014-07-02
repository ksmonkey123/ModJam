package ch.modjam.generic.multiblock;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import ch.modjam.generic.tileEntity.GenericTileEntity;

/**
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public abstract class MultiBlockTileEntity extends GenericTileEntity {
    
    private EnumTileEntityState state;
    private int                 structureID;
    
    public MultiBlockTileEntity() {
        this.state = EnumTileEntityState.IDLE;
        this.structureID = -1;
    }
    
    @Override
    public final void tick() {
        switch (this.state) {
            case MASTER:
                this.masterTick();
                break;
            case SLAVE:
                this.slaveTick();
                break;
            default:
                break;
        }
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
        tag.setInteger("StructureID", this.structureID);
        this.writeCustomNBT(tag);
    }
    
    @Override
    public final void readNBT(NBTTagCompound tag) {
        this.state = EnumTileEntityState.values()[tag.getInteger("EntityState")];
        this.structureID = tag.getInteger("StructureID");
        this.readCustomNBT(tag);
    }
    
    /**
     * activate this TileEntity as a slave of a given MultiBlock
     * 
     * @param StructureID
     *            the MultiBlock's registration ID
     * @throws AlreadyOwnedByMultiblockException
     */
    public final void activateAsSlave(int StructureID)
            throws AlreadyOwnedByMultiblockException {
        if (this.state != EnumTileEntityState.IDLE)
            throw new AlreadyOwnedByMultiblockException();
        this.state = EnumTileEntityState.SLAVE;
        this.structureID = StructureID;
    }
    
    public final void resetToIdle() {
        this.state = EnumTileEntityState.IDLE;
        this.structureID = -1;
    }
    
    public boolean activateAsMaster(int StructureID) {
        if (this.state != EnumTileEntityState.IDLE)
            return false;
        MultiblockPoint points[] = MultiblockRegistry.instance()
                .getMultiblockByInstanceID(StructureID).getMultiblockPoints();
        int counter = 0;
        try {
            for (MultiblockPoint pt : points) {
                int x = pt.getX(this.xCoord);
                int y = pt.getY(this.yCoord);
                int z = pt.getZ(this.zCoord);
                TileEntity te = this.worldObj.getTileEntity(x, y, z);
                if (te instanceof MultiBlockTileEntity)
                    ((MultiBlockTileEntity) te).activateAsSlave(StructureID);
                counter++;
            }
            this.state = EnumTileEntityState.MASTER;
            this.structureID = StructureID;
            return true;
        } catch (AlreadyOwnedByMultiblockException ex) {
            // reset previously set slaves
            for (MultiblockPoint pt : points) {
                if (counter == 0)
                    break;
                int x = pt.getX(this.xCoord);
                int y = pt.getY(this.yCoord);
                int z = pt.getZ(this.zCoord);
                TileEntity te = this.worldObj.getTileEntity(x, y, z);
                if (te instanceof MultiBlockTileEntity)
                    ((MultiBlockTileEntity) te).resetToIdle();
                counter--;
            }
        }
        return false;
    }
    
}
