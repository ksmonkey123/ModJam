package ch.judos.mcmod.itemNbt;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
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
		// this.setMaxStackSize(1); //XXX: i guess this is needed
	}

	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
		itemStack.stackTagCompound = new NBTTagCompound();
		itemStack.stackTagCompound.setString("owner", player.getCommandSenderName());
	}

	/**
	 * adds another information line on the item
	 */
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		if (itemStack.stackTagCompound != null) {
			String owner = itemStack.stackTagCompound.getString("owner");
			list.add(EnumChatFormatting.GREEN + "owner: " + owner);
		}
	}

	@Override
	public void onUpdate(ItemStack item, World world, Entity entity, int slot, boolean heldInHand) {
		if (entity instanceof EntityLivingBase) {
			if (item.stackTagCompound != null) {
				String heartOriginName = item.stackTagCompound.getString("owner");
				MinecraftServer s = MinecraftServer.getServer();
				EntityPlayer heartOrigin = s.getConfigurationManager().getPlayerForUsername(
					heartOriginName);

				EntityLivingBase current = (EntityLivingBase) entity;
				String currentName = current.getCommandSenderName();

				String t = Thread.currentThread().getName();

				System.out.println(t + ": origin:" + heartOriginName + "(" + heartOrigin
					.getHealth() + ") current:" + currentName + "(" + current.getHealth() + ")");

				if (current.getHealth() < heartOrigin.getHealth() - 1) {
					heartOrigin.attackEntityFrom(DamageSource.magic, 0.5f);
					current.attackEntityFrom(DamageSource.magic, -0.5f);
				}
			}
		}

	}
}
