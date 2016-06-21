package riskyken.cosmeticWings.common.wings;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import riskyken.cosmeticWings.common.network.PacketHandler;
import riskyken.cosmeticWings.common.network.message.MessageServerWingsData;

public class ExPropsWingsData implements ICapabilitySerializable {

    public static final String TAG_EXT_PROP_NAME = "playerWingData";

    private EntityPlayer player;
    private WingsData wingData;

    public ExPropsWingsData(EntityPlayer player) {
        this.player = player;
        this.wingData = new WingsData();
    }
/*
    public static final ExPropsWingsData get(EntityPlayer player) {
        player.getCapability(this, EnumFacing.NORTH);
        return (ExPropsWingsData) player.getExtendedProperties(TAG_EXT_PROP_NAME);
    }
*/

    public WingsData getWingData() {
        return wingData;
    }

    public void sendWingDataToPlayer(EntityPlayerMP targetPlayer) {
        PacketHandler.networkWrapper.sendTo(new MessageServerWingsData(player.getUniqueID(), wingData), targetPlayer);
    }

    public void sendWingDataToAllAround() {
        TargetPoint p = new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 512);
        PacketHandler.networkWrapper.sendToAllAround(new MessageServerWingsData(player.getUniqueID(), wingData), p);
    }

    public void updateWingData(WingsData wingData) {
        this.wingData = wingData;
        sendWingDataToAllAround();
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        // TODO Auto-generated method stub
        return null;
    }
}
