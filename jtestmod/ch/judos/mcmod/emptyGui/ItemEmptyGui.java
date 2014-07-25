package ch.judos.mcmod.emptyGui;

import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ch.judos.mcmod.MCMod;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;
import ch.modjam.generic.gui.GenericGuiHandler;
import ch.modjam.generic.gui.IItemHasGui;

/**
 * @author j
 *
 */
public class ItemEmptyGui extends Item implements IItemHasGui {
	/**
	 * 
	 */
	public ItemEmptyGui() {
		this.setCreativeTab(MCMod.modTab);
		this.setUnlocalizedName(Names.EmptyGui);
		this.setTextureName(References.MOD_ID + ":" + Names.EmptyGui);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		GenericGuiHandler.openItemGUI(player, world, item);
		return item;
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		list.add("Useful for debugging and");
		list.add("development of custom renderer.");
		list.add("On right-click opens an empty gui");
		list.add("this prevents the menu from opening when mc");
		list.add("looses focus");
	}

	@Override
	public GuiContainer getGuiClient(InventoryPlayer inventory, ItemStack stack, int slot) {
		return new EmptyGui(inventory, stack, slot);
	}

	@Override
	public Container getGuiServer(InventoryPlayer inventory, ItemStack stack, int slot) {
		return new EmptyContainer(inventory, stack, slot);
	}
}
