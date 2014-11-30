package riskyken.cosmeticWings.common.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import riskyken.cosmeticWings.common.wings.ExtendedPropsWingData;
import riskyken.cosmeticWings.common.wings.WingData;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
/**
 * Handles sending and receiving wing data and extended wing data properties creation.
 * @author RiskyKen
 *
 */
public final class WingDataHandler {

    public WingDataHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.target instanceof EntityPlayerMP) {
            //A player walked into tracking range of another. Send them the players wing data.
            //Note: Is called for each player.
            EntityPlayerMP targetPlayer = (EntityPlayerMP) event.target;
            ExtendedPropsWingData.get((EntityPlayer) event.entity).sendWingDataToPlayer(targetPlayer);
        }
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {
        if (event.entity instanceof EntityPlayer && ExtendedPropsWingData.get((EntityPlayer) event.entity) == null) {
            //A new player join that has no extended wing data properties. Lets make some for them.
            ExtendedPropsWingData.register((EntityPlayer) event.entity);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayerMP) {
            //A player has joined. Send them their own wing data.
            ExtendedPropsWingData.get((EntityPlayer) event.entity).sendWingDataToPlayer((EntityPlayerMP) event.entity);
        }
    }

    @SubscribeEvent
    public void onPlayerCloneEvent(PlayerEvent.Clone event) {
        //Copy a players wing data over when they die/change dimension.
        NBTTagCompound compound = new NBTTagCompound();
        ExtendedPropsWingData oldProps = ExtendedPropsWingData.get(event.original);
        ExtendedPropsWingData newProps = ExtendedPropsWingData.get(event.entityPlayer);
        oldProps.saveNBTData(compound);
        newProps.loadNBTData(compound);
    }

    public static void gotWingDataFromPlayer(EntityPlayerMP player, WingData wingData) {
        //Got updated wing data from a client. Send it to their extended wing data properties.
        ExtendedPropsWingData extendedPropsWingData = ExtendedPropsWingData.get(player);
        extendedPropsWingData.updateWingData(wingData);
    }
}
