package ch.modjam.generic.multiblock;

/**
 * This exception is thrown whenever a tile-entity that is already owned by another MultiBlock is
 * tried to be registered for a new MultiBlock
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public class AlreadyOwnedByMultiblockException extends Exception {

	private static final long	serialVersionUID	= -6509680625227675110L;

	/**
     * 
     */
	public AlreadyOwnedByMultiblockException() {
		// XXX: could add super("message") to explain further information
	}

}
