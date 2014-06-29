package ch.awae.trektech.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import ch.awae.trektech.UpgradeSlot;
import ch.awae.trektech.entities.TileEntityPlasmaFurnace;
import ch.modjam.generic.gui.GenericContainer;
import ch.modjam.generic.inventory.slot.OutputSlot;

@SuppressWarnings("javadoc")
public class ContainerPlasmaFurnace extends GenericContainer {
    
    private TileEntityPlasmaFurnace tileFurnace;
    
    public ContainerPlasmaFurnace(InventoryPlayer inventory,
            TileEntityPlasmaFurnace te) {
        super(inventory);
        this.tileFurnace = te;
        this.addSlotToContainer(new Slot(te, 0, 44, 42));
        this.addSlotToContainer(new OutputSlot(te, 1, 116, 42));
        this.addSlotToContainer(new UpgradeSlot(te, 2, 152, 8));
        this.addSlotToContainer(new UpgradeSlot(te, 3, 152, 26));
        this.addSlotToContainer(new UpgradeSlot(te, 4, 152, 44));
        this.addSlotToContainer(new UpgradeSlot(te, 5, 152, 62));
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