package test;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = Test.MODID, version = Test.VERSION, name = Test.MODID)
public class Test {

	public static final String MODID = "Test";
	public static final String VERSION = "0.1";

	public static CreativeTabs tabCustom = new CreativeTabs("tabTest") {

		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Items.diamond;
		}
	};

	@Instance("Test")
	public static Test instance;

	@EventHandler
	public void preInit(FMLInitializationEvent event) {
	}
}
