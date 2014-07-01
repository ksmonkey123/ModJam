package ch.awae.trektech;

@SuppressWarnings("javadoc")
public class BasicUpgrade {
    
    private float speed_increase;
    private float plasma_increase;
    private float regain_increase;
    
    public BasicUpgrade(float speed_increase, float plasma_increase,
            float regain_increase) {
        super();
        this.speed_increase = speed_increase;
        this.plasma_increase = plasma_increase;
        this.regain_increase = regain_increase;
    }
    
    public float getSpeed_increase() {
        return this.speed_increase;
    }
    
    public float getPlasma_increase() {
        return this.plasma_increase;
    }
    
    public float getRegain_increase() {
        return this.regain_increase;
    }
    
    public static BasicUpgrade combineUpgrades(BasicUpgrade... upgrades) {
        if (upgrades == null || upgrades.length == 0)
            return new BasicUpgrade(0, 0, 0);
        float speed = 0;
        float regain = 0;
        float plasma = 0;
        for (BasicUpgrade upgrade : upgrades) {
            if (upgrade == null)
                continue;
            speed += upgrade.speed_increase;
            regain += upgrade.regain_increase;
            plasma += upgrade.plasma_increase;
        }
        return new BasicUpgrade(speed, plasma, regain);
    }
    
}
