package ch.awae.trektech.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import ch.awae.trektech.EnumPlasmaTypes;

/**
 * This valve limits flow-through pressure to 10bar
 * 
 * TODO: make variable
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
public class TileEntityPlasmaPressureValve extends ATileEntityPlasmaSystem {

	@Override
	public float getParticlesPerBar(EnumPlasmaTypes plasma,
			ForgeDirection direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getParticleCount(EnumPlasmaTypes plasma, ForgeDirection direction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void applyParticleFlow(EnumPlasmaTypes plasma,
			ForgeDirection direction, int particleCount) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean connectsToPlasmaConnection(EnumPlasmaTypes plasma,
			ForgeDirection direction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setParticleCount(EnumPlasmaTypes plasma,
			ForgeDirection direction, int count) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onWrench(EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void customTick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		// TODO Auto-generated method stub

	}

}
