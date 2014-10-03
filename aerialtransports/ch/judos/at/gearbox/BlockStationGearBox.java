package ch.judos.at.gearbox;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ch.judos.at.ATMain;
import ch.judos.at.lib.ATNames;
import ch.modjam.generic.gui.GenericGuiHandler;

public class BlockStationGearBox extends BlockContainer {

	public BlockStationGearBox() {
		super(Material.wood);

		this.setUnlocalizedName(ATNames.station_gearbox);
		this.setTextureName(ATMain.MOD_ID + ":" + ATNames.station_gearbox);
		this.setCreativeTab(ATMain.modTab);
		this.setHardness(0.5f);
		this.setHarvestLevel("axe", 0);
	}

	@Override
	public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public boolean getWeakChanges(IBlockAccess world, int x, int y, int z) {
		System.out.println("weak change: " + x + "," + y + "," + z);
		return super.getWeakChanges(world, x, y, z);
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess worldIn, int x, int y, int z, int side) {
		System.out
			.println("checking for strong power: " + x + "," + y + "," + z + ", side:" + side);
		return super.isProvidingStrongPower(worldIn, x, y, z, side);
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess worldIn, int x, int y, int z, int side) {
		System.out.println("checking for weak power: " + x + "," + y + "," + z + ", side:" + side);
		return super.isProvidingWeakPower(worldIn, x, y, z, side);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player,
			int side, float hitX, float hitY, float hitZ) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (tileEntity instanceof TEStationGearbox)
			return GenericGuiHandler.openGUI(player, world, x, y, z);
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TEStationGearbox();
	}

}
