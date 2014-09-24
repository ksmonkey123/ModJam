package ch.phyranja.EssenceCrops.guis;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import ch.phyranja.EssenceCrops.entities.TileEntityExtractor;
import ch.phyranja.EssenceCrops.lib.References;

public class ExtractorGui extends GuiContainer {

	private TileEntityExtractor	tileEntity;

	/**
	 * Basic Constructor
	 * 
	 * @param inventoryPlayer
	 * @param tileEntity
	 */
	public ExtractorGui(InventoryPlayer inventory, TileEntityExtractor tileEntity) {
		super(new ContainerExtractor(inventory, tileEntity));
		this.tileEntity = tileEntity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
		String s = this.tileEntity.isCustomInventoryName() ? this.tileEntity.getInventoryName() : I18n
			.format(this.tileEntity.getInventoryName(), new Object[0]);
		this.fontRendererObj.drawString(s,
			this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8,
			this.ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(new ResourceLocation(
			References.MOD_ID + ":textures/guis/" + "ExtractorGui" + ".png"));
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		// int i1;
		//
		// i1 =20;
		// this.drawTexturedModalRect(x, y - i1, 176, i1,
		// 14, i1 + 2);
		//
		// i1 = 10;
		// this.drawTexturedModalRect(x + 142, y + 21 + 37 - i1, 176,
		// 15 + 44 - i1, 8, i1);
	}

}
