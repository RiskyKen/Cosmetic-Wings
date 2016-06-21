package riskyken.cosmeticWings.common.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.common.wings.ExPropsWingsData;
import riskyken.cosmeticWings.common.wings.WingsData;
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
    
    public static void init() {
        if (INSTANCE == null) {
            INSTANCE = new WingDataHandler();
        }
    }
    
    public WingDataHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    public void onAttachCapabilities(AttachCapabilitiesEvent.Entity event) {
        //event.addCapability(key, ExPropsWingsData.class);
    }
    
    @SubscribeEvent
    public void onStartTracking(PlayerEvent.StartTracking event) {
        if (event.getEntity() instanceof EntityPlayerMP & getEffectiveSide() == Side.SERVER) {
            //A player walked into tracking range of another. Send them the players wing data.
            //Note: Is called for each player.
            EntityPlayerMP targetPlayer = (EntityPlayerMP) event.getEntity();
            //ExPropsWingsData.get((EntityPlayer) event.getEntity()).sendWingDataToPlayer(targetPlayer);
        }
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {
        if (event.getEntity() instanceof EntityPlayer & getEffectiveSide() == Side.SERVER) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            //A new player join that has no extended wing data properties. Lets make some for them.
            //ExPropsWingsData.register(player);
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!event.getEntity().worldObj.isRemote && event.getEntity() instanceof EntityPlayerMP) {
            //A player has joined. Send them their own wing data.
            //ExPropsWingsData.get((EntityPlayer) event.getEntity()).sendWingDataToPlayer((EntityPlayerMP) event.getEntity());
        }
    }

    @SubscribeEvent
    public void onPlayerCloneEvent(PlayerEvent.Clone event) {
        //Copy a players wing data over when they die/change dimension.
        /*
        NBTTagCompound compound = new NBTTagCompound();
        ExPropsWingsData oldProps = ExPropsWingsData.get(event.getOriginal());
        ExPropsWingsData newProps = ExPropsWingsData.get(event.getEntityPlayer());
        oldProps.saveNBTData(compound);
        newProps.loadNBTData(compound);
        */
    }
    
    private Side getEffectiveSide() {
        return FMLCommonHandler.instance().getEffectiveSide();
    }

    public static void gotWingDataFromPlayer(EntityPlayerMP player, WingsData wingData) {
        //Got updated wing data from a client. Send it to their extended wing data properties.
        //ExPropsWingsData extendedPropsWingData = ExPropsWingsData.get(player);
        //extendedPropsWingData.updateWingData(wingData);
    }
}
