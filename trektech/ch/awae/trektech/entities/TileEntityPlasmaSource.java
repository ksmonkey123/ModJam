package ch.awae.trektech.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;
import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.Properties;
import ch.modjam.generic.GenericTileEntity;

public class TileEntityPlasmaSource extends GenericTileEntity implements
		IPlasmaConnection, IInventory {

	private static final short PLASMA_PER_TICK = 20;

	private ItemStack stack;
	private short maxPlasma = 1000;
	private short currentPlasma = 0;
	private int currentItemBurnTime = 0;
	private int currentItemRemainingTime = 0;
	private boolean isActive = false; // this value is used for caching. no need
										// to store in NBT

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return this.stack;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (slot != 0 || amount <= 0)
			return null;
		int realAmount = Math.min(this.stack.stackSize, amount);
		ItemStack stack = this.stack.copy();
		this.stack.stackSize -= realAmount;
		stack.stackSize = realAmount;
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return var1 == 0 ? this.stack : null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		if (var1 == 0)
			this.stack = var2;
	}

	@Override
	public String getInventoryName() {
		return "PlasmaSourceInventory";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return this.stack == null
				|| this.stack.getItem().equals(var2.getItem());
	}

	@Override
	public boolean acceptsPlasma(EnumPlasmaTypes plasma) {
		return false;
	}

	@Override
	public boolean providesPlasma(EnumPlasmaTypes plasma) {
		return plasma == EnumPlasmaTypes.NEUTRAL;
	}

	@Override
	public short getMaxPlasmaAmount(EnumPlasmaTypes plasma) {
		return 0;
	}

	@Override
	public short getCurrentPlasmaAmount(EnumPlasmaTypes plasma) {
		return 0;
	}

	@Override
	public short fillPlasma(EnumPlasmaTypes plasma, short amount) {
		return 0;
	}

	@Override
	public void tick() {
		if (this.currentItemRemainingTime > 0
				&& this.currentPlasma < this.maxPlasma - PLASMA_PER_TICK) {
			this.currentItemRemainingTime--;
			this.currentPlasma += PLASMA_PER_TICK;
		}
		System.out.println(this.currentItemRemainingTime + "/"
				+ this.currentItemBurnTime);
		if (this.currentItemRemainingTime == 0)
			this.refuel();

		for (ForgeDirection d : ForgeDirection.values()) {
			if (d == ForgeDirection.UNKNOWN)
				continue;
			TileEntity entity = worldObj.getTileEntity(this.xCoord + d.offsetX,
					this.yCoord + d.offsetY, this.zCoord + d.offsetZ);
			if (entity == null || !(entity instanceof IPlasmaConnection))
				continue;
			IPlasmaConnection t = (IPlasmaConnection) entity;
			// make actual transfer
			if (t.acceptsPlasma(EnumPlasmaTypes.NEUTRAL)) {
				short halfDiff = (short) Math
						.min(Properties.PLASMA_TRANSFER_SPEED,
								(this.currentPlasma - t
										.getCurrentPlasmaAmount(EnumPlasmaTypes.NEUTRAL)) / 2);
				if (halfDiff <= 0)
					continue;
				this.currentPlasma -= t.fillPlasma(EnumPlasmaTypes.NEUTRAL,
						halfDiff);
			}
		}
		this.markDirty();

	}

	private void refuel() {
		int value = this.getFuelValue();
		if (value <= 0 || this.stack.stackSize <= 0 || this.stack == null) {
			if (this.isActive) {
				this.isActive = false;
				// TODO: set block to inactive
			}
		} else {
			this.currentItemBurnTime = this.currentItemRemainingTime = value;
			this.stack.stackSize--;
			if (!this.isActive) {
				this.isActive = true;
				// TODO: set block to active
			}
		}
		if (this.stack != null && this.stack.stackSize <= 0) {
			this.stack = null;
		}
	}

	private int getFuelValue() {
		return TileEntityFurnace.getItemBurnTime(this.stack);
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		tag.setShort("Plasma", this.currentPlasma);
		tag.setInteger("CurrentTotal", this.currentItemBurnTime);
		tag.setInteger("CurrentRemain", this.currentItemRemainingTime);
		NBTTagList nbttaglist = new NBTTagList();
		if (this.stack != null) {
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			this.stack.writeToNBT(nbttagcompound1);
			nbttaglist.appendTag(nbttagcompound1);
		}
		tag.setTag("Items", nbttaglist);
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		this.currentPlasma = tag.getShort("Plasma");
		this.currentItemBurnTime = tag.getInteger("CurrentTotal");
		this.currentItemRemainingTime = tag.getInteger("CurrentRemain");
		NBTTagList nbttaglist = tag.getTagList("Items", 10);
		NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(0);
		this.stack = ItemStack.loadItemStackFromNBT(nbttagcompound1);

	}

}
