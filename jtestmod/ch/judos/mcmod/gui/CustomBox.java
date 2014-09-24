package ch.judos.mcmod.gui;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;

/**
 * @author judos
 */
public class CustomBox extends Box {

	/**
	 * 
	 */
	public CustomBox() {
		super();
		this.setUnlocalizedName(Names.CustomBox);
		this.setTextureName(References.MOD_ID + ":" + Names.CustomBox);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new CustomBoxTE();
	}

}
