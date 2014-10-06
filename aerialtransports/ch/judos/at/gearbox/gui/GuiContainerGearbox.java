package ch.judos.at.gearbox.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import ch.judos.at.ATMain;
import ch.judos.at.gearbox.TEStationGearbox;
import ch.judos.at.gearbox.TEStationGearbox.SendMode;
import ch.judos.at.lib.ATNames;
import ch.modjam.generic.gui.GenericGuiTEContainer;
import ch.modjam.generic.tileEntity.TileEntityChangeListener;
import cpw.mods.fml.client.config.GuiCheckBox;

public class GuiContainerGearbox extends GenericGuiTEContainer implements TileEntityChangeListener {

	private TEStationGearbox	teGearBox;
	private GuiCheckBox			emitRedstone;

	public GuiContainerGearbox(InventoryPlayer inventory, TEStationGearbox te) {
		super(new ContainerGearbox(inventory, te), te, inventory);
		this.teGearBox = te;

		this.xSize = 250;

		this.teGearBox.addListener(this);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		switch (button.id) {
			case 0:
				this.teGearBox.requestSetSendMode(TEStationGearbox.SendMode.Periodically);
				break;
			case 1:
				this.teGearBox.requestSetSendMode(TEStationGearbox.SendMode.GondolaFilled);
				break;
			case 2:
				this.teGearBox.requestSetSendMode(TEStationGearbox.SendMode.OnRedstone);
				break;
			case 3:
				this.teGearBox.requestSetReceiveRedstoneSignal(this.emitRedstone.isChecked());
				break;
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.clear();

		String text = StatCollector.translateToLocal("at.always");
		GuiButton b = new GuiButton(0, this.guiLeft + 10, this.guiTop + 33, 60, 20, text);
		if (this.teGearBox.sendMode == SendMode.Periodically)
			b.packedFGColour = 255 << 8;
		this.buttonList.add(b);
		text = StatCollector.translateToLocal("at.whenfull");
		b = new GuiButton(1, this.guiLeft + 75, this.guiTop + 33, 60, 20, text);
		if (this.teGearBox.sendMode == SendMode.GondolaFilled)
			b.packedFGColour = 255 << 8;
		this.buttonList.add(b);
		text = StatCollector.translateToLocal("at.onredstone");
		b = new GuiButton(2, this.guiLeft + 140, this.guiTop + 33, 80, 20, text);
		if (this.teGearBox.sendMode == SendMode.OnRedstone)
			b.packedFGColour = 255 << 8;
		this.buttonList.add(b);

		text = StatCollector.translateToLocal("at.emitredstone");
		this.emitRedstone = new GuiCheckBox(3, this.guiLeft + 10, this.guiTop + 73, text, false);
		this.emitRedstone.setIsChecked(this.teGearBox.receiveEmitsRedstone);
		this.buttonList.add(this.emitRedstone);

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		this.drawText("tile." + ATNames.station_gearbox + ".name", this.xSize / 2, 6, true);

		this.drawText("at.sendmode", ":", 6, 20, false);
		this.drawText("at.recmode", ":", 6, 60, false);
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

	@Override
	public void onNetworkCommand(String command, Object data) {
		// unused
	}

	@Override
	public void onDataPacket(NBTTagCompound nbtCompound) {
		this.initGui();
	}

}
