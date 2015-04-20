package riskyken.cosmeticWings.common.wings;

import java.util.ArrayList;
import java.util.HashMap;

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
    
    private final HashMap<String, IWings> wingsMap;
    
    public static void init() {
        INSTANCE = new WingsRegistry();
    }
    
    public WingsRegistry() {
        wingsMap = new HashMap<String, IWings>();
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
    
    private void registerWingsClass(IWings wings) {
        ModLogger.log("Registering wings: " + wings.getName() + " as: " + wings.getRegistryName());
        wingsMap.put(wings.getRegistryName(), wings);
    }
    
    public int getNumberOfWingsRegistered() {
        return wingsMap.size();
    }
    
    public IWings getWingsFormRegistryName(String registryName) {
        return wingsMap.get(registryName);
    }
    
    public ArrayList<IWings> getRegisteredWingTypes() {
        ArrayList<IWings> wingTypes = new ArrayList<IWings>();
        for (int i = 0; i < wingsMap.size(); i++) {
            String registryName = (String) wingsMap.keySet().toArray()[i];
            IWings wingType = getWingsFormRegistryName(registryName);
            if (wingType != null) {
                wingTypes.add(wingType);
            }
        }
        return wingTypes;
    }
}
