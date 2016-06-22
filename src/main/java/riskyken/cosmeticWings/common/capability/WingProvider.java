package riskyken.cosmeticWings.common.capability;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import riskyken.cosmeticWings.common.wings.WingsData;

public class WingProvider implements ICapabilitySerializable, IWingCapability {
    
    @CapabilityInject(IWingCapability.class)
    private static final Capability<IWingCapability> WING_CAP = null;
    
    private final Entity entity;
    private WingsData wingData;
    
    public WingProvider(Entity entity) {
        this.entity = entity;
        wingData = new WingsData();
    }
    
    @Override
    public WingsData getWingData() {
        return wingData;
    }
    
    @Override
    public void setWingsData(WingsData wingData) {
        this.wingData = wingData;
    }

    @Override
    public NBTBase serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        wingData.saveNBTData(compound);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        wingData.loadNBTData((NBTTagCompound) nbt);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability != null && capability == WING_CAP;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (hasCapability(capability, facing)) {
            return WING_CAP.cast(this);
        }
        return null;
    }
}
