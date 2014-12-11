package riskyken.cosmeticWings.client.wings;

import java.util.HashMap;
import java.util.UUID;

import riskyken.cosmeticWings.common.wings.WingsData;
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
    //TODO Removed players when they go offline/leave tracking range.
    private final  HashMap<UUID, WingsData> playerWingsData;
    
    public static void init() {
        INSTANCE = new ClientWingsCache();
    }
    
    public ClientWingsCache() {
        playerWingsData = new HashMap<UUID, WingsData>();
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
}
