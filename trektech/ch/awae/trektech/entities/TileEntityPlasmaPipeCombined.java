package ch.awae.trektech.entities;

import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.TrekTech;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPlasmaPipeCombined extends TileEntity implements
		IPlasmaPipe, IPlasmaConnection {

	private short maxNeutralPlasma = 1000;
	private short currentNeutralPlasma = 0;
	private short maxLowPlasma = 1000;
	private short currentLowPlasma = 0;
	private ResourceLocation texture;
	private float radius;

	public TileEntityPlasmaPipeCombined(String texture, float radius) {
		this.texture = new ResourceLocation(TrekTech.MODID
				+ ":textures/blocks/" + texture + ".png");
		this.radius = radius;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		tag.setShort("Plasma1", this.currentNeutralPlasma);
		tag.setShort("Plasma2", this.currentLowPlasma);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		this.currentNeutralPlasma = tag.getShort("Plasma1");
		this.currentLowPlasma = tag.getShort("Plasma2");
	}

	@Override
	public void updateEntity() {
		// TODO: implement entity tick
	}

	// -- IPlasmaPipe --
	@Override
	public boolean connectsTo(ForgeDirection d) {
		TileEntity t = this.worldObj.getTileEntity(this.xCoord + d.offsetX,
				this.yCoord + d.offsetY, this.zCoord + d.offsetZ);
		if (t == null || !(t instanceof IPlasmaConnection))
			return false;
		IPlasmaConnection te = (IPlasmaConnection) t;
		return te.acceptsPlasma(EnumPlasmaTypes.NEUTRAL)
				|| te.providesPlasma(EnumPlasmaTypes.NEUTRAL)
				|| te.acceptsPlasma(EnumPlasmaTypes.LOW)
				|| te.providesPlasma(EnumPlasmaTypes.LOW);
	}

	@Override
	public float getPipeRadius() {
		return this.radius;
	}

	@Override
	public ResourceLocation getTexture() {
		return this.texture;
	}

	// -- IPlasmaConnection --
	@Override
	public boolean acceptsPlasma(EnumPlasmaTypes plasma) {
		return plasma == EnumPlasmaTypes.NEUTRAL
				|| plasma == EnumPlasmaTypes.LOW;
	}

	@Override
	public boolean providesPlasma(EnumPlasmaTypes plasma) {
		return plasma == EnumPlasmaTypes.NEUTRAL
				|| plasma == EnumPlasmaTypes.LOW;
	}

	@Override
	public short getMaxPlasmaAmount(EnumPlasmaTypes plasma) {
		switch (plasma) {
		case NEUTRAL:
			return this.maxNeutralPlasma;
		case LOW:
			return this.maxLowPlasma;
		default:
			return 0;
		}
	}

	@Override
	public short getCurrentPlasmaAmount(EnumPlasmaTypes plasma) {
		switch (plasma) {
		case NEUTRAL:
			return this.currentNeutralPlasma;
		case LOW:
			return this.currentLowPlasma;
		default:
			return 0;
		}
	}

	@Override
	public short fillPlasma(EnumPlasmaTypes plasma, short amount) {
		if (plasma == EnumPlasmaTypes.NEUTRAL) {
			short filling = (short) Math.min(this.maxNeutralPlasma
					- this.currentNeutralPlasma, amount);
			this.currentNeutralPlasma += filling;
			return filling;
		}
		if (plasma == EnumPlasmaTypes.LOW) {
			short filling = (short) Math.min(this.maxLowPlasma
					- this.currentLowPlasma, amount);
			this.currentNeutralPlasma += filling;
			return filling;
		}
		return 0;
	}

}
