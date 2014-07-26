package ch.awae.trektech;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
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
import ch.modjam.generic.multiblock.Multiblock;
import ch.modjam.generic.multiblock.MultiblockBlock;
import ch.modjam.generic.multiblock.MultiblockRegistry;
import ch.modjam.generic.multiblock.MultiblockTileEntity;
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
        TrekTech.setMetadata(event.getModMetadata());
        MultiblockBlock myMBB = (MultiblockBlock) new MultiblockBlock(
                Material.rock) {
            @Override
            public void onDeactivation(World w, int x, int y, int z) {
                System.out.println(" -->> deactivation @ "
                        + this.getUnlocalizedName() + " [" + x + "," + y + ","
                        + z + "]");
            }
            
            @Override
            public void onActivation(World w, int x, int y, int z) {
                System.out.println(" -->> activation @ "
                        + this.getUnlocalizedName() + " [" + x + "," + y + ","
                        + z + "]");
            }
            
            @Override
            public MultiblockTileEntity getCustomTileEntity(World var1, int var2) {
                return null;
            }
        }.setBlockName("testBlock").setCreativeTab(TrekTech.tabCustom)
                .setBlockTextureName("trektech:test_block");
        RegistryUtil.registerBlock(myMBB);
        
        Multiblock mb = new Multiblock();
        mb.addBlock((short) 0, (short) 0, (short) 0, myMBB);
        mb.addBlock((short) 0, (short) 1, (short) 0, myMBB);
        MultiblockRegistry.instance().registerMultiblock(mb, "testStructure");
        
        // ITEMS
        RegistryUtil.registerItem(TrekTech.itemStarFleetSymbol);
        RegistryUtil.registerItem(TrekTech.itemDuraniumIngot);
        RegistryUtil.registerItem(TrekTech.itemDilithiumRaw);
        RegistryUtil.registerItem(TrekTech.itemDilithiumCrystal);
        RegistryUtil.registerItem(TrekTech.itemPlasmaContainmentRing);
        RegistryUtil.registerItem(TrekTech.itemScrap);
        // BLOCKS
        RegistryUtil.registerBlock(TrekTech.blockDuraniumWall);
        
        EnumPlasmaTypes plasmaTypes[] = EnumPlasmaTypes.values();
        for (int i = 0; i < plasmaTypes.length; i++) {
            EnumPlasmaTypes plasmaType = EnumPlasmaTypes.values()[i];
            // PIPES
            TrekTech.pipes[i][0] = new BlockPlasmaPipe("pipe" + i, plasmaType,
                    plasmaType.getRadius());
            TrekTech.pipes[i][1] = new BlockPlasmaPipe("pipe" + i + "c",
                    plasmaType);
            RegistryUtil.registerBlock(TrekTech.pipes[i][0],
                    TileEntityPlasmaPipe.class);
            RegistryUtil.registerBlock(TrekTech.pipes[i][1]);
            TrekTech.addEncasingRecipe(i);
            TrekTech.valves[i] = new BlockPlasmaValve("valve" + i, plasmaType);
            TrekTech.addValveRecipe(i);
            RegistryUtil.registerBlock(TrekTech.valves[i],
                    TileEntityPlasmaValve.class);
        }
        
        TrekTech.upgrades = new ItemUpgrade[EnumUpgrade.values().length];
        for (EnumUpgrade upgrade : EnumUpgrade.values()) {
            TrekTech.upgrades[upgrade.ordinal()] = new ItemUpgrade(upgrade);
            RegistryUtil.registerItem(TrekTech.upgrades[upgrade.ordinal()]);
        }
        
        RegistryUtil.registerBlock(TrekTech.blockPlasmaSource,
                TileEntityPlasmaSource.class);
        RegistryUtil.registerBlock(TrekTech.blockPlasmaEnergizerLow,
                TileEntityPlasmaEnergizerLow.class);
        RegistryUtil.registerBlock(TrekTech.blockPlasmaFurnace,
                TileEntityPlasmaFurnace.class);
        // RECIPES
        TrekTech.registerRecipes();
        TrekTech.proxy.registerRenderers();
        
        Properties.WRENCH = Items.diamond;
    }
    
    @SuppressWarnings("boxing")
    private static void registerRecipes() {
        GameRegistry.addSmelting(TrekTech.itemDilithiumRaw, new ItemStack(
                TrekTech.itemDilithiumCrystal, 1), 0.5F);
        GameRegistry.addShapedRecipe(new ItemStack(TrekTech.blockDuraniumWall,
                4), "DDD", "DDD", 'D',
                new ItemStack(TrekTech.itemDuraniumIngot));
        ItemStack duraniumWallStack = new ItemStack(TrekTech.blockDuraniumWall);
        GameRegistry.addShapelessRecipe(new ItemStack(
                TrekTech.itemDuraniumIngot, 6), duraniumWallStack,
                duraniumWallStack, duraniumWallStack, duraniumWallStack);
        GameRegistry.addSmelting(Items.redstone, new ItemStack(
                TrekTech.itemPlasmaContainmentRing, 1), 0.5F);
        GameRegistry.addShapedRecipe(new ItemStack(TrekTech.pipes[0][0], 6),
                "III", "CCC", "III", 'I', new ItemStack(Items.iron_ingot), 'C',
                new ItemStack(TrekTech.itemPlasmaContainmentRing));
        GameRegistry.addShapedRecipe(new ItemStack(TrekTech.pipes[1][0], 6),
                "PPP", "CCC", "PPP", 'P', new ItemStack(TrekTech.pipes[0][0]),
                'C', new ItemStack(TrekTech.itemPlasmaContainmentRing));
        
        ItemStack duraniumings = new ItemStack(TrekTech.itemDuraniumIngot);
        ItemStack mk1pipestack = new ItemStack(TrekTech.pipes[1][0]);
        ItemStack containments = new ItemStack(
                TrekTech.itemPlasmaContainmentRing);
        
        GameRegistry.addShapelessRecipe(new ItemStack(TrekTech.pipes[2][0], 3),
                duraniumings, duraniumings, duraniumings, containments,
                containments, containments, mk1pipestack, mk1pipestack,
                mk1pipestack);
        
        ItemStack mk2pipestack = new ItemStack(TrekTech.pipes[2][0]);
        
        GameRegistry.addShapelessRecipe(new ItemStack(TrekTech.pipes[3][0], 3),
                duraniumings, duraniumings, duraniumings, containments,
                containments, containments, mk2pipestack, mk2pipestack,
                mk2pipestack);
        
        TTRegistry.registerAdditionalFurnaceRecipe(Items.ender_pearl,
                new ItemStack(Items.ender_eye, 1));
    }
    
    @SuppressWarnings("boxing")
    private static void addEncasingRecipe(int pipeID) {
        GameRegistry.addShapedRecipe(
                new ItemStack(TrekTech.pipes[pipeID][1], 4), " P ", "PDP",
                " P ", 'D', new ItemStack(TrekTech.blockDuraniumWall), 'P',
                new ItemStack(TrekTech.pipes[pipeID][0]));
    }
    
    @SuppressWarnings("unused")
    private static void addValveRecipe(int pipeID) {
        // TODO: add valve recipes
    }
    
    private static void setMetadata(ModMetadata meta) {
        meta.autogenerated = false;
        meta.authorList.add("Andreas Waelchli (andreas.waelchli@me.com)");
        meta.name = TrekTech.NAME;
        meta.version = TrekTech.VERSION;
        meta.description = "Star Trek Technologies";
    }
}
