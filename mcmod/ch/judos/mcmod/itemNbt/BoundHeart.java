package ch.judos.mcmod.itemNbt;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;
import ch.modjam.generic.PlayerUtils;
import ch.modjam.generic.gui.GenericGuiHandler;
import ch.modjam.generic.gui.IItemHasGui;
import ch.modjam.generic.inventory.GenericNBTInventory;
import ch.modjam.generic.inventory.NBTProvider;

/**
 * @author judos
 * 
 */
public class BoundHeart extends Item implements IItemHasGui {

	/**
	 * transfer speed of the hopper functionality ( items per 10s )
	 */
	public static final String	NBT_TRANSFER_SPEED	= "ItemTransferSpeed";
	/**
	 * the number of available slots in this heart (capacity of inventory)
	 */
	public static final String	NBT_SLOTS			= "Slots";

	/**
	 * creating the item and setting up configs
	 */
	public BoundHeart() {
		this.setCreativeTab(MCMod.modTab);
		this.setUnlocalizedName(Names.BoundHeart);
		this.setTextureName(References.MOD_ID + ":" + Names.BoundHeart);
		this.setMaxStackSize(1);
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		if (itemStack.stackTagCompound == null) {
			itemStack.stackTagCompound = new NBTTagCompound();
			itemStack.stackTagCompound.setString("owner", player.getCommandSenderName());
			itemStack.stackTagCompound.setInteger(NBT_SLOTS, 0);
			itemStack.stackTagCompound.setInteger("counter", 0);
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		if (item.stackTagCompound.getInteger(NBT_SLOTS) > 0)
			GenericGuiHandler.openItemGUI(player, world, item);
		return item;
	}

	/**
	 * adds another information line on the item
	 */
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		if (itemStack.stackTagCompound != null) {
			String owner = itemStack.stackTagCompound.getString("owner");
			if (PlayerUtils.isPlayerOnlineInWorld(owner))
				owner = EnumChatFormatting.GREEN + "Owner: " + owner;
			else
				owner = EnumChatFormatting.RED + "Owner: " + owner + " (not online)";

			list.add(owner);
			if (itemStack.stackTagCompound.getInteger(NBT_SLOTS) > 0)
				list.add("Slots installed: " + EnumChatFormatting.GREEN + itemStack.stackTagCompound
					.getInteger(NBT_SLOTS));
			else {
				list.add(EnumChatFormatting.GRAY + "Add 4xWool, a chest and a gold");
				list.add(EnumChatFormatting.GRAY + "ingot to create inventory.");
			}

			if (itemStack.stackTagCompound.hasKey(BoundHeart.NBT_TRANSFER_SPEED)) {
				int speed = itemStack.stackTagCompound.getInteger(BoundHeart.NBT_TRANSFER_SPEED);
				list.add("Item transfer speed: " + EnumChatFormatting.GREEN + ((float) speed / 10) + "i /s");
			} else if (itemStack.stackTagCompound.getInteger(NBT_SLOTS) == 5) {
				list.add(EnumChatFormatting.GRAY + "Add some redstone and a hopper.");
			}

			// list.add("counter: " + itemStack.stackTagCompound.getInteger("counter"));

		}
	}

	@Override
	public void onUpdate(final ItemStack item, World world, Entity entity, int slot,
			boolean heldInHand) {
		if (world.isRemote)
			return;

		if (entity instanceof EntityPlayer) {
			if (item.stackTagCompound == null) {
				if (entity instanceof EntityPlayer) {
					onCreated(item, world, (EntityPlayer) entity);
				}
			} else {
				EntityPlayer current = (EntityPlayer) entity;
				EntityPlayer heartOrigin = PlayerUtils
					.getPlayerByNameOnServer(item.stackTagCompound.getString("owner"));
				if (heartOrigin == null)
					return;

				if (item.stackTagCompound.hasKey(NBT_TRANSFER_SPEED))
					transferItems(item, heartOrigin);

				if (current.getHealth() <= 10 && current.getHealth() < heartOrigin.getHealth() - 3) {
					heartOrigin.heal(-0.5f);
					current.heal(0.5f);
				}
			}
		}
	}

	private static void transferItems(final ItemStack item, EntityPlayer origin) {
		int counter = item.stackTagCompound.getInteger("counter") + 1;
		int speed = item.stackTagCompound.getInteger(NBT_TRANSFER_SPEED);
		if (speed == 0)
			return;
		if (counter >= 200 / speed) {
			counter = 0;

			NBTProvider pr = new NBTProvider() {
				@Override
				public NBTTagCompound getNBT() {
					return item.stackTagCompound;
				}
			};
			GenericNBTInventory heart = new GenericNBTInventory(pr, "boundheart_inventory");
			ItemStack push = heart.getAndRemoveFirstItem();
			if (push != null) {
				if (!origin.inventory.addItemStackToInventory(push))
					heart.addItemStackToInventory(push);
			}
		}
		item.stackTagCompound.setInteger("counter", counter);
	}

	@Override
	public GuiContainer getGuiClient(InventoryPlayer inventory, ItemStack stack, int slot) {
		return new BoundHeartGui(inventory, stack, slot);
	}

	@Override
	public Container getGuiServer(InventoryPlayer inventory, ItemStack stack, int slot) {
		return new BoundHeartContainer(inventory, stack, slot);
	}

}
