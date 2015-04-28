package riskyken.cosmeticWings.common.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import riskyken.cosmeticWings.common.wings.ExPropsWingsData;
import riskyken.cosmeticWings.common.wings.WingsData;
import riskyken.cosmeticWings.utils.ModLogger;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
/**
 * Handles sending and receiving wing data and extended wing data properties creation.
 * 
 * Jibril approves of wing handlers.
 * 
 * @author RiskyKen
 *
 */
public final class WingDataHandler {

    public static WingDataHandler INSTANCE;
    
    public static void init() {
        if (INSTANCE == null) {
            INSTANCE = new WingDataHandler();
        }
    }
    
    public WingDataHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.target instanceof EntityPlayerMP & getEffectiveSide() == Side.SERVER) {
            //A player walked into tracking range of another. Send them the players wing data.
            //Note: Is called for each player.
            EntityPlayerMP targetPlayer = (EntityPlayerMP) event.target;
            ExPropsWingsData.get((EntityPlayer) event.entity).sendWingDataToPlayer(targetPlayer);
        }
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {
        if (event.entity instanceof EntityPlayer & getEffectiveSide() == Side.SERVER) {
            EntityPlayer player = (EntityPlayer) event.entity;
            ModLogger.log(player.getClass().getName());
            //A new player join that has no extended wing data properties. Lets make some for them.
            ExPropsWingsData.register(player);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayerMP) {
            //A player has joined. Send them their own wing data.
            ExPropsWingsData.get((EntityPlayer) event.entity).sendWingDataToPlayer((EntityPlayerMP) event.entity);
        }
    }

    @SubscribeEvent
    public void onPlayerCloneEvent(PlayerEvent.Clone event) {
        //Copy a players wing data over when they die/change dimension.
        NBTTagCompound compound = new NBTTagCompound();
        ExPropsWingsData oldProps = ExPropsWingsData.get(event.original);
        ExPropsWingsData newProps = ExPropsWingsData.get(event.entityPlayer);
        oldProps.saveNBTData(compound);
        newProps.loadNBTData(compound);
    }
    
    private Side getEffectiveSide() {
        return FMLCommonHandler.instance().getEffectiveSide();
    }

    public static void gotWingDataFromPlayer(EntityPlayerMP player, WingsData wingData) {
        //Got updated wing data from a client. Send it to their extended wing data properties.
        ExPropsWingsData extendedPropsWingData = ExPropsWingsData.get(player);
        extendedPropsWingData.updateWingData(wingData);
    }
}
