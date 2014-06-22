package ch.modjam.generic;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.potion.Potion;

/**
 * improves creation of potions, automatically generates a new ID unique for
 * every new potion you add
 * 
 * @author j
 * 
 */
@SuppressWarnings("javadoc")
public abstract class AbstractPotion extends Potion {

	public AbstractPotion(boolean isBadEffect, int liquidColor) {
		super(getNextPotionId(), isBadEffect, liquidColor);
	}

	public static int getNextPotionId() {
		makePotionArrayAccessibleAndIncreaseSize();
		for (int i = 1; i < potionTypes.length; i++)
			if (potionTypes[i] == null)
				return i;
		throw new IllegalStateException(
			"No more space in Potion.potionTypes array, you can extend it with reflection.");

	}

	/**
	 * makes sure the potion array is public accessible and non final.<br>
	 * also increases size of the array if it's already full.
	 */
	private static void makePotionArrayAccessibleAndIncreaseSize() {
		// make the potion array accessible and modifiable
		for (Field f : Potion.class.getDeclaredFields()) {
			f.setAccessible(true);
			try {
				if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
					Field modfield = Field.class.getDeclaredField("modifiers");
					modfield.setAccessible(true);
					modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

					Potion[] potionTypes = (Potion[]) f.get(null);

					// potion array is already full, create a bigger copy of the
					// array
					if (potionTypes[potionTypes.length - 1] != null) {
						// extend the size of the array
						int newSize = potionTypes.length + 32;
						final Potion[] newPotionTypes = new Potion[newSize];
						System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
						f.set(null, newPotionTypes);
					}
				}
			} catch (Exception e) {
				System.err.println("Severe error, please report this to the mod author:");
				System.err.println(e);
			}
		}
	}

}
