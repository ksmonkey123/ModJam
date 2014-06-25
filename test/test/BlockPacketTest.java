package test;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ch.awae.trektech.TrekTech;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

@SuppressWarnings("javadoc")
public class BlockPacketTest extends Block {

	public BlockPacketTest() {
		super(Material.rock);
		setCreativeTab(TrekTech.tabCustom);
		setBlockName("packetTest");
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z,
			EntityPlayer p, int s, float f0, float f1, float f3) {
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			System.out.println("sending message");
			Test.NETWORK.sendToServer(new TestMessage("Hello World"));
		}
		return true;
	}

}
