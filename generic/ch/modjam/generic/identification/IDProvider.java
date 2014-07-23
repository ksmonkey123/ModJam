package ch.modjam.generic.identification;

/**
 * efficient IUniqueIdProvider
 * 
 * @since 06.07.2013
 * @author judos
 */
public class IDProvider implements IUniqueIdProvider {

	protected static final int		MAX_SLOTS	= 256;

	protected final int				lvl;
	protected final int				divisor;

	protected int					groupsFull;
	protected int					groupsEmpty;
	protected IUniqueIdProvider[]	groups;
	/**
	 * cache variable in order to find free slots faster
	 */
	protected int					lowestFreeSlot;

	/**
	 * 
	 */
	public IDProvider() {
		this(3); // starts as level 3 implementation to test first byte (highest value byte)
	}

	protected IDProvider(int lvl) {
		if (lvl == 0)
			throw new IllegalStateException(
				"Can't instantiate DuplicateFilter as lvl0, use DuplicateByteFilter if you only want to use 1 byte");
		this.lvl = lvl;
		this.groups = new IUniqueIdProvider[MAX_SLOTS];
		this.divisor = (int) Math.pow(MAX_SLOTS, lvl);
		this.groupsFull = 0;
		this.groupsEmpty = MAX_SLOTS;
		this.lowestFreeSlot = 0;
	}

	@Override
	public synchronized int getFreeID() throws IllegalStateException {
		for (int i = this.lowestFreeSlot; i < MAX_SLOTS; i++) {
			try {
				if (this.groups[i] == null) {
					this.lowestFreeSlot = i;
					return i * this.divisor;
				}
				int slot = this.groups[i].getFreeID(); // throws exception
				this.lowestFreeSlot = i;
				return gAndIndexToId(i, slot);
			} catch (IllegalStateException e) {
				// continue in for loop
				continue;
			}
		}
		throw new IllegalStateException("No free slot found");
	}

	@Override
	public synchronized boolean hasFreeID() {
		return this.groupsFull < MAX_SLOTS;
	}

	@Override
	public synchronized void useID(int id) throws IllegalStateException {
		int gIndex = idToGIndex(id);
		int lowerIndex = id % this.divisor;

		if (this.groups[gIndex] == null) {
			this.groupsEmpty--;
			if (this.lvl > 1)
				this.groups[gIndex] = new IDProvider(this.lvl - 1);
			else
				this.groups[gIndex] = new IDByteProvider();
		}

		this.groups[gIndex].useID(lowerIndex); // might throw exception in case id is already in use

		if (!this.groups[gIndex].hasFreeID()) {
			this.groupsFull++;
			if (this.lowestFreeSlot == gIndex)
				this.lowestFreeSlot++;
		}
	}

	protected synchronized int idToGIndex(int id) {
		int gIndex = id / this.divisor;
		// since integers start at -2^31
		if (this.lvl == 3)
			gIndex += 128;
		return gIndex;
	}

	protected synchronized int gAndIndexToId(int gIndex, int lowerId) {
		if (this.lvl == 3)
			gIndex -= 128;
		return gIndex * this.divisor + lowerId;
	}

	@Override
	public synchronized void freeID(int id) {
		int gIndex = idToGIndex(id);
		if (this.groups[gIndex] == null)
			return;
		int lowIndex = id % this.divisor;
		if (!this.groups[gIndex].hasFreeID())
			this.groupsFull--;
		this.groups[gIndex].freeID(lowIndex);

		if (!this.groups[gIndex].hasOccuredId()) {
			this.groups[gIndex] = null;
			this.groupsEmpty++;
		}

		if (gIndex < this.lowestFreeSlot)
			this.lowestFreeSlot = gIndex;
	}

	@Override
	public synchronized boolean isFreeID(int id) {
		int gIndex = idToGIndex(id);
		if (this.groups[gIndex] == null)
			return true;
		int lowIndex = id % this.divisor;
		return this.groups[gIndex].isFreeID(lowIndex);
	}

	@Override
	public synchronized boolean hasOccuredId() {
		return this.groupsEmpty < MAX_SLOTS;
	}

}
