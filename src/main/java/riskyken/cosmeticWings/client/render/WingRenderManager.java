package riskyken.cosmeticWings.client.render;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.opengl.GL11;

import riskyken.cosmeticWings.client.model.wings.ModelBigWings;
import riskyken.cosmeticWings.client.model.wings.ModelExtraBigWings;
import riskyken.cosmeticWings.client.model.wings.ModelKuroyukihimeWings;
import riskyken.cosmeticWings.client.model.wings.ModelMechWings;
import riskyken.cosmeticWings.client.model.wings.ModelMetalWings;
import riskyken.cosmeticWings.client.model.wings.ModelSmallMechWings;
import riskyken.cosmeticWings.common.wings.WingData;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.Type;
import cpw.mods.fml.relauncher.Side;

public final class WingRenderManager {

    public static WingRenderManager INSTANCE;
    public static boolean wingGuiOpen = false;
    
    /** Holds UUID's of players as a key and their wing data. */
    //TODO Removed players when they go offline/leave tracking range.
    private final  HashMap<UUID, WingData> playerWingData;
    
    private Queue<WingRenderQueueItem> wingRenderQueue;

    /** Wing models. */
    public final ModelBigWings bigWings = new ModelBigWings();
    public final ModelExtraBigWings extraBigWings = new ModelExtraBigWings();
    public final ModelMetalWings metalWings = new ModelMetalWings();
    public final ModelKuroyukihimeWings kuroyukihimeWings = new ModelKuroyukihimeWings();
    public final ModelSmallMechWings smallMechWings  = new ModelSmallMechWings();
    public final ModelMechWings mechWings  = new ModelMechWings();
    
    public static void init() {
        INSTANCE = new WingRenderManager();
    }
    
    public WingRenderManager() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        playerWingData = new HashMap<UUID, WingData>();
        wingRenderQueue = new ArrayDeque();
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
    public void onRender(RenderWorldLastEvent event) {
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        LightingHelper.disableLighting();
        for(WingRenderQueueItem wingRenderItem : wingRenderQueue) {
            wingRenderItem.Render(this, event.partialTicks);
        }
        LightingHelper.enableLighting();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(true);
        wingRenderQueue.clear();
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
        
        if (wingData.wingType.postRender) {
            if (player.getUniqueID() != Minecraft.getMinecraft().thePlayer.getUniqueID()) {
                wingRenderQueue.add(new WingRenderQueueItem(ev.entityPlayer, wingData));
            } else {
                if (!wingGuiOpen) {
                    wingRenderQueue.add(new WingRenderQueueItem(ev.entityPlayer, wingData));
                }
            }
        }
        
        
        GL11.glPushMatrix();
        if (player.isSneaking()) {
            GL11.glRotatef(28.6F, 1, 0, 0);
        }
        GL11.glTranslatef(0, (wingData.wingScale - 1F) * 0.10F, (1F - wingData.wingScale) * 0.0625F);
        
        GL11.glTranslatef(0, 6 * 0.0625F, 0F);
        GL11.glTranslatef(0, -wingData.heightOffset * 8 * 0.0625F, 0F);
        
        switch (wingData.wingType) {
        case BLACK:
            bigWings.render(ev.entityPlayer, ev.renderer, 0, wingData);
            break;
        case ANGEL:
            bigWings.render(ev.entityPlayer, ev.renderer, 1, wingData);
            break;
        case SHANA:
            bigWings.render(ev.entityPlayer, ev.renderer, 2, wingData);
            break;
        case FLANDRE:
            extraBigWings.render(ev.entityPlayer, ev.renderer, wingData);
            break;
        case METAL:
            metalWings.render(ev.entityPlayer, ev.renderer, wingData);
            break;
        case KUROYUKIHIME:
            kuroyukihimeWings.render(ev.entityPlayer, ev.renderer, wingData);
            break;
        case SMALL_MECH:
            smallMechWings.render(ev.entityPlayer, ev.renderer, wingData);
            break;
        case MECH:
            mechWings.render(ev.entityPlayer, false, wingData);
            if (wingGuiOpen) {
                LightingHelper.disableLighting();
                GL11.glDepthMask(false);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_CULL_FACE);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                mechWings.render(ev.entityPlayer, true, wingData);
                GL11.glEnable(GL11.GL_CULL_FACE);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDepthMask(true);
                LightingHelper.enableLighting();
            }
            break;
        default:
            break; 
        }
        GL11.glPopMatrix();
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == Side.CLIENT & event.type == Type.PLAYER & event.phase == Phase.END) {
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
            bigWings.onTick(player, 0, wingData);
            break;
        case ANGEL:
            bigWings.onTick(player, 1, wingData);
            break;
        case SHANA:
            bigWings.onTick(player, 2, wingData);
            break;
        case KUROYUKIHIME:
            //kuroyukihimeWings.onTick(player, wingData.wingScale);
        default:
            break;
        }
    }
}
