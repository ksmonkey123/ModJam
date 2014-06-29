package ch.awae.trektech.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;
import ch.awae.trektech.EnumPlasmaTypes;
import ch.awae.trektech.TrekTech;
import ch.modjam.generic.ListedStuff;
import ch.modjam.generic.blocks.BlockGenericDualStateDirected;

/**
 * Plasma Furnace
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class TileEntityPlasmaFurnace extends ATileEntityPlasmaSystem implements
        ISidedInventory {
    private static final int   PLASMA_PER_BAR           = 1000;
    
    private static final int   BASE_PLASMA_PER_ITEM     = 500;
    private static final int   BASE_BURN_TIME           = 200;
    private static final float BASE_PLASMA_REGAIN       = 0.5f;
    private static final float BASE_ORE_DOUBLING_CHANCE = 1.0f;
    
    private ItemStack[]        stacks                   = new ItemStack[2];
    private int                currentL                 = 0;
    private int                currentN                 = 0;
    private int                remainingBurnTime        = 0;
    
    // -- ISidedInventory --
    
    @Override
    public int getSizeInventory() {
        return this.stacks.length;
    }
    
    @Override
    public ItemStack getStackInSlot(int var1) {
        return this.stacks[var1];
    }
    
    @Override
    public ItemStack decrStackSize(int slot, int amt) {
        if (slot < 0 && slot > this.stacks.length)
            return null;
        int trueAmount = amt;
        if (trueAmount > this.stacks[slot].stackSize)
            trueAmount = this.stacks[slot].stackSize;
        ItemStack returnStack = this.stacks[slot].copy();
        returnStack.stackSize = trueAmount;
        this.stacks[slot].stackSize -= trueAmount;
        if (this.stacks[slot].stackSize == 0)
            this.stacks[slot] = null;
        return returnStack;
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return null;
    }
    
    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (slot < 0 && slot > this.stacks.length)
            return;
        this.stacks[slot] = stack;
    }
    
    @Override
    public String getInventoryName() {
        return "container.plasmaFurnace";
    }
    
    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer var1) {
        return true;
    }
    
    @Override
    public void openInventory() {
        // not required
    }
    
    @Override
    public void closeInventory() {
        // not required
    }
    
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return slot == 0;
    }
    
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        switch (side) {
            case 0:
                return new int[] { 1 };
            case 1:
                return new int[] { 0 };
            default:
                return new int[] {};
        }
    }
    
    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        return side == 1 && slot == 0;
    }
    
    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return side == 0 && slot == 1;
    }
    
    // -- GenericTileEntity --
    
    @Override
    public void writeNBT(NBTTagCompound tag) {
        for (int i = 0; i < this.stacks.length; i++) {
            NBTTagCompound internal = new NBTTagCompound();
            if (this.stacks[i] != null)
                this.stacks[i].writeToNBT(internal);
            tag.setTag("Slot" + i, internal);
        }
        tag.setInteger("plasmaN", this.currentN);
        tag.setInteger("plasmaL", this.currentL);
        tag.setInteger("remaining", this.remainingBurnTime);
    }
    
    @Override
    public void readNBT(NBTTagCompound tag) {
        for (int i = 0; i < this.stacks.length; i++) {
            NBTTagCompound internal = (NBTTagCompound) tag.getTag("Slot" + i);
            this.stacks[i] = ItemStack.loadItemStackFromNBT(internal);
        }
        this.currentN = tag.getInteger("plasmaN");
        this.currentL = tag.getInteger("plasmaL");
        this.remainingBurnTime = tag.getInteger("remaining");
    }
    
    // -- Plasma System --
    
    @Override
    public float getParticlesPerBar(EnumPlasmaTypes plasma,
            ForgeDirection direction) {
        switch (plasma) {
            case NEUTRAL:
            case LOW:
                return PLASMA_PER_BAR;
            default:
                return 0;
        }
    }
    
    @Override
    public int getParticleCount(EnumPlasmaTypes plasma, ForgeDirection direction) {
        switch (plasma) {
            case NEUTRAL:
                return this.currentN;
            case LOW:
                return this.currentL;
            default:
                return 0;
        }
    }
    
    @Override
    public int applyParticleFlow(EnumPlasmaTypes plasma,
            ForgeDirection direction, int particleCount) {
        switch (plasma) {
            case NEUTRAL:
                this.currentN += particleCount;
                return particleCount;
            case LOW:
                this.currentL += particleCount;
                return particleCount;
            default:
                return 0;
        }
    }
    
    @Override
    public boolean connectsToPlasmaConnection(EnumPlasmaTypes plasma,
            ForgeDirection direction) {
        switch (plasma) {
            case NEUTRAL:
            case LOW:
                return true;
            default:
                return false;
        }
    }
    
    @Override
    public boolean setParticleCount(EnumPlasmaTypes plasma,
            ForgeDirection direction, int count) {
        switch (plasma) {
            case NEUTRAL:
                this.currentN = count;
                return true;
            case LOW:
                this.currentL = count;
                return true;
            default:
                return false;
        }
    }
    
    @Override
    public boolean onWrench(EntityPlayer player) {
        if (!player.isSneaking()) {
            if (this.worldObj.isRemote) {
                String pressure = (this.currentN / (float) PLASMA_PER_BAR) + "";
                if (pressure.length() > 4)
                    pressure = pressure.substring(0, 4);
                player.addChatMessage(new ChatComponentText(
                        "Current Plasma Pressure (N): " + pressure + " bar"));
                pressure = (this.currentL / (float) PLASMA_PER_BAR) + "";
                if (pressure.length() > 4)
                    pressure = pressure.substring(0, 4);
                player.addChatMessage(new ChatComponentText(
                        "Current Plasma Pressure (L): " + pressure + " bar"));
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void customTick() {
        if (this.currentL >= this.getPlasmaConsumptionPerItem()) {
            this.remainingBurnTime--;
            if (this.remainingBurnTime == 0) {
                this.smelt();
            } else if (this.remainingBurnTime < 0)
                if (this.canSmelt()) {
                    this.remainingBurnTime = this.getSmeltTimePerItem();
                    this.setActive(true);
                } else {
                    this.remainingBurnTime = 0;
                    this.setActive(false);
                }
        } else {
            this.setActive(false);
        }
        this.markDirty();
    }
    
    private void setActive(boolean newState) {
        BlockGenericDualStateDirected.setActive(newState, this.worldObj,
                this.xCoord, this.yCoord, this.zCoord);
    }
    
    private boolean canSmelt() {
        if (this.stacks[0] == null) {
            return false;
        }
        ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(
                this.stacks[0]);
        if (itemstack == null)
            return false;
        if (this.stacks[1] == null)
            return true;
        if (!this.stacks[1].isItemEqual(itemstack))
            return false;
        int result = this.stacks[1].stackSize + itemstack.stackSize;
        return result <= getInventoryStackLimit()
                && result <= this.stacks[1].getMaxStackSize();
    }
    
    private void smelt() {
        if (this.currentL < this.getPlasmaConsumptionPerItem())
            return;
        this.currentL -= this.getPlasmaConsumptionPerItem();
        this.currentN += this.getPlasmaConsumptionPerItem()
                * this.getPlasmaRegain();
        ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(
                this.stacks[0]);
        // IDEA: add scrap item for items without recipe
        if (this.stacks[1] == null || this.stacks[1].stackSize == 0) {
            this.stacks[1] = itemstack.copy();
            this.stacks[1].stackSize = 0;
            this.stacks[1].stackSize += this.getRealOutputCount(this.stacks[0],
                    itemstack, 64 - this.stacks[1].stackSize);
            this.stacks[0].stackSize--;
            if (this.stacks[0].stackSize <= 0) {
                this.stacks[0] = null;
            }
        }
    }
    
    private int getRealOutputCount(ItemStack input, ItemStack output,
            int maxCount) {
        System.out.println(input.getItem().getUnlocalizedName().substring(5));
        if (ListedStuff.getOreNames().contains(
                input.getItem().getUnlocalizedName().substring(5))) {
            float chance = this.getOreDoublingChange();
            int factor = (TrekTech.rand.nextFloat() < chance) ? 2 : 1;
            return Math.min(output.stackSize * factor, maxCount);
        }
        return output.stackSize;
    }
    
    private int getPlasmaConsumptionPerItem() {
        return BASE_PLASMA_PER_ITEM;
        // TODO: calculate plasma consumption
    }
    
    private int getSmeltTimePerItem() {
        return BASE_BURN_TIME;
        // TODO: calculate item smelt time
    }
    
    private float getPlasmaRegain() {
        return BASE_PLASMA_REGAIN;
        // TODO: calculate plasma regain
    }
    
    private float getOreDoublingChange() {
        return BASE_ORE_DOUBLING_CHANCE;
        // TODO: calculate ore doubling chance
    }
}
