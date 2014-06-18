package ch.judos.mcmod.potions;

import ch.modjam.generic.AbstractPotion;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class PotionTest extends AbstractPotion {

	public static final String NAME = "potion.mcmodTest.name";

	public PotionTest() {
		super(false, 0xFF55FF00);

		this.setPotionName(LanguageRegistry.instance().getStringLocalization(
				NAME));
		this.setIconIndex(0, 0);

	}

}
