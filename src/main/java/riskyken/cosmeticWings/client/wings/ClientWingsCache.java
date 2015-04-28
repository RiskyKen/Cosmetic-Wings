package riskyken.cosmeticWings.client.wings;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import riskyken.cosmeticWings.common.wings.WingsData;
import riskyken.cosmeticWings.utils.ModLogger;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
/**
 * Stores the wing data for all player near the local player.
 * @author RiskyKen
 *
 */
@SideOnly(Side.CLIENT)
public class ClientWingsCache {
    
    public static ClientWingsCache INSTANCE;
    
    /** Holds UUID's of players as a key and their wing data. */
    private final  HashMap<UUID, WingsData> playerWingsData;
    
    public static void init() {
        INSTANCE = new ClientWingsCache();
    }
    
    public ClientWingsCache() {
        playerWingsData = new HashMap<UUID, WingsData>();
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }
    
    public void setWingsData(UUID playerId, WingsData wingsData) {
        if (playerWingsData.containsKey(playerId)) {
            playerWingsData.remove(playerId);
        }
        playerWingsData.put(playerId, wingsData);
    }
    
    public WingsData getPlayerWingsData(UUID playerId) {
        if (!playerWingsData.containsKey(playerId)) {
            return null;
        }
        return playerWingsData.get(playerId);
    }
    
    @SubscribeEvent
    public void onStopTracking(PlayerEvent.StopTracking event) {
        if (event.target instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.target;
            if (playerWingsData.containsKey(player.getUniqueID())) {
                playerWingsData.remove(player.getUniqueID());
            }
        }
    }
    
    @SubscribeEvent
    public void onClientDisconnect(ClientDisconnectionFromServerEvent event) {
        playerWingsData.clear();
    }
}
