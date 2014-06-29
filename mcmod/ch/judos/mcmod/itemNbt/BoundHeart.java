package ch.judos.mcmod.itemNbt;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;
import ch.modjam.generic.gui.GenericGuiHandler;
import ch.modjam.generic.inventory.GenericNBTInventory;
import ch.modjam.generic.inventory.IItemHasGui;
import ch.modjam.generic.inventory.NBTProvider;

/**
 * @author judos
 * 
 */
public class BoundHeart extends Item implements IItemHasGui {

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
			itemStack.stackTagCompound.setInteger("Slots", 5);
			itemStack.stackTagCompound.setInteger("counter", 0);
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		GenericGuiHandler.openItemGUI(player, world, item);
		return item;
	}

	/**
	 * adds another information line on the item
	 */
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		if (itemStack.stackTagCompound != null) {
			String owner = itemStack.stackTagCompound.getString("owner");
			list.add(EnumChatFormatting.GREEN + "Owner: " + owner);
			if (itemStack.stackTagCompound.hasKey("upgrade"))
				list.add(EnumChatFormatting.GREEN + "Upgrades installed: " + itemStack.stackTagCompound
					.getInteger("upgrade"));

			List entities = Minecraft.getMinecraft().theWorld.playerEntities;
			boolean exists = false;
			for (Object x : entities) {
				if (x instanceof EntityPlayer) {
					EntityPlayer xPlayer = (EntityPlayer) x;
					if (xPlayer.getCommandSenderName().equals(owner)) {
						exists = true;
						break;
					}
				}
			}
			if (!exists)
				list.add(EnumChatFormatting.RED + "Player not found or not online.");
		}
	}

	@Override
	public void onUpdate(final ItemStack item, World world, Entity entity, int slot,
			boolean heldInHand) {
		if (world.isRemote)
			return;

		if (entity instanceof EntityLivingBase) {
			if (item.stackTagCompound == null) {
				if (entity instanceof EntityPlayer) {
					onCreated(item, world, (EntityPlayer) entity);
				}
			} else {
				int counter = item.stackTagCompound.getInteger("counter") + 1;

				String heartOriginName = item.stackTagCompound.getString("owner");
				MinecraftServer s = MinecraftServer.getServer();
				EntityPlayer heartOrigin = s.getConfigurationManager().getPlayerForUsername(
					heartOriginName);
				if (heartOrigin == null)
					return;

				EntityLivingBase current = (EntityLivingBase) entity;
				// String currentName = current.getCommandSenderName();
				// String t = Thread.currentThread().getName();

				if (current.getHealth() <= 10 && current.getHealth() < heartOrigin.getHealth() - 3) {
					heartOrigin.heal(-0.5f);
					current.heal(0.5f);
				}

				if (current instanceof EntityPlayer && counter >= 20) {
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
						if (!heartOrigin.inventory.addItemStackToInventory(push)) {
							heart.addItemStackToInventory(push);
						}
					}
				}

				item.stackTagCompound.setInteger("counter", counter);
			}
		}
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
