package riskyken.cosmeticWings.client.render;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.opengl.GL11;

import riskyken.cosmeticWings.client.render.wings.IWingRenderer;
import riskyken.cosmeticWings.client.render.wings.RenderWingsAngel;
import riskyken.cosmeticWings.client.render.wings.RenderWingsBlack;
import riskyken.cosmeticWings.client.render.wings.RenderWingsFlandre;
import riskyken.cosmeticWings.client.render.wings.RenderWingsKuroyukihime;
import riskyken.cosmeticWings.client.render.wings.RenderWingsMech;
import riskyken.cosmeticWings.client.render.wings.RenderWingsMetal;
import riskyken.cosmeticWings.client.render.wings.RenderWingsShana;
import riskyken.cosmeticWings.client.render.wings.RenderWingsSmallMech;
import riskyken.cosmeticWings.client.wings.ClientWingCache;
import riskyken.cosmeticWings.common.wings.WingData;
import riskyken.cosmeticWings.common.wings.WingType;
import riskyken.cosmeticWings.utils.ModLogger;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.Type;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class WingRenderManager {

    public static WingRenderManager INSTANCE;
    
    private Queue<WingRenderQueueItem> wingRenderQueue;
    private final HashMap<WingType, IWingRenderer> wingRendererMap;
    
    public static void init() {
        INSTANCE = new WingRenderManager();
    }
    
    public WingRenderManager() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        wingRenderQueue = new ArrayDeque();
        wingRendererMap = new HashMap<WingType, IWingRenderer>();
        loadWingRenderers();
    }
    
    private void loadWingRenderers() {
        registerRendererForWingType(WingType.BLACK, RenderWingsBlack.class);
        registerRendererForWingType(WingType.ANGEL, RenderWingsAngel.class);
        registerRendererForWingType(WingType.SHANA, RenderWingsShana.class);
        registerRendererForWingType(WingType.FLANDRE, RenderWingsFlandre.class);
        registerRendererForWingType(WingType.METAL, RenderWingsMetal.class);
        registerRendererForWingType(WingType.KUROYUKIHIME, RenderWingsKuroyukihime.class);
        registerRendererForWingType(WingType.SMALL_MECH, RenderWingsSmallMech.class);
        registerRendererForWingType(WingType.MECH, RenderWingsMech.class);
    }
    
    private void registerRendererForWingType(WingType wingType, Class<? extends IWingRenderer> renderClass) {
        try {
            ModLogger.log("Registering wing renderer " + renderClass.getSimpleName() + " for " + wingType.name());
            wingRendererMap.put(wingType, renderClass.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(15728880);
        LightingHelper.disableLighting();
        for(WingRenderQueueItem wingRenderItem : wingRenderQueue) {
            wingRenderItem.Render(event.partialTicks);
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
        
        WingData wingData = ClientWingCache.INSTANCE.getPlayerWingData(player.getUniqueID());
        if (wingData==null) {
            return;
        }
        
        Minecraft mc = Minecraft.getMinecraft();
        boolean renderingInGui = mc.currentScreen != null && mc.currentScreen.getClass() != GuiChat.class;
        
        if (renderingInGui) {
            if (!player.equals(mc.thePlayer)) {
                renderingInGui = false;
            }
        }
        
        if (!wingRendererMap.containsKey(wingData.wingType)) {
            return;
        }
        
        IWingRenderer wingRenderer = wingRendererMap.get(wingData.wingType);
        
        if (wingData.wingType.postRender) {
            if (player.getUniqueID() != Minecraft.getMinecraft().thePlayer.getUniqueID()) {
                wingRenderQueue.add(new WingRenderQueueItem(ev.entityPlayer, wingData, wingRenderer));
            } else {
                if (!renderingInGui) {
                    wingRenderQueue.add(new WingRenderQueueItem(ev.entityPlayer, wingData, wingRenderer));
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
        
        wingRenderer.render(player, wingData, ev.partialRenderTick);
        if (wingData.wingType.postRender && renderingInGui) {
            LightingHelper.disableLighting();
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            wingRenderer.postRender(player, wingData, ev.partialRenderTick);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);
            LightingHelper.enableLighting();
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

        WingData wingData = ClientWingCache.INSTANCE.getPlayerWingData(player.getUniqueID());
        if (wingData==null) {
            return;
        }
        if (wingData.particleSpawnRate == 0F) {
            return;
        }
        
        if (!wingRendererMap.containsKey(wingData.wingType)) {
            return;
        }
        IWingRenderer wingRenderer = wingRendererMap.get(wingData.wingType);
        wingRenderer.onTick(player, wingData);
    }
}
