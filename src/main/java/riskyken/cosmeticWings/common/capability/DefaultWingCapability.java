package riskyken.cosmeticWings.common.capability;

import riskyken.cosmeticWings.common.wings.WingsData;

public class DefaultWingCapability implements IWingCapability {

    @Override
    public WingsData getWingData() {
        return new WingsData();
    } 
    
    @Override
    public void setWingsData(WingsData wingData) {
    }
}
