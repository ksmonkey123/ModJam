package ch.awae.trektech.entities;

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
public class TileEntityPlasmaPressureValve extends TileEntityPlasmaPipe {

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
		super(plasmaType, null, 0);
		this.pressureThreshold = 5f;
	}

	@Override
	public int getMaxAcceptance(EnumPlasmaTypes plasma, ForgeDirection direction) {
		return (int) ((this.pressureThreshold - (this.getParticleCount(plasma,
				direction) / PARTICLES_PER_BAR)) * PARTICLES_PER_BAR);
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		super.writeNBT(tag);
		tag.setFloat("Treshold", this.pressureThreshold);
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		super.readNBT(tag);
		this.pressureThreshold = tag.getFloat("Treshold");
	}

}
