package ch.judos.at.gearbox;

public enum SendMode {
	Periodically(0), GondolaFilled(1), OnRedstone(2);
	public int	id;

	private SendMode(int id) {
		this.id = id;
	}

	public static SendMode fromId(int id) {
		for (SendMode x : values()) {
			if (x.id == id)
				return x;
		}
		return null;
	}
}
