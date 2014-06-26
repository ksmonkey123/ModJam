package ch.awae.trektech.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;
import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.TrekTech;

/**
 * Tile Entity for the pipe system
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class TileEntityPlasmaPipe extends ATileEntityPlasmaSystem implements
        IPlasmaPipe {
    
    /**
     * Particle amount required to reach 1 bar
     */
    public static final float PARTICLES_PER_BAR = 100f;
    
    protected int             currentPlasma     = 0;
    protected EnumPlasmaTypes plasmaType;
    private String            texture;
    private float             radius;
    
    /**
     * Default constructor for TE construction
     * 
     * @param plasma
     * @param texture
     * @param radius
     */
    public TileEntityPlasmaPipe(EnumPlasmaTypes plasma, String texture,
            float radius) {
        this.plasmaType = plasma;
        this.texture = TrekTech.MODID + ":textures/blocks/" + texture + ".png";
        this.radius = radius;
    }
    
    /**
     * Generic constructor for TE reconstruction from Save-File Only use this
     * constructor when data is restored from NBT afterwards!
     */
    public TileEntityPlasmaPipe() {
        this.plasmaType = EnumPlasmaTypes.NEUTRAL;
        this.radius = 0;
        this.texture = "";
    }
    
    // -- IPlasmaPipe --
    @Override
    public boolean connectsTo(ForgeDirection d) {
        TileEntity t = this.worldObj.getTileEntity(this.xCoord + d.offsetX,
                this.yCoord + d.offsetY, this.zCoord + d.offsetZ);
        if (t == null || !(t instanceof IPlasmaConnection))
            return false;
        IPlasmaConnection te = (IPlasmaConnection) t;
        ForgeDirection opposite = d.getOpposite();
        return te.connectsToPlasmaConnection(this.plasmaType, opposite);
    }
    
    @Override
    public float getPipeRadius() {
        return this.radius;
    }
    
    @Override
    public String getTexture() {
        return this.texture;
    }
    
    // -- IPlasmaConnection --
    @Override
    public float getParticlesPerBar(EnumPlasmaTypes plasma,
            ForgeDirection direction) {
        return plasma == this.plasmaType ? PARTICLES_PER_BAR : 0;
    }
    
    @Override
    public int getParticleCount(EnumPlasmaTypes plasma, ForgeDirection direction) {
        return plasma == this.plasmaType ? this.currentPlasma : 0;
    }
    
    @Override
    public int applyParticleFlow(EnumPlasmaTypes plasma,
            ForgeDirection direction, int particleCount) {
        if (plasma == this.plasmaType) {
            this.currentPlasma += particleCount;
            return particleCount;
        }
        return 0;
    }
    
    @Override
    public boolean connectsToPlasmaConnection(EnumPlasmaTypes plasma,
            ForgeDirection direction) {
        return plasma == this.plasmaType;
    }
    
    @Override
    public boolean setParticleCount(EnumPlasmaTypes plasma,
            ForgeDirection direction, int count) {
        if (plasma == this.plasmaType) {
            this.currentPlasma = count;
            return true;
        }
        return false;
    }
    
    // -- ATileEntityPlasmaSystem --
    @Override
    public void customTick() {
        // NOT NEEDED
    }
    
    @Override
    public void writeNBT(NBTTagCompound tag) {
        tag.setInteger("PlasmaType", this.plasmaType.ordinal());
        tag.setString("Texture", this.texture);
        tag.setFloat("Radius", this.radius);
        tag.setInteger("Plasma", this.currentPlasma);
    }
    
    @Override
    public void readNBT(NBTTagCompound tag) {
        this.plasmaType = EnumPlasmaTypes.values()[tag.getInteger("PlasmaType")];
        this.texture = tag.getString("Texture");
        this.radius = tag.getFloat("Radius");
        this.currentPlasma = tag.getInteger("Plasma");
    }
    
    @Override
    public boolean onWrench(EntityPlayer player) {
        if (this.worldObj.isRemote && !player.isSneaking()) {
            player.addChatMessage(new ChatComponentText("Plasma Type: "
                    + this.plasmaType.toString()));
            float pressure = this.currentPlasma / PARTICLES_PER_BAR;
            player.addChatMessage(new ChatComponentText(
                    "Current Plasma Pressure: " + pressure + " bar"));
            return true;
        }
        return false;
    }
    
    @Override
    public int getMaxAcceptance(EnumPlasmaTypes plasma, ForgeDirection direction) {
        return Integer.MAX_VALUE;
    }
    
}