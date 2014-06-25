package ch.awae.trektech.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import ch.awae.trektech.entities.TileEntityPlasmaValve;
import ch.awae.trektech.gui.container.ContainerPlasmaValve;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;

/**
 * @author awae
 * 
 *         TODO: cleanup, but really
 */
public class GuiPlasmaValve extends GuiContainer {

	TileEntityPlasmaValve tileEntity;

	/**
	 * @param inventory
	 * @param tileEntity
	 */
	public GuiPlasmaValve(InventoryPlayer inventory,
			TileEntityPlasmaValve tileEntity) {
		super(new ContainerPlasmaValve(inventory));
		this.tileEntity = tileEntity;
	}

	@Override
	public void initGui() {
		super.initGui();
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.buttonList.add(new GuiButton(0, x + 140, y + 30, 30, 20, "+"));
		this.buttonList.add(new GuiButton(1, x + 140, y + 50, 30, 20, "-"));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		String s = StatCollector.translateToLocal("tile.valve0.name");
		int color = 4210752;
		this.fontRendererObj.drawString(s, this.xSize / 2
				- this.fontRendererObj.getStringWidth(s) / 2, 6, color);
		this.fontRendererObj.drawString(
				StatCollector.translateToLocal("container.inventory"), 8,
				this.ySize - 96 + 2, color);
		this.fontRendererObj.drawString(this.tileEntity.getPressureThreshold()
				+ "", (this.width - this.xSize) / 2 + 120,
				(this.height - this.ySize) / 2 + 40, color);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2,
			int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(new ResourceLocation(References.MOD_ID
				+ ":textures/gui/" + Names.Box + ".png"));
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void actionPerformed(GuiButton button) {

		switch (button.id) {
		case 0:
			this.tileEntity.setPressureThreshold(this.tileEntity
					.getPressureThreshold() + 1);
			break;
		case 1:
			this.tileEntity.setPressureThreshold(this.tileEntity
					.getPressureThreshold() - 1);
			break;
		default:
			break;
		}
	}

}
