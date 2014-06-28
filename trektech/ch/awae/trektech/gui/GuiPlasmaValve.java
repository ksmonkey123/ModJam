package ch.awae.trektech.gui;

import java.awt.Color;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import ch.awae.trektech.TrekTech;
import ch.awae.trektech.entities.TileEntityPlasmaValve;
import ch.awae.trektech.gui.container.ContainerPlasmaValve;
import ch.modjam.generic.tileEntity.GenericGuiTEContainer;

/**
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class GuiPlasmaValve extends GenericGuiTEContainer {
    
    TileEntityPlasmaValve tileEntity;
    
    /**
     * @param inventory
     * @param tileEntity
     */
    public GuiPlasmaValve(InventoryPlayer inventory,
            TileEntityPlasmaValve tileEntity) {
        super(new ContainerPlasmaValve(inventory), tileEntity, inventory);
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
        String s = StatCollector.translateToLocal(this.tileEntity
                .getWorldObj()
                .getBlock(this.tileEntity.xCoord, this.tileEntity.yCoord,
                        this.tileEntity.zCoord).getUnlocalizedName()
                + ".name");
        int color = Color.darkGray.darker().hashCode();
        this.fontRendererObj.drawString(s, this.xSize / 2
                - this.fontRendererObj.getStringWidth(s) / 2, 6, color);
        this.fontRendererObj.drawString(
                StatCollector.translateToLocal("container.inventory"), 8,
                this.ySize - 96 + 2, color);
        this.fontRendererObj.drawString(
                "Threshold: " + this.tileEntity.getPressureThreshold(), 50, 43,
                color);
        this.fontRendererObj.drawString(
                "Pressure : " + this.tileEntity.getPressure(), 50, 52, color);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2,
            int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation(TrekTech.MODID
                + ":textures/gui/generic.png"));
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
