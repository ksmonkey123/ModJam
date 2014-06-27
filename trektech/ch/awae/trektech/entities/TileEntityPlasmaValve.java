package ch.awae.trektech.entities;

import java.nio.ByteBuffer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;
import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.gui.GuiPlasmaValve;
import ch.awae.trektech.gui.container.ContainerPlasmaValve;
import ch.modjam.generic.tileEntity.IHasGui;

/**
 * This valve limits flow-through pressure to 10bar
 * 
 * TODO: make pressure level customizable
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class TileEntityPlasmaValve extends TileEntityPlasmaPipe implements
        IHasGui {
    
    private float        pressureThreshold;
    private static float MIN_THRESHOLD = 01f;
    private static float MAX_THRESHOLD = 20f;
    
    /**
     * Full-Fledged Constructor
     * 
     * @param plasmaType
     *            the plasma Type for this valve
     * @param pressureThreshold
     *            the pressure to block at
     */
    public TileEntityPlasmaValve(EnumPlasmaTypes plasmaType,
            float pressureThreshold) {
        super(plasmaType, null, 0);
        this.pressureThreshold = 5f;
    }
    
    /**
     * raw constructor. for reconstruction from save-file only
     */
    public TileEntityPlasmaValve() {}
    
    @Override
    public int getMaxOutput(EnumPlasmaTypes plasma, ForgeDirection direction) {
        int particleThreshold = (int) (this.pressureThreshold * PARTICLES_PER_BAR);
        return (this.currentPlasma > particleThreshold) ? particleThreshold
                : this.currentPlasma;
    }
    
    @Override
    public void writeNBT(NBTTagCompound tag) {
        super.writeNBT(tag);
        tag.setFloat("Treshold", this.pressureThreshold);
    }
    
    @Override
    public void readNBT(NBTTagCompound tag) {
        super.readNBT(tag);
        this.pressureThreshold = tag.getFloat("Treshold");
    }
    
    /**
     * @return the pressure threshold
     */
    public float getPressureThreshold() {
        return this.pressureThreshold;
    }
    
    /**
     * set the pressure threshold
     * 
     * @param threshold
     *            the new threshold
     */
    public void setPressureThreshold(float threshold) {
        if (threshold < MIN_THRESHOLD || threshold > MAX_THRESHOLD)
            return;
        this.pressureThreshold = threshold;
        if (this.worldObj.isRemote)
            this.sendNetworkCommand("set",
                    ByteBuffer.allocate(4).putFloat(this.pressureThreshold)
                            .array());
        this.markDirty();
    }
    
    @Override
    public void onNetworkCommand(String command, byte[] data) {
        if (command.equals("set"))
            this.setPressureThreshold(ByteBuffer.wrap(data).getFloat());
    }
    
    @Override
    public boolean onWrench(EntityPlayer player) {
        if (!player.isSneaking()) {
            if (this.worldObj.isRemote)
                player.addChatMessage(new ChatComponentText(
                        "Current Plasma Pressure Threshold: "
                                + this.pressureThreshold + " bar"));
            return true;
        }
        return false;
    }
    
    @Override
    public GuiContainer getGuiClient(InventoryPlayer inventory) {
        return new GuiPlasmaValve(inventory, this);
    }
    
    @Override
    public Container getGuiServer(InventoryPlayer inventory) {
        return new ContainerPlasmaValve(inventory);
    }
    
    /**
     * @return the pressure pressing on the block
     */
    public float getPressure() {
        return this.currentPlasma / PARTICLES_PER_BAR;
    }
    
}
