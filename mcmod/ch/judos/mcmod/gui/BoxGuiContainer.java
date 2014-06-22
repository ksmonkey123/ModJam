package ch.judos.mcmod.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;

public class BoxGuiContainer extends GuiContainer {

	private BoxTE te;

	public BoxGuiContainer(InventoryPlayer inventory, BoxTE te) {
		super(new BoxContainer(inventory, te));
		this.te = te;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		String s = StatCollector.translateToLocal("tile.box.name");
		int color = 4210752;
		this.fontRendererObj.drawString(s,
			this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, color);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8,
			ySize - 96 + 2, color);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(new ResourceLocation(
			References.MOD_ID + ":textures/gui/" + Names.Box + ".png"));
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
