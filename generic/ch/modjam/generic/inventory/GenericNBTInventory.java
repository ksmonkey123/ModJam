package ch.modjam.generic.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

/**
 * @author judos
 */
public class GenericNBTInventory extends AbstractInventory {

	/**
	 * nbt provider where inventory is stored in
	 */
	private NBTProvider	provider;

	/**
	 * @param provider
	 * @param tileName
	 */
	public GenericNBTInventory(NBTProvider provider, String tileName) {
		super(tileName);
		this.provider = provider;
	}

	/**
	 * @return the current nbt
	 */
	protected NBTTagCompound nbt() {
		return this.provider.getNBT();
	}

	@Override
	public int getSizeInventory() {
		if (this.nbt() == null)
			return 0;
		return this.nbt().getInteger("Slots");
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (this.nbt() == null)
			return null;
		NBTTagCompound tag = (NBTTagCompound) this.nbt().getTag("Slot" + slot);
		if (tag == null)
			return null;
		return ItemStack.loadItemStackFromNBT(tag);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (this.nbt() == null)
			return;
		NBTTagCompound tag = new NBTTagCompound();
		if (stack != null)
			stack.writeToNBT(tag);
		this.nbt().setTag("Slot" + slot, tag);
	}

	@Override
	public String getInventoryName() {
		return StatCollector.translateToLocal("tile." + this.tileName + ".name");
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public void markDirty() {
		if (!Minecraft.getMinecraft().theWorld.isRemote)
			new Exception("inventory marked as dirty: ").printStackTrace();

		// not implemented
		// System.out.println("inventory marked as dirty");
	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		return true;
	}

}
