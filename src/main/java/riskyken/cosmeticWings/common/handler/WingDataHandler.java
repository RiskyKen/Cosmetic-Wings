package riskyken.cosmeticWings.common.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import riskyken.cosmeticWings.common.capability.IWingCapability;
import riskyken.cosmeticWings.common.capability.WingProvider;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.common.network.PacketHandler;
import riskyken.cosmeticWings.common.network.message.MessageServerWingsData;
import riskyken.cosmeticWings.common.wings.ExPropsWingsData;
import riskyken.cosmeticWings.common.wings.WingsData;
import riskyken.cosmeticWings.utils.ModLogger;
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
    public static ResourceLocation key = new ResourceLocation(LibModInfo.ID, "playerWingData");
    
    @CapabilityInject(IWingCapability.class)
    private static final Capability<IWingCapability> WING_CAP = null;
    
    public static void init() {
        if (INSTANCE == null) {
            INSTANCE = new WingDataHandler();
        }
    }
    
    public WingDataHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onAttachCapabilities(AttachCapabilitiesEvent.Entity event) {
        if (event.getEntity() instanceof EntityPlayer & getEffectiveSide() == Side.SERVER) {
            event.addCapability(key, new WingProvider());
        }
    }
    
    @SubscribeEvent
    public void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getEntity() instanceof EntityPlayerMP & getEffectiveSide() == Side.SERVER) {
            //A player walked into tracking range of another. Send them the players wing data.
            //Note: Is called for each player.
            EntityPlayerMP targetPlayer = (EntityPlayerMP) event.getEntity();
            IWingCapability cap = event.getEntity().getCapability(WING_CAP, null);
            if (cap != null) {
                PacketHandler.networkWrapper.sendTo(new MessageServerWingsData(event.getEntity().getUniqueID(), cap.getWingData()), targetPlayer);
            }
            //ExPropsWingsData.get((EntityPlayer) event.getEntity()).sendWingDataToPlayer(targetPlayer);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!event.getEntity().worldObj.isRemote && event.getEntity() instanceof EntityPlayerMP) {
            //A player has joined. Send them their own wing data.
            EntityPlayerMP targetPlayer = (EntityPlayerMP) event.getEntity();
            IWingCapability cap = targetPlayer.getCapability(WING_CAP, null);
            if (cap != null) {
                ModLogger.log("sending wing data to player");
                PacketHandler.networkWrapper.sendTo(new MessageServerWingsData(targetPlayer.getUniqueID(), cap.getWingData()), targetPlayer);
            } else {
                ModLogger.log("failed to get wing cap for player");
            }
            
            //ExPropsWingsData.get((EntityPlayer) event.getEntity()).sendWingDataToPlayer((EntityPlayerMP) event.getEntity());
        }
    }

    @SubscribeEvent
    public void onPlayerCloneEvent(PlayerEvent.Clone event) {
        //Copy a players wing data over when they die/change dimension.
        if (event.isWasDeath()) {
            IWingCapability oldCap = event.getOriginal().getCapability(WING_CAP, null);
            IWingCapability newCap = event.getEntityPlayer().getCapability(WING_CAP, null);
            if (oldCap != null & newCap != null) {
                newCap.setWingsData(oldCap.getWingData());
            }
        }
    }
    
    private Side getEffectiveSide() {
        return FMLCommonHandler.instance().getEffectiveSide();
    }

    public static void gotWingDataFromPlayer(EntityPlayerMP player, WingsData wingData) {
        //Got updated wing data from a client. Send it to their extended wing data properties.
        IWingCapability cap = player.getCapability(WING_CAP, null);
        if (cap != null) {
            cap.setWingsData(wingData);
            TargetPoint p = new TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 512);
            PacketHandler.networkWrapper.sendToAllAround(new MessageServerWingsData(player.getUniqueID(), wingData), p);
        }
    }
}
