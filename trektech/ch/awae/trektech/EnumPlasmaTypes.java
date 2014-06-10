package ch.awae.trektech;

public enum EnumPlasmaTypes {

	NEUTRAL((byte) 0), LOW((byte) 1), MEDIUM((byte) 2), HIGH((byte) 3);

	private byte index;

	private EnumPlasmaTypes(byte index) {
		this.index = index;
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
