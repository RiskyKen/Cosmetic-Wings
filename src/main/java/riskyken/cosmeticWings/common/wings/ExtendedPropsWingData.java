package riskyken.cosmeticWings.common.wings;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import riskyken.cosmeticWings.common.network.PacketHandler;
import riskyken.cosmeticWings.common.network.message.MessageServerWingData;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

public class ExtendedPropsWingData implements IExtendedEntityProperties {
    
    public static final String TAG_EXT_PROP_NAME = "playerWingData";
    
    private EntityPlayer player;
    private WingData wingData;
    
    public ExtendedPropsWingData(EntityPlayer player) {
        this.player = player;
        this.wingData = new WingData();
    }
    
    public static final void register(EntityPlayer player) {
        player.registerExtendedProperties(ExtendedPropsWingData.TAG_EXT_PROP_NAME, new ExtendedPropsWingData(player));
    }
    
    public static final ExtendedPropsWingData get(EntityPlayer player) {
        return (ExtendedPropsWingData) player.getExtendedProperties(TAG_EXT_PROP_NAME);
    }
    
    @Override
    public void saveNBTData(NBTTagCompound compound) {
        wingData.saveNBTData(compound);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        wingData.loadNBTData(compound);
    }

    public WingData getWingData() {
        return wingData;
    }
    
    @Override
    public void init(Entity entity, World world) {}
    
    public void sendWingDataToPlayer(EntityPlayerMP targetPlayer) {
        PacketHandler.networkWrapper.sendTo(new MessageServerWingData(player.getUniqueID(), wingData), targetPlayer);
    }
    
    public void sendWingDataToAllAround() {
        TargetPoint p = new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 512);
        PacketHandler.networkWrapper.sendToAllAround(new MessageServerWingData(player.getUniqueID(), wingData), p);
    }
    
    public void updateWingData(WingData wingData) {
        this.wingData = wingData;
        sendWingDataToAllAround();
    }
}
