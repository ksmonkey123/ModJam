package ch.modjam.generic.identification;

/**
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public interface IUniqueIdProvider {
    
    /**
     * retrieve any free ID
     * 
     * @return any free ID
     */
    public int getFreeID();
    
    /**
     * set ID to be used
     * 
     * @param id
     */
    public void useID(int id);
    
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
    
}