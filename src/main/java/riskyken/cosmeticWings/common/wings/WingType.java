package riskyken.cosmeticWings.common.wings;

import net.minecraft.util.StatCollector;
import riskyken.cosmeticWings.common.lib.LibModInfo;

public enum WingType {
    NONE(false),
    BLACK(false),
    WHITE(true),
    SHANA(false),
    FLANDRE(false),
    METAL(false);
    
    public final boolean canRecolour;
    
    private WingType(boolean canRecolour) {
        this.canRecolour = canRecolour;
    }
    
    public String getLocalizedName() {
        String unlocalizedName = "wings." + LibModInfo.ID.toLowerCase() + ":" + this.name().toLowerCase() + ".name";
        String localizedName = StatCollector.translateToLocal(unlocalizedName);
        if (!unlocalizedName.equals(localizedName)){
            return localizedName;
        }
        return unlocalizedName;
    }
    
    public static WingType getOrdinal(int id) {
        return WingType.values()[id];
    }
}
