package riskyken.cosmeticWings.common.wings;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public final class WingDataManager {
    
    public WingDataManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.target instanceof EntityPlayerMP) {
            EntityPlayerMP targetPlayer = (EntityPlayerMP) event.target;
            ExtendedPropsWingData.get((EntityPlayer) event.entity).sendWingDataToPlayer(targetPlayer);
        }
    }
    
    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {
        if (event.entity instanceof EntityPlayer && ExtendedPropsWingData.get((EntityPlayer) event.entity) == null) {
            ExtendedPropsWingData.register((EntityPlayer) event.entity);
        }
    }
    
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayerMP) {
            ExtendedPropsWingData.get((EntityPlayer) event.entity).sendWingDataToPlayer((EntityPlayerMP) event.entity);
        }
    }
    
    @SubscribeEvent
    public void onPlayerCloneEvent (PlayerEvent.Clone  event) {
        NBTTagCompound compound = new NBTTagCompound();
        ExtendedPropsWingData oldProps = ExtendedPropsWingData.get(event.original);
        ExtendedPropsWingData newProps = ExtendedPropsWingData.get(event.entityPlayer);
        oldProps.saveNBTData(compound);
        newProps.loadNBTData(compound);
    }

    public static void gotWingDataFromPlayer(EntityPlayerMP player, WingData wingData) {
        ExtendedPropsWingData extendedPropsWingData = ExtendedPropsWingData.get(player);
        extendedPropsWingData.updateWingData(wingData);
    }
}
