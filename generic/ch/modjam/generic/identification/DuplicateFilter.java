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
	public int getFreeID() {
		// TODO:
		return 0;
	}

	@Override
	public boolean hasFreeID() {
		return this.used < MAX_SLOTS;
	}

	@Override
	public void useID(int id) {
		// since integers start at -2^31
		// gIndex += 128;
		int gIndex = id / divisor;
		int lowerIndex = id % divisor;
	}

	@Override
	public void freeID(int id) {

	}

	@Override
	public boolean isFreeID(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
