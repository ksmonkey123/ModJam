package ch.judos.mcmod.potions;

import net.minecraft.potion.Potion;
import ch.modjam.generic.AbstractPotion;
import cpw.mods.fml.common.registry.LanguageRegistry;

@SuppressWarnings("javadoc")
public class PotionTest extends AbstractPotion {

	public static final String NAME = "potion.mcmodTest.name";

	public PotionTest() {
		super(false, 0xFF55FF00);

		this.setPotionName(LanguageRegistry.instance().getStringLocalization(
				NAME));
		this.setIconIndex(6, 1);
	}

	@Override
	protected Potion setIconIndex(int par1, int par2) {
		return super.setIconIndex(par1, par2);
	}

}
