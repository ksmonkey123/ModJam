package ch.judos.at.gearbox;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
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
