package ch.awae.trektech.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import ch.awae.trektech.entities.TileEntityPlasmaFurnace;
import ch.modjam.generic.tileEntity.GenericContainer;

@SuppressWarnings("javadoc")
public class ContainerPlasmaFurnace extends GenericContainer {
    
    private TileEntityPlasmaFurnace tileFurnace;
    
    public ContainerPlasmaFurnace(InventoryPlayer par1InventoryPlayer,
            TileEntityPlasmaFurnace te) {
        super(par1InventoryPlayer);
        this.tileFurnace = te;
        this.addSlotToContainer(new Slot(te, 0, 44, 42));
        this.addSlotToContainer(new Slot(te, 1, 116, 42));
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.tileFurnace.isUseableByPlayer(par1EntityPlayer);
    }
    
    @Override
    public int getSizeInventory() {
        return this.tileFurnace.getSizeInventory();
    }
    
}