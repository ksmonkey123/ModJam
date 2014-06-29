package ch.judos.mcmod.itemNbt;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ch.modjam.generic.gui.GenericContainer;
import ch.modjam.generic.inventory.GenericInventory;
import ch.modjam.generic.inventory.InventorySlotChangedListener;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author judos
 * 
 */
public class BoundHeartContainer extends GenericContainer implements InventorySlotChangedListener {

	/**
	 * the item stack of the heart
	 */
	private ItemStack			stackBefore;
	private GenericInventory	heart;
	private int					heartSlot;
	private InventoryPlayer		playerInventory;

	/**
	 * @param inventory
	 * @param stack
	 * @param slot
	 */
	public BoundHeartContainer(InventoryPlayer inventory, ItemStack stack, int slot) {
		super(inventory);
		this.playerInventory = inventory;
		this.heartSlot = slot;
		this.stackBefore = stack;
		this.heart = new GenericInventory(5, "boundheart_inventory");
		this.heart.readNBT(stack.stackTagCompound);
		this.heart.resizeInventory(5);
		this.heart.addListener((InventorySlotChangedListener) this);
		init();
	}

	@Override
	protected boolean isPlayerSlotAFakeSlot(int slot) {
		return slot == this.heartSlot;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		ItemStack newHeart = this.stackBefore.copy();
		this.heart.writeNBT(newHeart.stackTagCompound);

		ItemStack[] inventory = player.inventory.mainInventory;
		inventory[this.heartSlot].setTagCompound(newHeart.stackTagCompound);

	}

	private void init() {
		unbindPlayerInventory();
		bindPlayerInventory(this.playerInventory);
		for (int i = 0; i < this.heart.getSizeInventory(); i++)
			addSlotToContainer(new Slot(heart, i, 44 + 18 * i, 53));
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	@Override
	public int getSizeInventory() {
		return this.heart.getSizeInventory();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		System.out.println("update");
	}

	@Override
	public void slotChanged(int slot, ItemStack items) {
		NBTTagCompound tag = new NBTTagCompound();
		if (items != null)
			items.writeToNBT(tag);
		this.playerInventory.mainInventory[this.heartSlot].stackTagCompound.setTag("Slot" + slot,
			tag);
	}

}
