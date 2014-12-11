package riskyken.cosmeticWings.common.wings;

import java.util.ArrayList;

import riskyken.cosmeticWings.common.wings.types.WingsAngel;
import riskyken.cosmeticWings.common.wings.types.WingsBlack;
import riskyken.cosmeticWings.common.wings.types.WingsFlandre;
import riskyken.cosmeticWings.common.wings.types.WingsKuroyukihime;
import riskyken.cosmeticWings.common.wings.types.WingsMech;
import riskyken.cosmeticWings.common.wings.types.WingsMetal;
import riskyken.cosmeticWings.common.wings.types.WingsShana;
import riskyken.cosmeticWings.common.wings.types.WingsSmallMech;
import riskyken.cosmeticWings.utils.ModLogger;

public class WingsRegistry {
    
    public static WingsRegistry INSTANCE;
    
    private final ArrayList<IWings> wingsList;
    
    public static void init() {
        INSTANCE = new WingsRegistry();
    }
    
    public WingsRegistry() {
        wingsList = new ArrayList<IWings>();
        registerWings();
    }
    
    private void registerWings() {
        registerWingsClass(new WingsBlack());
        registerWingsClass(new WingsAngel());
        registerWingsClass(new WingsShana());
        registerWingsClass(new WingsFlandre());
        registerWingsClass(new WingsMetal());
        registerWingsClass(new WingsKuroyukihime());
        registerWingsClass(new WingsSmallMech());
        registerWingsClass(new WingsMech());
    }
    
    public int getSize() {
        return wingsList.size();
    }
    
    public IWings getWingsForId(int index) {
        if (index < 0 | index > wingsList.size() - 1) {
            return null;
        }
        return wingsList.get(index);
    }
    
    private void registerWingsClass(IWings wings) {
        int wingId = getNextFreeWingsId();
        wings.setId(wingId);
        ModLogger.log("Registering wings: " + wings.getName() + " with id: " + wingId);
        wingsList.add(wings);
    }
    
    private int getNextFreeWingsId() {
        return wingsList.size();
    }
}
