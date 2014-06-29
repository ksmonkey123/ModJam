package ch.awae.trektech;

import net.minecraft.util.EnumChatFormatting;

@SuppressWarnings("javadoc")
public enum EnumUpgrade {
    
    EFFICIENCY("reduces the amount of",
               "plasma / fuel consumed",
               "by a machine",
               EnumChatFormatting.DARK_RED +
               " - 12.5% plasma consumtion",
               EnumChatFormatting.DARK_RED +
               " - 12.5% fuel consumption"),
    REGAIN("increases the amount of",
           "neutral plasma that is",
           "regained from energy plasma",
           EnumChatFormatting.DARK_RED +
           " + 12.5% plasma regain"),
    SPEED("increases the operation speed",
          "of a machine",
          EnumChatFormatting.DARK_RED +
          " + 75% production rate"),
    SPECIAL("adds special behavior to a machine",
            EnumChatFormatting.DARK_RED +
            " + 20% ore doubling chance (furnace)"),
    SECURITY("prevents a machine from performing",
             "unwanted operations",
             EnumChatFormatting.DARK_RED +
             " prevent scrap production (furnace)");
    
    private final String[] description;
    
    private EnumUpgrade(String... desc) {
        this.description = desc;
    }
    
    public String[] getDesc() {
        return this.description;
    }
}
