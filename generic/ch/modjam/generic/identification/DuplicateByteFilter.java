package ch.modjam.generic.identification;

/**
 * @author j
 *
 */
public class DuplicateByteFilter implements IUniqueIdProvider {

	private static final int	MAX_SLOTS	= 256;

	private boolean[]			slotUsed;
	private int					used;
	private int					lowestFreeId;

	/**
	 * 
	 */
	public DuplicateByteFilter() {
		this.slotUsed = new boolean[256];
		this.used = 0;
		this.lowestFreeId = 0;
	}

	@Override
	public int getFreeID() throws IllegalStateException {
		for (int i = this.lowestFreeId; i < MAX_SLOTS; i++) {
			if (!this.slotUsed[i]) {
				this.lowestFreeId = i;
				return i;
			}
		}
		throw new IllegalStateException("No free slot found");
	}

	@Override
	public void useID(int id) throws IllegalStateException {
		if (this.slotUsed[id])
			throw new IllegalStateException("ID already in use");
		this.slotUsed[id] = true;
		this.used++;
		if (this.lowestFreeId == id)
			this.lowestFreeId++;
	}

	@Override
	public void freeID(int id) {
		if (this.slotUsed[id])
			this.used--;
		this.slotUsed[id] = false;
		if (id < this.lowestFreeId)
			this.lowestFreeId = id;
	}

	@Override
	public boolean isFreeID(int id) {
		return !this.slotUsed[id];
	}

	@Override
	public boolean hasFreeID() {
		return this.used < MAX_SLOTS;
	}

	@Override
	public boolean hasOccuredId() {
		return this.used > 0;
	}

}