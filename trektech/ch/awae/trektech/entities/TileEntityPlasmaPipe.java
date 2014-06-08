package ch.awae.trektech.entities;

import ch.awae.trektech.EnumPlasmaTypes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPlasmaPipe extends TileEntity implements IPlasmaPipe,
		IPlasmaConnection {

	private short maxPlasma = 1000;
	private short currentPlasma = 0;
	private EnumPlasmaTypes plasmaType;
	private int textureID;
	private float radius;

	public TileEntityPlasmaPipe(EnumPlasmaTypes plasma, int textureID,
			float radius) {
		this.plasmaType = plasma;
		this.textureID = textureID;
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
	public int getTextureID() {
		return this.textureID;
	}

	@Override
	public float getPipeRadius() {
		return this.radius;
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
