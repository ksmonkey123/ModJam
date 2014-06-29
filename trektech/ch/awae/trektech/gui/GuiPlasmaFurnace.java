package ch.awae.trektech.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import ch.awae.trektech.TrekTech;
import ch.awae.trektech.entities.TileEntityPlasmaFurnace;
import ch.awae.trektech.gui.container.ContainerPlasmaFurnace;

/**
 * Plasma Source GUI
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class GuiPlasmaFurnace extends GuiContainer {
    
    private TileEntityPlasmaFurnace tileEntity;
    
    /**
     * Basic Constructor
     * 
     * @param inventoryPlayer
     * @param tileEntity
     */
    public GuiPlasmaFurnace(InventoryPlayer inventoryPlayer,
            TileEntityPlasmaFurnace tileEntity) {
        super(new ContainerPlasmaFurnace(inventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int param1, int param2) {
        String s = this.tileEntity.hasCustomInventoryName() ? this.tileEntity
                .getInventoryName() : I18n.format(
                this.tileEntity.getInventoryName(), new Object[0]);
        this.fontRendererObj.drawString(s, this.xSize / 2
                - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(
                StatCollector.translateToLocal("container.inventory"), 8,
                this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2,
            int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation(TrekTech.MODID
                + ":textures/gui/" + "plasmaFurnace" + ".png"));
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
        int i1;
        /*
         * if (this.tileEntity.isBurning()) { i1 =
         * this.tileEntity.getBurnTimeRemainingScaled(12);
         * this.drawTexturedModalRect(x + 81, y + 26 + 12 - i1, 176, 12 - i1,
         * 14, i1 + 2); }
         */
        i1 = this.tileEntity.getPlasmaLevelScaled(4, 44, false);
        this.drawTexturedModalRect(x + 142, y + 65 - i1, 176, 59 - i1, 8, i1);
        i1 = this.tileEntity.getPlasmaLevelScaled(4, 44, true);
        this.drawTexturedModalRect(x + 26, y + 65 - i1, 184, 59 - i1, 8, i1);
    }
}
