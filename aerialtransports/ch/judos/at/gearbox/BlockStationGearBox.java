package ch.judos.at.gearbox;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import ch.judos.at.ATMain;
import ch.judos.at.lib.ATNames;

public class BlockStationGearBox extends Block {

	public BlockStationGearBox() {
		super(Material.wood);

		this.setUnlocalizedName(ATNames.station_gearbox);
		this.setTextureName(ATMain.MOD_ID + ":" + ATNames.station_gearbox);
		this.setCreativeTab(ATMain.modTab);
		this.setHardness(0.5f);
		this.setHarvestLevel("axe", 0);
	}

}
