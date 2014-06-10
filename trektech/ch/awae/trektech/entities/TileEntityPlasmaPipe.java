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

public class TileEntityPlasmaPipe extends TileEntity implements IPlasmaPipe,
		IPlasmaConnection {

	private short maxPlasma = 1000;
	private short currentPlasma = 0;
	private EnumPlasmaTypes plasmaType;
	private ResourceLocation texture;
	private float radius;

	public TileEntityPlasmaPipe(EnumPlasmaTypes plasma, String texture,
			float radius) {
		this.plasmaType = plasma;
		this.texture = new ResourceLocation(TrekTech.MODID
				+ ":textures/blocks/" + texture + ".png");
		this.radius = radius;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setShort("Plasma", this.currentPlasma);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.currentPlasma = tag.getShort("Plasma");
	}

	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		System.out.println("gotPacket");
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord,
				this.zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		System.out.println("gotPacket");
		readFromNBT(pkt.func_148857_g());
	}

	@Override
	public void updateEntity() {
		System.out.println(this.currentPlasma);
		for (ForgeDirection d : ForgeDirection.values()) {
			if (d == ForgeDirection.UNKNOWN)
				continue;
			TileEntity entity = worldObj.getTileEntity(this.xCoord + d.offsetX,
					this.yCoord + d.offsetY, this.zCoord + d.offsetZ);
			if (entity == null || !(entity instanceof IPlasmaConnection))
				continue;
			IPlasmaConnection t = (IPlasmaConnection) entity;
			// make actual transfer
			if (t.acceptsPlasma(this.plasmaType)) {
				short halfDiff = (short) Math.min(
						Properties.PLASMA_TRANSFER_SPEED,
						(this.currentPlasma - t
								.getCurrentPlasmaAmount(this.plasmaType)) / 2);
				if (halfDiff <= 0)
					continue;
				this.currentPlasma -= t.fillPlasma(this.plasmaType, halfDiff);
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
		return te.acceptsPlasma(this.plasmaType)
				|| te.providesPlasma(this.plasmaType);
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
		return plasma == this.plasmaType;
	}

	@Override
	public boolean providesPlasma(EnumPlasmaTypes plasma) {
		return plasma == this.plasmaType;
	}

	@Override
	public short getMaxPlasmaAmount(EnumPlasmaTypes plasma) {
		return plasma == this.plasmaType ? this.maxPlasma : 0;
	}

	@Override
	public short getCurrentPlasmaAmount(EnumPlasmaTypes plasma) {
		return plasma == this.plasmaType ? this.currentPlasma : 0;
	}

	@Override
	public short fillPlasma(EnumPlasmaTypes plasma, short amount) {
		if (plasma != this.plasmaType)
			return 0;
		short filling = (short) Math.min(this.maxPlasma - this.currentPlasma,
				amount);
		this.currentPlasma += filling;
		return filling;
	}

}
