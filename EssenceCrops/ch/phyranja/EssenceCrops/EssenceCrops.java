package ch.phyranja.EssenceCrops;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import ch.phyranja.EssenceCrops.blocks.*;
import ch.phyranja.EssenceCrops.essences.Essence;
import ch.phyranja.EssenceCrops.items.*;
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
	
	public static EssenceExtractor extractor;
	
	public static EssenceSeed[] seeds=new EssenceSeed[Essence.values().length];
	public static EssencePlant[] plants=new EssencePlant[Essence.values().length];
	public static EssencePetal[] petals=new EssencePetal[Essence.values().length];
	public static BigEssenceCapsule[] bigCapsules=new BigEssenceCapsule[Essence.values().length];
	public static SmallEssenceCapsule[] smallCapsules=new SmallEssenceCapsule[Essence.values().length];
	

	@Mod.Instance("essencecrops")
	public static EssenceCrops instance;
		
	/**
	 * @param e  
	 */
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		System.out.println("PreInit EssenceCrops");
		createCreativeTab();
		
		Names.initNames();
		
		addItems();
		addPlants();
		addMachines();
		

		proxy.registerRenderInformation();
	}
	
	private void addMachines() {
		extractor=new EssenceExtractor();
		GameRegistry.registerBlock(extractor, Names.Extractor);
	
		
	}

	private void addItems() {
		
		for(Essence essence: Essence.values()){
			petals[essence.ordinal()]=new EssencePetal(essence);
			GameRegistry.registerItem(petals[essence.ordinal()], Names.petals[essence.ordinal()]);
		
			seeds[essence.ordinal()]=new EssenceSeed(essence);
			GameRegistry.registerItem(seeds[essence.ordinal()], Names.seeds[essence.ordinal()]);
		
			bigCapsules[essence.ordinal()]=new BigEssenceCapsule(essence);
			GameRegistry.registerItem(bigCapsules[essence.ordinal()], Names.bigCapsules[essence.ordinal()]);
		
			smallCapsules[essence.ordinal()]=new SmallEssenceCapsule(essence);
			GameRegistry.registerItem(smallCapsules[essence.ordinal()], Names.smallCapsules[essence.ordinal()]);
		
			
		
		}
	}

	private void addPlants() {
		
		for(Essence essence: Essence.values()){
			plants[essence.ordinal()]=new EssencePlant(essence);
			GameRegistry.registerBlock(plants[essence.ordinal()], Names.plants[essence.ordinal()]);
		}
	}

	

	/**
	 * @param e  
	 */
	@EventHandler
	public void init(FMLInitializationEvent e) {
		System.out.println("Init EssenceCrops");
		
	}

	/**
	 * @param e  
	 */
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		System.out.println("PostInit EssenceCrops");

	}
	
	
	private static void createCreativeTab() {
		modTab = new CreativeTabs("EssenceCrops") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return EssenceCrops.seeds[0];
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
