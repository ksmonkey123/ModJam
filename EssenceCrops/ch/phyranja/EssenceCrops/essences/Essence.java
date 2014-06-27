package ch.phyranja.EssenceCrops.essences;

/**
 * @author phyranja
 */
@SuppressWarnings("javadoc")
public enum Essence {
	NEUTRAL, COAL, REDSTONE, GLOWSTONE, IRON, COPPER, GOLD, DIAMOND, EMERALD;
	
	@Override public String toString() {
		   //only capitalize the first letter
		   String s = super.toString();
		   return s.toLowerCase();
		 }
}
