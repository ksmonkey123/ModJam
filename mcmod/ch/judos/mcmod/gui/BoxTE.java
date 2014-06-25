package ch.judos.mcmod.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import ch.judos.mcmod.lib.Names;
import ch.modjam.generic.tileEntity.GenericTileEntity;
import ch.modjam.generic.tileEntity.IHasGui;

/**
 * @author j
 */
public class BoxTE extends GenericTileEntity implements IInventory, IHasGui {

	protected ItemStack[] stack;

	/**
	 * 
	 */
	public BoxTE() {
		this.stack = new ItemStack[1];
	}

	@Override
	public int getSizeInventory() {
		return this.stack.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.stack[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (slot < 0 || slot > this.stack.length - 1 || amount <= 0)
			return null;
		int realAmount = Math.min(this.stack[slot].stackSize, amount);
		ItemStack itemStack = this.stack[slot].copy();
		this.stack[slot].stackSize -= realAmount;
		itemStack.stackSize = realAmount;
		return itemStack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// no items should be dropped, they should remain in the tileEntity
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack items) {
		this.stack[slot] = items;
	}

	@Override
	public String getInventoryName() {
		return StatCollector.translateToLocal("tile." + Names.Box + ".name");
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
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
		// not required
	}

	@Override
	public void closeInventory() {
		// not required
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack items) {
		return this.stack[slot] == null || this.stack[slot].getItem().equals(items.getItem());
	}

	@Override
	public void tick() {
		// not required
	}

	@Override
	public void writeNBT(NBTTagCompound tag) {
		tag.setInteger("Slots", this.stack.length);
		NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < this.stack.length; i++) {
			if (this.stack[i] != null) {
				NBTTagCompound compoundTag = new NBTTagCompound();
				this.stack[i].writeToNBT(compoundTag);
				tagList.appendTag(compoundTag);
			} else { // write a empty stack incase the slot is empty
				ItemStack st = new ItemStack(Blocks.dirt, 0);
				NBTTagCompound compoundTag = new NBTTagCompound();
				st.writeToNBT(compoundTag);
				tagList.appendTag(compoundTag);
			}
		}
		tag.setTag("Items", tagList);
	}

	@Override
	public void readNBT(NBTTagCompound tag) {
		this.stack = new ItemStack[tag.getInteger("Slots")];
		NBTTagList tagList = tag.getTagList("Items", 10);
		for (int i = 0; i < this.stack.length; i++) {
			NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
			this.stack[i] = ItemStack.loadItemStackFromNBT(compoundTag);
			if (this.stack[i].stackSize == 0)
				this.stack[i] = null;
		}
	}

	@Override
	public GuiContainer getGuiClient(InventoryPlayer inventory) {
		return new BoxGuiContainer(inventory, this);
	}

	@Override
	public Container getGuiServer(InventoryPlayer inventory) {
		return new BoxContainer(inventory, this);
	}
}
