package ch.awae.trektech.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;
import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.TrekTech;

/**
 * TileEntity for the combined Plasma Pipe
 * 
 * TODO: merge into TileEntityPlasmaPipe
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
public class TileEntityPlasmaPipeCombined extends ATileEntityPlasmaSystem
		implements IPlasmaPipe {

	/**
	 * Particle amount required to reach 1 bar
	 */
	public static final float PARTICLES_PER_BAR = 100f;

	private int currentNeutralPlasma = 0;
	private int currentLowPlasma = 0;
	private String texture = "";
	private float radius = 0;

	/**
	 * Default Constructor
	 * 
	 * @param texture
	 * @param radius
	 */
	public TileEntityPlasmaPipeCombined(String texture, float radius) {
		this.texture = TrekTech.MODID + ":textures/blocks/" + texture + ".png";
		this.radius = radius;
	}

	/**
	 * Constructor for Entity restoring. Only use if data is restored from NBT
	 * afterwards!
	 */
	public TileEntityPlasmaPipeCombined() {
	}

	@Override
	public float getParticlesPerBar(EnumPlasmaTypes plasma,
			ForgeDirection direction) {
		switch (plasma) {
		case NEUTRAL:
		case LOW:
			return PARTICLES_PER_BAR;
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
	public int applyParticleFlow(EnumPlasmaTypes plasma,
			ForgeDirection direction, int particleCount) {
		switch (plasma) {
		case NEUTRAL:
			this.currentNeutralPlasma += particleCount;
			return particleCount;
		case LOW:
			this.currentLowPlasma += particleCount;
			return particleCount;
		default:
			return 0;
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
	public void writeNBT(NBTTagCompound tag) {
		tag.setString("Texture", this.texture);
		tag.setFloat("Radius", this.radius);
		tag.setInteger("Neutral", this.currentNeutralPlasma);
		tag.setInteger("Low", this.currentLowPlasma);
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		this.texture = tag.getString("Texture");
		this.radius = tag.getFloat("Radius");
		this.currentNeutralPlasma = tag.getInteger("Neutral");
		this.currentLowPlasma = tag.getInteger("Low");
	}

	@Override
	public boolean onWrench(EntityPlayer player) {
		if (this.worldObj.isRemote && !player.isSneaking()) {
			player.addChatMessage(new ChatComponentText("Plasma Type: "
					+ EnumPlasmaTypes.NEUTRAL.toString()));
			float pressure = this.currentNeutralPlasma / PARTICLES_PER_BAR;
			player.addChatMessage(new ChatComponentText(
					"Current Plasma Pressure: " + pressure + " bar"));
			player.addChatMessage(new ChatComponentText("Plasma Type: "
					+ EnumPlasmaTypes.LOW.toString()));
			pressure = this.currentLowPlasma / PARTICLES_PER_BAR;
			player.addChatMessage(new ChatComponentText(
					"Current Plasma Pressure: " + pressure + " bar"));
			return true;
		}
		return false;
	}
}
