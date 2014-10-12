package riskyken.cosmeticWings.client.wings;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.opengl.GL11;

import riskyken.cosmeticWings.client.model.wings.ModelBigWings;
import riskyken.cosmeticWings.client.model.wings.ModelExtraBigWings;
import riskyken.cosmeticWings.client.model.wings.ModelKuroyukihimeWings;
import riskyken.cosmeticWings.client.model.wings.ModelMetalWings;
import riskyken.cosmeticWings.common.wings.WingData;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.Type;
import cpw.mods.fml.relauncher.Side;

public final class WingRenderManager {

    public static WingRenderManager INSTANCE;
    
    private final  HashMap<UUID, WingData> playerWingData;

    private final ModelBigWings bigWings = new ModelBigWings();
    private final ModelExtraBigWings extraBigWings = new ModelExtraBigWings();
    private final ModelMetalWings metalWings = new ModelMetalWings();
    private final ModelKuroyukihimeWings kuroyukihimeWings = new ModelKuroyukihimeWings();

    public static void init() {
        INSTANCE = new WingRenderManager();
    }
    
    public WingRenderManager() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
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

    @SubscribeEvent
    public void onRender(RenderPlayerEvent.SetArmorModel ev) {
        EntityPlayer player = ev.entityPlayer;
        if (player.isInvisible()) {
            return;
        }
        
        WingData wingData = getPlayerWingData(player.getUniqueID());
        if (wingData==null) {
            return;
        }
        GL11.glPushMatrix();
        
        GL11.glTranslatef(0, (1F - wingData.wingScale) * 0.20F, (1F - wingData.wingScale) * 0.12F);
        GL11.glScalef(wingData.wingScale, wingData.wingScale, wingData.wingScale);
        
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
        case KUROYUKIHIME:
            GL11.glTranslatef(0, (1F - wingData.wingScale) * 0.60F, 0);
            kuroyukihimeWings.render(ev.entityPlayer, ev.renderer);
            break;
        default:
            break;
        }
        GL11.glPopMatrix();
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == Side.CLIENT & event.type == Type.PLAYER
                & event.phase == Phase.END) {
            onPlayerTick(event.player);
        }
    }

    public void onPlayerTick(EntityPlayer player) {
        if (player.isInvisible()) {
            return;
        }

        WingData wingData = getPlayerWingData(player.getUniqueID());
        if (wingData==null) {
            return;
        }
        
        if (!wingData.spawnParticles) {
            return;
        }

        switch (wingData.wingType) {
        case BLACK:
            bigWings.onTick(player, 0, wingData.wingScale);
            break;
        case WHITE:
            bigWings.onTick(player, 1, wingData.wingScale);
            break;
        case SHANA:
            bigWings.onTick(player, 2, wingData.wingScale);
            break;
        default:
            break;
        }
    }
}
