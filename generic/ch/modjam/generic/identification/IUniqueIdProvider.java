package ch.modjam.generic.identification;

/**
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public interface IUniqueIdProvider {
    
    /**
     * retrieve any free ID
     * 
     * @return any free ID
     * @throws IllegalStateException
     */
    public int getFreeID() throws IllegalStateException;
    
    /**
     * set ID to be used
     * 
     * @param id
     * @throws IllegalStateException
     */
    public void useID(int id) throws IllegalStateException;
    
    /**
     * set ID to be unused
     * 
     * @param id
     */
    public void freeID(int id);
    
    /**
     * indicates whether or not an ID is free.
     * 
     * @param id
     * @return <tt>true</tt> if an ID is not used, <tt>false</tt> otherwise
     */
    public boolean isFreeID(int id);
    
    /**
     * indicates whether or not the provider has at least 1 unused ID.
     * 
     * @return <tt>
     */
    public boolean hasFreeID();
    
}