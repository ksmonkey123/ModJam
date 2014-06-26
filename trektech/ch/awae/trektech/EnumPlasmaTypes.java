package ch.awae.trektech;

/**
 * Holds the different plasma types present
 * 
 * @author Andreas Waelchli (andreas.waelchli@me.com)
 */
public enum EnumPlasmaTypes {
    
    /**
     * Neutral Plasma. This is the basic plasma type
     */
    NEUTRAL("Neutral Plasma", 4),
    
    /**
     * Low-Energy Plasma. This is the first Tier of energetic plasma. It's
     * production method should be relatively cheap.
     */
    LOW("Low Energy Plasma (EPS Mk. 1)", 4),
    
    /**
     * Medium-Energy Plasma. This is the second Tier of energetic plasma. It's
     * production methods should be rather expensive (e.g. fission). It should
     * not be able to be gained from low-energy plasma.
     */
    MEDIUM("Medium Energy Plasma (EPS Mk. 2)", 5),
    
    /**
     * High-Energy Plasma. This is the third Tier of energetic plasma. It's
     * production methods should be very expensive (e.g. fusion or anti-matter).
     * It should not be able to be gained from lower-energy plasma.
     */
    HIGH("High Energy Plasma (EPS Mk. 3)", 6);
    
    private final String desc;
    private final int    radius;
    
    private EnumPlasmaTypes(String desc, int radius) {
        this.desc = desc;
        this.radius = radius;
    }
    
    /**
     * This list contains all valid plasma types. This does exclude dummy types.
     * Do only use plasma types in this list for machines / pipes!
     */
    public static EnumPlasmaTypes[] VALID = { NEUTRAL, LOW, MEDIUM, HIGH };
    
    @Override
    public String toString() {
        return this.desc;
    }
    
    /**
     * @return the pipe radius
     */
    public int getRadius() {
        return this.radius;
    }
    
}
