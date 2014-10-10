package riskyken.cosmeticWings.client.wings;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import riskyken.cosmeticWings.client.model.wings.ModelBigWings;
import riskyken.cosmeticWings.client.model.wings.ModelExtraBigWings;
import riskyken.cosmeticWings.client.model.wings.ModelMetalWings;
import riskyken.cosmeticWings.common.wings.WingData;
import riskyken.cosmeticWings.common.wings.WingType;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.Type;
import cpw.mods.fml.relauncher.Side;

public final class WingRenderManager {

    private static HashMap<UUID, WingData> playerWingData;
    
    private static ModelBigWings bigWings = new ModelBigWings();
    private static ModelExtraBigWings extraBigWings = new ModelExtraBigWings();
    private static ModelMetalWings metalWings = new ModelMetalWings();
    
    public WingRenderManager() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        playerWingData = new HashMap<UUID, WingData>();
    }
    
    public void setWingData(UUID playerId, WingData wingData) {
        if (playerWingData.containsKey(playerId)) {
            playerWingData.remove(playerId);
        }
        
        if (wingData.wingType != WingType.NONE) {
            playerWingData.put(playerId, wingData);
        }
    }
    
    @SubscribeEvent
    public void onRender(RenderPlayerEvent.SetArmorModel ev){
        EntityPlayer player = ev.entityPlayer;
        if (player.isInvisible()) { return; }
        
        if (!playerWingData.containsKey(player.getUniqueID())) {
            return;
        }
        WingData wingData = playerWingData.get(player.getUniqueID());
        
        switch (wingData.wingType) {
        case BLACK:
            bigWings.render(ev.entityPlayer, ev.renderer, 0);
            break;
        case WHITE:
            bigWings.render(ev.entityPlayer, ev.renderer, 1);
            break;
        case SHANA:
            bigWings.render(ev.entityPlayer, ev.renderer, 2);
            break;
        case FLANDRE:
            extraBigWings.render(ev.entityPlayer, ev.renderer);
            break;
        case METAL:
            metalWings.render(ev.entityPlayer, ev.renderer);
            break;
        default:
            break;
        }
    }
    
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == Side.CLIENT & event.type == Type.PLAYER & event.phase == Phase.END) {
            onPlayerTick(event.player);
        }
    }
    
    public void onPlayerTick(EntityPlayer player) {
        if (player.isInvisible()) { return; }
        
        if (!playerWingData.containsKey(player.getUniqueID())) {
            return;
        }
        WingData wingData = playerWingData.get(player.getUniqueID());
        
        switch (wingData.wingType) {
        case BLACK:
            bigWings.onTick(player, 0);
            break;
        case WHITE:
            bigWings.onTick(player, 1);
            break;
        case SHANA:
            bigWings.onTick(player, 2);
            break;
        default:
            break;
        }
    }
}
