package riskyken.cosmeticWings.client.wings;

import java.util.HashMap;
import java.util.UUID;

import riskyken.cosmeticWings.common.wings.WingData;
/**
 * Stores the wing data for all player near the local player.
 * @author RiskyKen
 *
 */
public class ClientWingCache {
    
    public static ClientWingCache INSTANCE;
    
    /** Holds UUID's of players as a key and their wing data. */
    //TODO Removed players when they go offline/leave tracking range.
    private final  HashMap<UUID, WingData> playerWingData;
    
    public static void init() {
        INSTANCE = new ClientWingCache();
    }
    
    public ClientWingCache() {
        playerWingData = new HashMap<UUID, WingData>();
    }
    
    public void setWingData(UUID playerId, WingData wingData) {
        if (playerWingData.containsKey(playerId)) {
            playerWingData.remove(playerId);
        }
        playerWingData.put(playerId, wingData);
    }
    
    public WingData getPlayerWingData(UUID playerId) {
        if (!playerWingData.containsKey(playerId)) {
            return null;
        }
        return playerWingData.get(playerId);
    }
}
