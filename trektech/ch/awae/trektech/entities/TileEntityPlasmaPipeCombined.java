package ch.awae.trektech.entities;

import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.Properties;
import ch.awae.trektech.TrekTech;
import ch.modjam.generic.GenericTileEntity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPlasmaPipeCombined extends GenericTileEntity implements
		IPlasmaPipe, IPlasmaConnection {

	private short maxNeutralPlasma = 1000;
	private short currentNeutralPlasma = 0;
	private short maxLowPlasma = 1000;
	private short currentLowPlasma = 0;
	private String texture;
	private float radius;

	public TileEntityPlasmaPipeCombined(String texture, float radius) {
		this.texture = TrekTech.MODID + ":textures/blocks/" + texture + ".png";
		this.radius = radius;
	}

	public TileEntityPlasmaPipeCombined() {
		this.texture = "";
		this.radius = 0;
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		tag.setShort("Plasma1", this.currentNeutralPlasma);
		tag.setShort("Plasma2", this.currentLowPlasma);
		tag.setFloat("Radius", this.radius);
		tag.setString("Texture", this.texture);
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		this.currentNeutralPlasma = tag.getShort("Plasma1");
		this.currentLowPlasma = tag.getShort("Plasma2");
		this.texture = tag.getString("Texture");
		this.radius = tag.getFloat("Radius");
	}

	@Override
	public void tick() {
		for (ForgeDirection d : ForgeDirection.values()) {
			if (d == ForgeDirection.UNKNOWN)
				continue;
			TileEntity entity = worldObj.getTileEntity(this.xCoord + d.offsetX,
					this.yCoord + d.offsetY, this.zCoord + d.offsetZ);
			if (entity == null || !(entity instanceof IPlasmaConnection))
				continue;
			IPlasmaConnection t = (IPlasmaConnection) entity;
			// make actual transfer
			ForgeDirection opposite = d.getOpposite();
			if (t.acceptsPlasma(EnumPlasmaTypes.NEUTRAL, opposite)) {
				short halfDiff = (short) Math.min(
						Properties.PLASMA_TRANSFER_SPEED,
						(this.currentNeutralPlasma - t.getCurrentPlasmaAmount(
								EnumPlasmaTypes.NEUTRAL, opposite)) / 2);
				if (halfDiff <= 0)
					continue;
				this.currentNeutralPlasma -= t.fillPlasma(
						EnumPlasmaTypes.NEUTRAL, halfDiff, opposite);
			}
			if (t.acceptsPlasma(EnumPlasmaTypes.LOW, opposite)) {
				short halfDiff = (short) Math.min(
						Properties.PLASMA_TRANSFER_SPEED,
						(this.currentLowPlasma - t.getCurrentPlasmaAmount(
								EnumPlasmaTypes.LOW, opposite)) / 2);
				if (halfDiff <= 0)
					continue;
				this.currentLowPlasma -= t.fillPlasma(EnumPlasmaTypes.LOW,
						halfDiff, opposite);
			}
		}
		this.markDirty();
	}

	// -- IPlasmaPipe --
	@Override
	public boolean connectsTo(ForgeDirection d) {
		TileEntity t = this.worldObj.getTileEntity(this.xCoord + d.offsetX,
				this.yCoord + d.offsetY, this.zCoord + d.offsetZ);
		if (t == null || !(t instanceof IPlasmaConnection))
			return false;
		IPlasmaConnection te = (IPlasmaConnection) t;
		ForgeDirection opp = d.getOpposite();
		return te.acceptsPlasma(EnumPlasmaTypes.NEUTRAL, opp)
				|| te.providesPlasma(EnumPlasmaTypes.NEUTRAL, opp)
				|| te.acceptsPlasma(EnumPlasmaTypes.LOW, opp)
				|| te.providesPlasma(EnumPlasmaTypes.LOW, opp);
	}

	@Override
	public float getPipeRadius() {
		return this.radius;
	}

	@Override
	public String getTexture() {
		return this.texture;
	}

	// -- IPlasmaConnection --
	@Override
	public boolean acceptsPlasma(EnumPlasmaTypes plasma, ForgeDirection d) {
		return plasma == EnumPlasmaTypes.NEUTRAL
				|| plasma == EnumPlasmaTypes.LOW;
	}

	@Override
	public boolean providesPlasma(EnumPlasmaTypes plasma, ForgeDirection d) {
		return plasma == EnumPlasmaTypes.NEUTRAL
				|| plasma == EnumPlasmaTypes.LOW;
	}

	@Override
	public short getMaxPlasmaAmount(EnumPlasmaTypes plasma, ForgeDirection d) {
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
	public short getCurrentPlasmaAmount(EnumPlasmaTypes plasma, ForgeDirection d) {
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
	public short fillPlasma(EnumPlasmaTypes plasma, short amount,
			ForgeDirection d) {
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
