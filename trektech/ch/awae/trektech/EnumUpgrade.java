package ch.awae.trektech;

import net.minecraft.util.EnumChatFormatting;

@SuppressWarnings("javadoc")
public enum EnumUpgrade {
    
    EFFICIENCY(new BasicUpgrade(0, -0.125f, 0), "reduces the amount of",
            "plasma / fuel consumed", "by a machine",
            EnumChatFormatting.DARK_RED + " -12.5% plasma consumtion",
            EnumChatFormatting.DARK_RED + " -12.5% fuel consumption"),
    REGAIN(new BasicUpgrade(0.1f, 0, 0.125f), "increases the amount of",
            "neutral plasma that is", "regained from energy plasma",
            EnumChatFormatting.DARK_RED + " +12.5% plasma regain",
            EnumChatFormatting.DARK_RED + " - 9.1" + "% production rate"),
    SPEED(new BasicUpgrade(-0.1875f, 0.25f, 0),
            "increases the operation speed", "of a machine",
            EnumChatFormatting.DARK_RED + " +75% production rate",
            EnumChatFormatting.DARK_RED + " +25% plasma consumption"),
    SPECIAL("adds special behavior to a machine", EnumChatFormatting.DARK_RED
            + " +20% ore doubling chance (furnace)"),
    SECURITY("prevents a machine from performing", "unwanted operations",
            EnumChatFormatting.DARK_RED + " prevent scrap production (furnace)");
    
    private final String[]     description;
    private final BasicUpgrade upgrade;
    
    private EnumUpgrade(BasicUpgrade upgrade, String... desc) {
        this.description = desc;
        this.upgrade = upgrade;
    }
    
    private EnumUpgrade(String... desc) {
        this.upgrade = null;
        this.description = desc;
    }
    
    public String[] getDesc() {
        return this.description;
    }
    
    public BasicUpgrade getUpgrade() {
        return this.upgrade;
    }
}
