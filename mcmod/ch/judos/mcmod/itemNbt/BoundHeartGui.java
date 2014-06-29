package ch.judos.mcmod.itemNbt;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;

/**
 * @author judos
 */
public class BoundHeartGui extends GuiContainer {

	/**
	 * @param inventory
	 * @param stack
	 */
	public BoundHeartGui(InventoryPlayer inventory, ItemStack stack) {
		super(new BoundHeartContainer(inventory, stack));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		String s = StatCollector.translateToLocal("tile." + Names.BoundHeart + ".name");
		int color = 4210752;
		this.fontRendererObj.drawString(s,
			this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6 + 33, color);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8,
			this.ySize - 96 + 2, color);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(new ResourceLocation(
			References.MOD_ID + ":textures/gui/" + Names.BoundHeart + ".png"));
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y + 33, 0, 0, this.xSize, this.ySize);
	}

}
