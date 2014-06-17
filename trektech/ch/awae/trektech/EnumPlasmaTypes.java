package ch.awae.trektech;

/**
 * Holds the different plasma types present
 * 
 * @author Andreas Waelchli <andreas.waelchli@me.com>
 */
public enum EnumPlasmaTypes {

	NEUTRAL((byte) 0, "Neutral Plasma"), LOW((byte) 1,
			"Low Energy Plasma (EPS Mk. 1)"), MEDIUM((byte) 2,
			"Medium Energy Plasma (EPS Mk. 2)"), HIGH((byte) 3,
			"High Energy Plasma (EPS Mk. 3)");

	private final byte index;
	private final String desc;

	private EnumPlasmaTypes(byte index, String desc) {
		this.index = index;
		this.desc = desc;
	}

	public byte getIndex() {
		return this.index;
	}

	public static EnumPlasmaTypes getByIndex(byte index) {
		for (EnumPlasmaTypes type : EnumPlasmaTypes.values()) {
			if (type.getIndex() == index)
				return type;
		}
		return null;
	}

}
