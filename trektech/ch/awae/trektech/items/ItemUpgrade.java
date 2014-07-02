package ch.awae.trektech.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ch.awae.trektech.EnumUpgrade;
import ch.awae.trektech.TrekTech;

/**
 * This Item is used for TrekTech upgrades
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class ItemUpgrade extends Item {
    
    private EnumUpgrade type;
    
    /**
     * Basic Constructor
     * 
     * @param type
     *            the upgrade type for this upgrade
     */
    public ItemUpgrade(EnumUpgrade type) {
        this.type = type;
        this.setMaxStackSize(1);
        this.setUnlocalizedName("upgrade" + type.ordinal());
        this.setTextureName(TrekTech.MODID + ":chip" + type.ordinal());
        this.setCreativeTab(TrekTech.tabCustom);
    }
    
    @Override
    public final void addInformation(ItemStack stack, EntityPlayer player,
            List stringList, boolean par4) {
        String[] desc = this.type.getDesc();
        for (String line : desc)
            stringList.add(line);
    }
    
    /**
     * @return the upgrade type
     */
    public final EnumUpgrade getUpgradeType() {
        return this.type;
    }
    
}
