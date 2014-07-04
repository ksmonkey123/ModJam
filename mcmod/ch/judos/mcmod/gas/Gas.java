package ch.judos.mcmod.gas;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import ch.judos.mcmod.lib.Names;
import ch.judos.mcmod.lib.References;

/**
 * @author j
 *
 */
public class Gas extends Block {

	/**
	 * 
	 */
	public Gas() {
		super(Material.air);
		this.setBlockTextureName(References.MOD_ID + ":" + Names.Gas);
	}

}
