package ch.awae.trektech.entities;

import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.Properties;
import ch.awae.trektech.TrekTech;
import ch.modjam.generic.GenericTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPlasmaPipe extends ATileEntityPlasmaSystem implements
		IPlasmaPipe, IPlasmaConnection, IWrenchable {

	private int currentPlasma = 0;
	private EnumPlasmaTypes plasmaType;
	private String texture;
	private float radius;

	public TileEntityPlasmaPipe(EnumPlasmaTypes plasma, String texture,
			float radius) {
		this.plasmaType = plasma;
		this.texture = TrekTech.MODID + ":textures/blocks/" + texture + ".png";
		this.radius = radius;
	}

	public TileEntityPlasmaPipe() {
		this.plasmaType = EnumPlasmaTypes.NEUTRAL;
		this.radius = 0;
		this.texture = "";
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
		return te.connectsToPlasmaConnection(this.plasmaType, opposite);
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
	public float getParticlesPerBar(EnumPlasmaTypes plasma,
			ForgeDirection direction) {
		// TODO: extract magical number
		return plasma == this.plasmaType ? 100 : 0;
	}

	@Override
	public int getParticleCount(EnumPlasmaTypes plasma, ForgeDirection direction) {
		return plasma == this.plasmaType ? this.currentPlasma : 0;
	}

	@Override
	public void applyParticleFlow(EnumPlasmaTypes plasma,
			ForgeDirection direction, int particleCount) {
		if (plasma == this.plasmaType)
			this.currentPlasma += particleCount;
	}

	@Override
	public boolean connectsToPlasmaConnection(EnumPlasmaTypes plasma,
			ForgeDirection direction) {
		return plasma == this.plasmaType;
	}

	@Override
	public boolean setParticleCount(EnumPlasmaTypes plasma,
			ForgeDirection direction, int count) {
		if (plasma == this.plasmaType) {
			this.currentPlasma = count;
			return true;
		}
		return false;
	}

	// -- ATileEntityPlasmaSystem --
	@Override
	public void customTick() {
		// NOT NEEDED
		System.out.println(this.plasmaType);
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		tag.setInteger("PlasmaType", this.plasmaType.ordinal());
		tag.setString("Texture", this.texture);
		tag.setFloat("Radius", this.radius);
		tag.setInteger("Plasma", this.currentPlasma);
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		this.plasmaType = EnumPlasmaTypes.VALID[tag.getInteger("PlasmaType")];
		this.texture = tag.getString("Texture");
		this.radius = tag.getFloat("Radius");
		this.currentPlasma = tag.getInteger("Plasma");
	}

	@Override
	public boolean wrench(EntityPlayer player) {
		if (worldObj.isRemote) {
			player.addChatMessage(new ChatComponentText("Plasma Type: "
					+ this.plasmaType.toString()));
			// TODO: extract magical number
			float pressure = this.currentPlasma / 100f;
			player.addChatMessage(new ChatComponentText("Plasma Level: "
					+ pressure + " bar"));
			return true;
		}
		return false;
	}

	@Override
	public boolean wrenchSneaking(EntityPlayer player) {
		return false;
	}

}