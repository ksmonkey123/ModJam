package ch.awae.trektech;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ch.awae.trektech.blocks.BlockDuraniumWall;
import ch.awae.trektech.blocks.BlockPlasmaEnergizerLow;
import ch.awae.trektech.blocks.BlockPlasmaFurnace;
import ch.awae.trektech.blocks.BlockPlasmaPipe;
import ch.awae.trektech.blocks.BlockPlasmaSource;
import ch.awae.trektech.blocks.BlockPlasmaValve;
import ch.awae.trektech.entities.TileEntityPlasmaEnergizerLow;
import ch.awae.trektech.entities.TileEntityPlasmaFurnace;
import ch.awae.trektech.entities.TileEntityPlasmaPipe;
import ch.awae.trektech.entities.TileEntityPlasmaSource;
import ch.awae.trektech.entities.TileEntityPlasmaValve;
import ch.awae.trektech.items.ItemDilithiumCrystal;
import ch.awae.trektech.items.ItemDilithiumRaw;
import ch.awae.trektech.items.ItemDuraniumIngot;
import ch.awae.trektech.items.ItemPlasmaContainmentRing;
import ch.awae.trektech.items.ItemScrap;
import ch.awae.trektech.items.ItemStarFleetSymbol;
import ch.awae.trektech.items.ItemUpgrade;
import ch.modjam.generic.RegistryUtil;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * TrekTech Core Class
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
@SuppressWarnings("javadoc")
@Mod(modid = TrekTech.MODID, version = TrekTech.VERSION, name = TrekTech.NAME)
public class TrekTech {
    
    // -- MOD DATA --
    public static final String MODID                     = "TrekTech";
    public static final String VERSION                   = "0.1";
    public static final String NAME                      = "Trek Tech";
    
    public static Random       rand                      = new Random();
    
    public static CreativeTabs tabCustom                 = new CreativeTabs(
                                                                 "tabTrekTech") {
                                                             @Override
                                                             @SideOnly(Side.CLIENT)
                                                             public Item getTabIconItem() {
                                                                 return TrekTech.itemStarFleetSymbol;
                                                             }
                                                         };
    
    @Instance("TrekTech")
    public static TrekTech     instance;
    
    // Items
    public static Item         itemStarFleetSymbol       = new ItemStarFleetSymbol();
    public static Item         itemDuraniumIngot         = new ItemDuraniumIngot();
    public static Item         itemDilithiumRaw          = new ItemDilithiumRaw();
    public static Item         itemDilithiumCrystal      = new ItemDilithiumCrystal();
    public static Item         itemPlasmaContainmentRing = new ItemPlasmaContainmentRing();
    public static Item         itemScrap                 = new ItemScrap();
    public static Item[]       upgrades;
    
    public static Block        blockDuraniumWall         = new BlockDuraniumWall();
    
    // Machines
    public static Block        blockPlasmaSource         = new BlockPlasmaSource();
    public static Block        blockPlasmaValve          = new BlockPlasmaValve(
                                                                 "valve1",
                                                                 EnumPlasmaTypes.NEUTRAL);
    public static Block        blockPlasmaEnergizerLow   = new BlockPlasmaEnergizerLow();
    public static Block        blockPlasmaFurnace        = new BlockPlasmaFurnace();
    
    // Pipe System
    public static Block[][]    pipes                     = new Block[EnumPlasmaTypes
                                                                 .values().length][2];
    public static Block[]      valves                    = new Block[EnumPlasmaTypes
                                                                 .values().length];
    
    @SidedProxy(clientSide = "ch.awae.trektech.ClientProxy", serverSide = "ch.awae.trektech.CommonProxy")
    public static CommonProxy  proxy;
    
    /**
     * @param event
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        setMetadata(event.getModMetadata());
        // ITEMS
        RegistryUtil.registerItem(itemStarFleetSymbol);
        RegistryUtil.registerItem(itemDuraniumIngot);
        RegistryUtil.registerItem(itemDilithiumRaw);
        RegistryUtil.registerItem(itemDilithiumCrystal);
        RegistryUtil.registerItem(itemPlasmaContainmentRing);
        RegistryUtil.registerItem(itemScrap);
        // BLOCKS
        RegistryUtil.registerBlock(blockDuraniumWall);
        
        EnumPlasmaTypes plasmaTypes[] = EnumPlasmaTypes.values();
        for (int i = 0; i < plasmaTypes.length; i++) {
            EnumPlasmaTypes plasmaType = EnumPlasmaTypes.values()[i];
            // PIPES
            pipes[i][0] = new BlockPlasmaPipe("pipe" + i, plasmaType,
                    plasmaType.getRadius());
            pipes[i][1] = new BlockPlasmaPipe("pipe" + i + "c", plasmaType);
            RegistryUtil.registerBlock(pipes[i][0], TileEntityPlasmaPipe.class);
            RegistryUtil.registerBlock(pipes[i][1]);
            addEncasingRecipe(i);
            valves[i] = new BlockPlasmaValve("valve" + i, plasmaType);
            addValveRecipe(i);
            RegistryUtil.registerBlock(valves[i], TileEntityPlasmaValve.class);
        }
        
        upgrades = new ItemUpgrade[EnumUpgrade.values().length];
        for (EnumUpgrade upgrade : EnumUpgrade.values()) {
            upgrades[upgrade.ordinal()] = new ItemUpgrade(upgrade);
            RegistryUtil.registerItem(upgrades[upgrade.ordinal()]);
        }
        
        RegistryUtil.registerBlock(blockPlasmaSource,
                TileEntityPlasmaSource.class);
        RegistryUtil.registerBlock(blockPlasmaEnergizerLow,
                TileEntityPlasmaEnergizerLow.class);
        RegistryUtil.registerBlock(blockPlasmaFurnace,
                TileEntityPlasmaFurnace.class);
        // RECIPES
        registerRecipes();
        proxy.registerRenderers();
        
        Properties.WRENCH = Items.diamond;
    }
    
    @SuppressWarnings("boxing")
    private static void registerRecipes() {
        GameRegistry.addSmelting(itemDilithiumRaw, new ItemStack(
                itemDilithiumCrystal, 1), 0.5F);
        GameRegistry.addShapedRecipe(new ItemStack(blockDuraniumWall, 4),
                "DDD", "DDD", 'D', new ItemStack(itemDuraniumIngot));
        ItemStack duraniumWallStack = new ItemStack(blockDuraniumWall);
        GameRegistry.addShapelessRecipe(new ItemStack(itemDuraniumIngot, 6),
                duraniumWallStack, duraniumWallStack, duraniumWallStack,
                duraniumWallStack);
        GameRegistry.addSmelting(Items.redstone, new ItemStack(
                itemPlasmaContainmentRing, 1), 0.5F);
        GameRegistry.addShapedRecipe(new ItemStack(pipes[0][0], 6), "III",
                "CCC", "III", 'I', new ItemStack(Items.iron_ingot), 'C',
                new ItemStack(itemPlasmaContainmentRing));
        GameRegistry.addShapedRecipe(new ItemStack(pipes[1][0], 6), "PPP",
                "CCC", "PPP", 'P', new ItemStack(pipes[0][0]), 'C',
                new ItemStack(itemPlasmaContainmentRing));
        
        ItemStack duraniumings = new ItemStack(itemDuraniumIngot);
        ItemStack mk1pipestack = new ItemStack(pipes[1][0]);
        ItemStack containments = new ItemStack(itemPlasmaContainmentRing);
        
        GameRegistry.addShapelessRecipe(new ItemStack(pipes[2][0], 3),
                duraniumings, duraniumings, duraniumings, containments,
                containments, containments, mk1pipestack, mk1pipestack,
                mk1pipestack);
        
        ItemStack mk2pipestack = new ItemStack(pipes[2][0]);
        
        GameRegistry.addShapelessRecipe(new ItemStack(pipes[3][0], 3),
                duraniumings, duraniumings, duraniumings, containments,
                containments, containments, mk2pipestack, mk2pipestack,
                mk2pipestack);
    }
    
    @SuppressWarnings("boxing")
    private static void addEncasingRecipe(int pipeID) {
        GameRegistry.addShapedRecipe(new ItemStack(pipes[pipeID][1], 4), " P ",
                "PDP", " P ", 'D', new ItemStack(blockDuraniumWall), 'P',
                new ItemStack(pipes[pipeID][0]));
    }
    
    private static void addValveRecipe(int pipeID) {
        // TODO: add valve recipes
    }
    
    private static void setMetadata(ModMetadata meta) {
        meta.autogenerated = false;
        meta.authorList.add("Andreas Waelchli (andreas.waelchli@me.com)");
        meta.name = NAME;
        meta.version = VERSION;
        meta.description = "Star Trek Technologies";
    }
}
