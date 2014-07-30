package ch.modjam.generic.multiblock;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import ch.modjam.generic.tileEntity.GenericTileEntity;

/**
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public abstract class MultiblockTileEntity extends GenericTileEntity {

	private EnumTileEntityState	state;
	private long				instanceID;
	private String				multiblockName;
	private boolean				isRegistered;

	/**
	 * instantiate a new MultiblockTileEntity
	 */
	public MultiblockTileEntity() {
		this.state = EnumTileEntityState.IDLE;
		this.instanceID = -1;
		this.multiblockName = null;
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
				// TODO: implement
				// if (!this.isRegistered) {
				// this.isRegistered = false;
				// }
				break;
		}
	}

	private void register() {
		// TODO: fix the following which caused compile errors
		// MultiblockRegistry.instance().registerMultiblockInstance(
		// this.instanceID, this.multiblockID, this);
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
		tag.setLong("InstanceID", this.instanceID);
		this.writeCustomNBT(tag);
	}

	@Override
	public final void readNBT(NBTTagCompound tag) {
		this.state = EnumTileEntityState.values()[tag.getInteger("EntityState")];
		this.instanceID = tag.getLong("InstanceID");
		this.readCustomNBT(tag);
	}

	/**
	 * activate this TileEntity as a slave of a given MultiBlock
	 * 
	 * @param instanceIDarg the MultiBlock's instance ID
	 * @throws AlreadyOwnedByMultiblockException whenever a TileEntity that is already registered to
	 *             a structure is attempted to be registered to a new one
	 */
	public final void activateAsSlave(long instanceIDarg) throws AlreadyOwnedByMultiblockException {
		if (this.state != EnumTileEntityState.IDLE)
			throw new AlreadyOwnedByMultiblockException();
		this.state = EnumTileEntityState.SLAVE;
		this.instanceID = instanceIDarg;
	}

	/**
	 * resets the multiblock's internal state to idle and removes its tile-entity registration.
	 */
	public final void resetToIdle() {
		this.state = EnumTileEntityState.IDLE;
		this.instanceID = -1;
	}

	/**
	 * @return <tt>true</tt> if the tile-entity is idle and free to be registered, <tt>false</tt>
	 *         otherwise
	 */
	public boolean isIdle() {
		return this.state == EnumTileEntityState.IDLE;
	}

	/**
	 * activates the tile-entity as the master of the multiblock instance with the given ID. This
	 * also registers all the multiblock slaves to this entity. In case the multiblock can not be
	 * completely registered (e.g. because one of its TileEntities is already registered to another
	 * structure), the slave registration is reverted. However this is not protected against
	 * multiple access. The <i>later</i> master will simply fail to register overlapping Entities,
	 * even if the <i>first</i> fails too, which would make the <i>later</i> one valid.
	 * 
	 * @param instanceIDarg
	 * @param multiblockID
	 * @return <tt>true</tt> if structure registration was successful, <tt>false</tt> otherwise.
	 */
	public boolean activateAsMaster(long instanceIDarg, String multiblockID) {
		if (this.state != EnumTileEntityState.IDLE)
			return false;
		MultiblockPoint points[] = MultiblockRegistry.instance().getMultiblockByInstanceID(
			instanceIDarg).getMultiblockPoints();
		int counter = 0;
		try {
			for (MultiblockPoint pt : points) {
				int x = pt.getX(this.xCoord);
				int y = pt.getY(this.yCoord);
				int z = pt.getZ(this.zCoord);
				TileEntity te = this.worldObj.getTileEntity(x, y, z);
				if (te instanceof MultiblockTileEntity) {
					((MultiblockTileEntity) te).activateAsSlave(instanceIDarg);
				}
				counter++;
			}
			this.state = EnumTileEntityState.MASTER;
			this.instanceID = instanceIDarg;
			this.multiblockName = multiblockID;
			this.isRegistered = true;
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
		}
		return false;
	}

	/**
	 * @return the multiblockName
	 */
	public String getMultiblockName() {
		return this.multiblockName;
	}

}
