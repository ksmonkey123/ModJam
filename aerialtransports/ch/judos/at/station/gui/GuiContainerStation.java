package ch.judos.at.station.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import ch.judos.at.ATMain;
import ch.judos.at.lib.ATNames;
import ch.judos.at.station.TEStation;
import ch.modjam.generic.gui.GenericGuiTEContainer;

/**
 * @author judos
 */
public class GuiContainerStation extends GenericGuiTEContainer {

	private TEStation	teStation;

	/**
	 * @param inventory
	 * @param te
	 */
	public GuiContainerStation(InventoryPlayer inventory, TEStation te) {
		super(new ContainerStation(inventory, te), te, inventory);
		this.teStation = te;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.clear();
		String text = StatCollector.translateToLocal("at.connect");
		if (this.teStation.isConnectedToSomething()) {
			text = StatCollector.translateToLocal("at.reconnect");

			String t2;
			if (this.teStation.isSender())
				t2 = StatCollector.translateToLocal("at.sender");
			else
				t2 = StatCollector.translateToLocal("at.receiver");
			this.buttonList.add(new GuiButton(1, this.guiLeft + 100, this.guiTop + 43, 60, 20, t2));

		}
		this.buttonList.add(new GuiButton(0, this.guiLeft + 100, this.guiTop + 23, 60, 20, text));
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
			case 0:
				this.inventory.player.closeScreen();
				this.teStation.clientRequestBindRopeConnection(this.inventory.player);
				break;
			case 1:
				this.teStation.clientRequestSenderChange();
				this.initGui();
				break;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		String s = StatCollector.translateToLocal("tile." + ATNames.station + ".name");
		int color = 4210752;
		this.fontRendererObj.drawString(s,
			this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, color);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8,
			this.ySize - 96 + 2, color);

		String gondolas = StatCollector.translateToLocal("at.gondolas");
		this.fontRendererObj.drawString(gondolas, 10, 30, color);
		String items = StatCollector.translateToLocal("at.items");
		this.fontRendererObj.drawString(items, 10, 48, color);

		// XXX: is this needed?
		// initGui();

		// if (this.teStation.isConnectedToSomething()) {
		// String reconnect = StatCollector.translateToLocal("at.reconnect");
		// ((GuiButton) this.buttonList.get(0)).displayString = reconnect;
		// }
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(new ResourceLocation(
			ATMain.MOD_ID + ":textures/gui/" + ATNames.station + ".png"));
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}

}
