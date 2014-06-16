package ch.awae.trektech.entities;

import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.Properties;
import ch.awae.trektech.TrekTech;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPlasmaPipeCombined extends ATileEntityPlasmaSystem
		implements IPlasmaPipe, IPlasmaConnection {

	private int currentNeutralPlasma = 0;
	private int currentLowPlasma = 0;
	private String texture = "";
	private float radius = 0;

	public TileEntityPlasmaPipeCombined(String texture, float radius) {
		this.texture = TrekTech.MODID + ":textures/blocks/" + texture + ".png";
		this.radius = radius;
	}

	public TileEntityPlasmaPipeCombined() {
	}

	@Override
	public float getParticlesPerBar(EnumPlasmaTypes plasma,
			ForgeDirection direction) {
		switch (plasma) {
		case NEUTRAL:
		case LOW:
			return 100;
		default:
			return 0;
		}
	}

	@Override
	public int getParticleCount(EnumPlasmaTypes plasma, ForgeDirection direction) {
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
	public void applyParticleFlow(EnumPlasmaTypes plasma,
			ForgeDirection direction, int particleCount) {
		switch (plasma) {
		case NEUTRAL:
			this.currentNeutralPlasma += particleCount;
			break;
		case LOW:
			this.currentLowPlasma += particleCount;
			break;
		default:
			break;
		}
	}

	@Override
	public boolean connectsToPlasmaConnection(EnumPlasmaTypes plasma,
			ForgeDirection direction) {
		switch (plasma) {
		case NEUTRAL:
		case LOW:
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean setParticleCount(EnumPlasmaTypes plasma,
			ForgeDirection direction, int count) {
		switch (plasma) {
		case NEUTRAL:
			this.currentNeutralPlasma = count;
			return true;
		case LOW:
			this.currentLowPlasma = count;
			return true;
		default:
			return false;
		}
	}

	// -- IPlasmaPipe --

	@Override
	public boolean connectsTo(ForgeDirection d) {
		TileEntity t = this.worldObj.getTileEntity(this.xCoord + d.offsetX,
				this.yCoord + d.offsetY, this.zCoord + d.offsetZ);
		if (t == null || !(t instanceof IPlasmaConnection))
			return false;
		IPlasmaConnection te = (IPlasmaConnection) t;
		ForgeDirection opposite = d.getOpposite();
		return te.connectsToPlasmaConnection(EnumPlasmaTypes.NEUTRAL, opposite)
				|| te.connectsToPlasmaConnection(EnumPlasmaTypes.LOW, opposite);
	}

	@Override
	public float getPipeRadius() {
		return this.radius;
	}

	@Override
	public String getTexture() {
		return this.texture;
	}

	@Override
	public void customTick() {
		// NOT REQUIRED
	}

	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		tag.setString("Texture", this.texture);
		tag.setFloat("Radius", this.radius);
	}

	@Override
	public void readCustomNBT(NBTTagCompound tag) {
		this.texture = tag.getString("Texture");
		this.radius = tag.getFloat("Radius");

	}

}
