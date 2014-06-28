package ch.judos.mcmod.itemNbt;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;

/**
 * @author judos
 * 
 */
public class BoundHeart extends Item {

	/**
	 * creating the item and setting up configs
	 */
	public BoundHeart() {
		this.setCreativeTab(MCMod.modTab);
		this.setUnlocalizedName(Names.BoundHeart);
		this.setTextureName(References.MOD_ID + ":" + Names.BoundHeart);
		this.setMaxStackSize(2);
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		if (itemStack.stackTagCompound == null) {
			itemStack.stackTagCompound = new NBTTagCompound();
			itemStack.stackTagCompound.setString("owner", player.getCommandSenderName());
		}
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
	public void onUpdate(ItemStack item, World world, Entity entity, int slot, boolean heldInHand) {
		if (world.isRemote)
			return;

		if (entity instanceof EntityLivingBase) {
			if (item.stackTagCompound == null) {
				if (entity instanceof EntityPlayer) {
					onCreated(item, world, (EntityPlayer) entity);
				}
			} else {
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

				if (current instanceof EntityPlayer) {
					EntityPlayer curP = (EntityPlayer) current;
					int syncSlot = 0;

					ItemStack curI = curP.inventory.mainInventory[syncSlot];
					ItemStack remoteI = heartOrigin.inventory.mainInventory[syncSlot];

					remoteI = transfer(curI, remoteI);
					curI = transfer(remoteI, curI);

					curP.inventory.mainInventory[syncSlot] = curI;
					heartOrigin.inventory.mainInventory[syncSlot] = remoteI;

					// XXX: i could do something with the hunger here as well
					// System.out.println(curP.getFoodStats().getFoodLevel());
				}
			}
		}
	}

	private ItemStack transfer(ItemStack a, ItemStack b) {
		if (a == null)
			return b;
		if (b == null) {
			b = a.copy();
			b.stackSize /= 2;
			a.stackSize -= b.stackSize;
		}
		if (a.isItemEqual(b)) {
			int move = (a.stackSize - b.stackSize) / 2;
			b.stackSize += move;
			a.stackSize -= move;
		}
		if (b.stackSize == 0)
			b = null;
		return b;
	}

}
