package ch.phyranja.EssenceCrops.guis;


import ch.modjam.generic.gui.GenericContainer;
import ch.modjam.generic.inventory.slot.OutputSlot;
import ch.phyranja.EssenceCrops.entities.TileEntityExtractor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;


public class ContainerExtractor extends GenericContainer {
    
    private TileEntityExtractor tileEntity;
    
    
    public ContainerExtractor(InventoryPlayer inventory, TileEntityExtractor tileEntity) {
    	super(inventory);
        this.tileEntity = tileEntity;
        addSlotToContainer(new Slot(this.tileEntity, 0, 43, 38));
        addSlotToContainer(new OutputSlot(this.tileEntity, 1, 125, 38));
        bindPlayerInventory(inventory);
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.tileEntity.isUseableByPlayer(player);
    }

	@Override
	public int getSizeInventory() {
		
		return 2;
	}
    
}
