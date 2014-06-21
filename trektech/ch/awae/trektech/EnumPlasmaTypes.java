package ch.awae.trektech;

/**
 * Holds the different plasma types present
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
public enum EnumPlasmaTypes {

	MIXED("Mixed"), NEUTRAL("Neutral Plasma"), LOW(
			"Low Energy Plasma (EPS Mk. 1)"), MEDIUM(
			"Medium Energy Plasma (EPS Mk. 2)"), HIGH(
			"High Energy Plasma (EPS Mk. 3)");

	private final String desc;

	private EnumPlasmaTypes(String desc) {
		this.desc = desc;
	}

	public static EnumPlasmaTypes[] VALID = { NEUTRAL, LOW, MEDIUM, HIGH };

	@Override
	public String toString() {
		return this.desc;
	}

}
