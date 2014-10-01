package ch.judos.at.gearbox.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import ch.judos.at.ATMain;
import ch.judos.at.gearbox.TEStationGearbox;
import ch.judos.at.lib.ATNames;
import ch.modjam.generic.gui.GenericGuiTEContainer;

public class GuiContainerGearbox extends GenericGuiTEContainer {

	private TEStationGearbox	teGearBox;

	public GuiContainerGearbox(InventoryPlayer inventory, TEStationGearbox te) {
		super(new ContainerGearbox(inventory, te), te, inventory);
		this.teGearBox = te;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		String s = StatCollector.translateToLocal("tile." + ATNames.station_gearbox + ".name");
		int color = 4210752;
		this.fontRendererObj.drawString(s,
			this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, color);

		// String items = StatCollector.translateToLocal("at.items");
		// this.fontRendererObj.drawString(items, 10, 48, color);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(new ResourceLocation(
			ATMain.MOD_ID + ":textures/gui/" + ATNames.gearbox + ".png"));
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}

}
