package ch.phyranja.EssenceCrops;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import ch.judos.mcmod.TutorialMod;
import ch.phyranja.EssenceCrops.*;
import ch.phyranja.EssenceCrops.items.NeutralEssenceSeed;
import ch.phyranja.EssenceCrops.lib.*;
import ch.phyranja.EssenceCrops.world.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * if you need to add dependencies:<br>
 * dependencies="required-after:"modid"@["version"]"
 */
@SuppressWarnings("javadoc")
@Mod(modid = References.MOD_ID, version = References.VERSION)
public class EssenceCrops {
	@SidedProxy(clientSide = References.Client, serverSide = References.Common)
	public static CommonProxy proxy;

	public static CreativeTabs modTab;
	
	public static NeutralEssenceSeed neutralSeed;

	@Mod.Instance("essencecrops")
	public static EssenceCrops instance;
		
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		System.out.println("PreInit EssenceCrops");
		createCreativeTab();
		
		addSeeds();

		proxy.registerRenderInformation();
	}
	
	private void addSeeds() {
		neutralSeed = new NeutralEssenceSeed();
		GameRegistry.registerItem(neutralSeed, Names.NeutralES);
		
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		System.out.println("Init EssenceCrops");
		
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		System.out.println("PostInit EssenceCrops");

	}
	
	
	private void createCreativeTab() {
		modTab = new CreativeTabs("EssenceCrops") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return EssenceCrops.neutralSeed;
			}

			@Override
			@SideOnly(Side.CLIENT)
			public String getTabLabel() {
				return "EssenceCrops";
			}

			@Override
			@SideOnly(Side.CLIENT)
			public String getTranslatedTabLabel() {
				return "EssenceCrops";
			}
		};
	}
}
