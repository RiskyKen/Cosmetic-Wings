package riskyken.cosmeticWings.common.wings;

import net.minecraft.util.StatCollector;
import riskyken.cosmeticWings.common.lib.LibModInfo;

public enum WingType {
    NONE(false, false),
    BLACK(false, false),
    ANGEL(true, false),
    SHANA(false, false),
    FLANDRE(false, false),
    METAL(false, false),
    KUROYUKIHIME(false, false),
    SMALL_MECH(true, true),
    MECH(true, true);

    public final boolean canRecolour;
    public final boolean postRender;

    private WingType(boolean canRecolour, boolean postRender) {
        this.canRecolour = canRecolour;
        this.postRender = postRender;
    }

    public String getLocalizedName() {
        String unlocalizedName = "wings." + LibModInfo.ID.toLowerCase() + ":" + this.name().toLowerCase() + ".name";
        String localizedName = StatCollector.translateToLocal(unlocalizedName);
        if (!unlocalizedName.equals(localizedName)) {
            return localizedName;
        }
        return unlocalizedName;
    }
    
    public String getFlavourText() {
        String unlocalizedName = "wings." + LibModInfo.ID.toLowerCase() + ":" + this.name().toLowerCase() + ".flavour";
        String localizedName = StatCollector.translateToLocal(unlocalizedName);
        if (!unlocalizedName.equals(localizedName)) {
            return localizedName;
        }
        return "";
    }
    
    public static WingType getOrdinal(int id) {
        if (id >= 0 & id < values().length) {
            return WingType.values()[id];
        }
        return NONE;
    }
}
