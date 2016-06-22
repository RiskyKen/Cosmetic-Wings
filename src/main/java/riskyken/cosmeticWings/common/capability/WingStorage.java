package riskyken.cosmeticWings.common.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class WingStorage implements IStorage<IWingCapability> {

    @Override
    public NBTBase writeNBT(Capability<IWingCapability> capability, IWingCapability instance, EnumFacing side) {
        NBTTagCompound compound = new NBTTagCompound();
        instance.getWingData().saveNBTData(compound);
        return compound;
    }

    @Override
    public void readNBT(Capability<IWingCapability> capability, IWingCapability instance, EnumFacing side, NBTBase nbt) {
        instance.getWingData().loadNBTData((NBTTagCompound) nbt);
    }
}
