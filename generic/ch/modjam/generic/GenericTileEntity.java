package ch.modjam.generic;

import net.minecraft.tileentity.TileEntity;

public abstract class GenericTileEntity extends TileEntity {

	@Override
	public void updateEntity() {
		super.updateEntity();
	}

	/**
	 * Tick the entity
	 */
	public abstract void tick();

}
