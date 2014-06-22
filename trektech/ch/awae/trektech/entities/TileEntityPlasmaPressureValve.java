package ch.awae.trektech.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import ch.awae.trektech.EnumPlasmaTypes;

/**
 * This valve limits flow-through pressure to 10bar
 * 
 * TODO: make pressure level customizable
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
public class TileEntityPlasmaPressureValve extends ATileEntityPlasmaSystem {

	private int plasmaLevel;
	private EnumPlasmaTypes plasmaType;
	private float pressureThreshold;

	/**
	 * Full-Fledged Constructor
	 * 
	 * @param plasmaType
	 *            the plasma Type for this valve
	 * @param pressureThreshold
	 *            the pressure to block at
	 */
	public TileEntityPlasmaPressureValve(EnumPlasmaTypes plasmaType,
			float pressureThreshold) {
		this.plasmaType = plasmaType;
		this.pressureThreshold = pressureThreshold;
	}

	@Override
	public float getParticlesPerBar(EnumPlasmaTypes plasma,
			ForgeDirection direction) {
		return plasma == this.plasmaType ? TileEntityPlasmaPipe.PARTICLES_PER_BAR
				: 0;
	}

	@Override
	public int getParticleCount(EnumPlasmaTypes plasma, ForgeDirection direction) {
		return plasma == this.plasmaType ? this.plasmaLevel : 0;
	}

	@Override
	public int applyParticleFlow(EnumPlasmaTypes plasma,
			ForgeDirection direction, int particleCount) {
		System.out.println(particleCount);
		if (plasma != this.plasmaType)
			return 0;
		if (particleCount < 0) {
			this.plasmaLevel += particleCount;
			return particleCount;
		}
		int trueCount = particleCount;
		int maxChange = (int) ((this.pressureThreshold - (this.plasmaLevel / TileEntityPlasmaPipe.PARTICLES_PER_BAR)) * TileEntityPlasmaPipe.PARTICLES_PER_BAR);
		if (trueCount > maxChange)
			trueCount = maxChange;
		this.plasmaLevel += trueCount;
		return trueCount;
	}

	@Override
	public boolean connectsToPlasmaConnection(EnumPlasmaTypes plasma,
			ForgeDirection direction) {
		return plasma == this.plasmaType;
	}

	@Override
	public boolean setParticleCount(EnumPlasmaTypes plasma,
			ForgeDirection direction, int count) {
		if (plasma == this.plasmaType)
			this.plasmaLevel = count;
		return plasma == this.plasmaType;
	}

	@Override
	public boolean onWrench(EntityPlayer player) {
		// TODO implement wrenching actions
		return false;
	}

	@Override
	public void customTick() {
		// not required
		System.out.println(this.pressureThreshold + " | "
				+ (this.plasmaLevel / TileEntityPlasmaPipe.PARTICLES_PER_BAR));
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		tag.setInteger("Plasma", this.plasmaLevel);
		tag.setFloat("Treshold", this.pressureThreshold);
		tag.setInteger("Type", this.plasmaType.ordinal());
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		this.plasmaLevel = tag.getInteger("Plasma");
		this.pressureThreshold = tag.getFloat("Threshold");
		this.plasmaType = EnumPlasmaTypes.values()[tag.getInteger("Type")];
	}

}
