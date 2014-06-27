package ch.judos.mcmod.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;

/**
 * @author j
 */
public class CustomBoxGuiContainer extends GuiContainer {

	private CustomBoxTE			te;
	protected InventoryPlayer	inventory;

	/**
	 * @param inventory
	 * @param te
	 */
	public CustomBoxGuiContainer(InventoryPlayer inventory, CustomBoxTE te) {
		super(new CustomBoxContainer(inventory, te));
		this.te = te;
		this.inventory = inventory;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, guiLeft + 140, guiTop + 30, 30, 20, "+"));
		this.buttonList.add(new GuiButton(1, guiLeft + 140, guiTop + 50, 30, 20, "-"));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
			case 0:
				this.te.tryIncreaseSize();
				break;
			case 1:
				this.te.tryDecreaseSize();
				break;
			default:
				break;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		String s = StatCollector.translateToLocal("tile." + Names.CustomBox + ".name");
		int color = 4210752;
		this.fontRendererObj.drawString(s,
			this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, color);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8,
			this.ySize - 96 + 2, color);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(new ResourceLocation(
			References.MOD_ID + ":textures/gui/" + Names.CustomBox + ".png"));
		this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
		for (int i = 1; i < this.te.stack.length; i++)
			this.drawTexturedModalRect(guiLeft + 25 + 18 * i, guiTop + 41, 176, 0, 18, 18);
	}

}
