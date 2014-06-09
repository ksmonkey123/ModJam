package ch.awae.trektech.entities;

import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.TrekTech;
import net.minecraft.nbt.NBTTagCompound;
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
		tag.setShort("Plasma", this.currentPlasma);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		this.currentPlasma = tag.getShort("Plasma");
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
