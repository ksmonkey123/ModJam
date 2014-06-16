package ch.awae.trektech.entities;

import net.minecraft.block.BlockFurnace;
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
import ch.awae.trektech.blocks.BlockPlasmaSource;
import ch.modjam.generic.BlockGenericDualStateDirected;
import ch.modjam.generic.EnumFace;

public class TileEntityPlasmaSource extends ATileEntityPlasmaSystem implements
		IPlasmaConnection, IInventory {

	private static final int PLASMA_PER_TICK = 5;
	private static final int PLASMA_PER_BAR = 1000;
	private static final int MAX_BAR = 10;

	private ItemStack stack;
	private int currentPlasma = 0;
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
	public void customTick() {

		if (this.currentItemRemainingTime > 0) {
			this.currentItemRemainingTime--;
			if (this.currentPlasma < PLASMA_PER_BAR * MAX_BAR)
				this.currentPlasma += PLASMA_PER_TICK;
		}
		if (this.currentItemRemainingTime == 0
				&& this.currentPlasma < PLASMA_PER_BAR * MAX_BAR)
			this.refuel();
	}

	private void refuel() {
		int value = this.getFuelValue();
		if (value <= 0 || this.stack.stackSize <= 0 || this.stack == null) {
			if (this.isActive) {
				this.isActive = false;
				this.updateBlock();
			}
		} else {
			this.currentItemBurnTime = this.currentItemRemainingTime = value;
			this.stack.stackSize--;
			if (!this.isActive) {
				this.isActive = true;
				this.updateBlock();
			}
		}
		if (this.stack != null && this.stack.stackSize <= 0) {
			this.stack = null;
		}
	}

	private void updateBlock() {
		BlockPlasmaSource.updatePlasmaSourceState(this.isActive, this.worldObj,
				this.xCoord, this.yCoord, this.zCoord);
	}

	private int getFuelValue() {
		return TileEntityFurnace.getItemBurnTime(this.stack);
	}

	@Override
	public void writeCustomNBT(NBTTagCompound tag) {
		// tag.setShort("Plasma", this.currentPlasma);
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
	public void readCustomNBT(NBTTagCompound tag) {
		// this.currentPlasma = tag.getShort("Plasma");
		this.currentItemBurnTime = tag.getInteger("CurrentTotal");
		this.currentItemRemainingTime = tag.getInteger("CurrentRemain");
		NBTTagList nbttaglist = tag.getTagList("Items", 10);
		NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(0);
		this.stack = ItemStack.loadItemStackFromNBT(nbttagcompound1);

	}

	@Override
	public String getInventoryName() {
		return "container.plasmaSource";
	}

	public boolean isBurning() {
		return this.currentItemRemainingTime > 0;
	}

	public int getBurnTimeRemainingScaled(int h) {
		return currentItemBurnTime == 0 ? 0
				: ((h * currentItemRemainingTime) / currentItemBurnTime);
	}

	public int getPlasmaLevelScaled(int h) {
		return currentPlasma == 0 ? 0 : (currentPlasma < PLASMA_PER_BAR
				* MAX_BAR ? ((currentPlasma * h) / PLASMA_PER_BAR * MAX_BAR)
				: h);
	}

	@Override
	public float getParticlesPerBar(EnumPlasmaTypes plasma,
			ForgeDirection direction) {
		return plasma == EnumPlasmaTypes.NEUTRAL ? PLASMA_PER_BAR : 0;
	}

	@Override
	public int getParticleCount(EnumPlasmaTypes plasma, ForgeDirection direction) {
		return (this.connectsToPlasmaConnection(plasma, direction)) ? this.currentPlasma
				: 0;
	}

	@Override
	public void applyParticleFlow(EnumPlasmaTypes plasma,
			ForgeDirection direction, int particleCount) {
		if (connectsToPlasmaConnection(plasma, direction))
			this.currentPlasma += particleCount;

	}

	@Override
	public boolean connectsToPlasmaConnection(EnumPlasmaTypes plasma,
			ForgeDirection direction) {
		return plasma == EnumPlasmaTypes.NEUTRAL
				&& BlockGenericDualStateDirected.getFaceDirectionForMeta(
						EnumFace.BACK, this.blockMetadata) == direction
						.getOpposite();
	}

	@Override
	public boolean setParticleCount(EnumPlasmaTypes plasma,
			ForgeDirection direction, int count) {
		if (this.connectsToPlasmaConnection(plasma, direction)) {
			this.currentPlasma = count;
			return true;
		}
		return false;
	}
}
