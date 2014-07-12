package ch.modjam.generic.identification;

/**
 * efficient IUniqueIdProvider, needs to be fully implemented first
 * 
 * @since 06.07.2013
 * @author judos
 */
public class DuplicateFilter implements IUniqueIdProvider {

	private static final int	MAX_SLOTS	= 256;

	private int					lvl;
	private int					divisor;

	private int					used;
	private IUniqueIdProvider[]	groups;
	private int					lowestFreeSlot;

	/**
	 * 
	 */
	public DuplicateFilter() {
		this(3); // starts as level 3 implementation to test first byte (highest value byte)
	}

	protected DuplicateFilter(int lvl) {
		if (lvl == 0)
			throw new IllegalStateException(
				"Can't instantiate DuplicateFilter as lvl0, use lvl3 for integers");
		this.lvl = lvl;
		this.groups = new IUniqueIdProvider[256];
		this.divisor = (int) Math.pow(2, 8 * lvl);
		this.used = 0;
		this.lowestFreeSlot = 0;
	}

	@Override
	public int getFreeID() throws IllegalStateException {
		for (int i = this.lowestFreeSlot; i < MAX_SLOTS; i++) {
			try {
				if (this.groups[i] == null) {
					this.lowestFreeSlot = i;
					return i * this.divisor;
				}
				int slot = this.groups[i].getFreeID(); // throws exception
				this.lowestFreeSlot = i;
				return i * this.divisor + slot;
			} catch (IllegalStateException e) {
				// continue in for loop
				continue;
			}
		}
		throw new IllegalStateException("No free slot found");
	}

	@Override
	public boolean hasFreeID() {
		return this.used < MAX_SLOTS;
	}

	@Override
	public void useID(int id) throws IllegalStateException {
		int gIndex = id / this.divisor;
		// since integers start at -2^31
		if (this.lvl == 3)
			gIndex += 128;

		int lowerIndex = id % this.divisor;

		if (this.groups[gIndex] == null) {
			if (this.lvl > 1)
				this.groups[gIndex] = new DuplicateFilter(this.lvl - 1);
			else
				this.groups[gIndex] = new DuplicateByteFilter();
		}

		this.groups[gIndex].useID(lowerIndex); // might throw exception in case id is already in use

		if (!this.groups[gIndex].hasFreeID() && this.lowestFreeSlot == gIndex)
			this.lowestFreeSlot++;
	}

	@Override
	public void freeID(int id) {
	    // TODO: implement
	}

	@Override
	public boolean isFreeID(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
