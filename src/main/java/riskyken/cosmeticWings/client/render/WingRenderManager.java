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
import riskyken.cosmeticWings.client.wings.ClientWingsCache;
import riskyken.cosmeticWings.common.wings.IWings;
import riskyken.cosmeticWings.common.wings.WingsData;
import riskyken.cosmeticWings.common.wings.WingsRegistry;
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
    
    private WingsRegistry wingsRegistry;
    private Queue<WingRenderQueueItem> wingRenderQueue;
    private final HashMap<IWings, IWingRenderer> wingRendererMap;
    
    public static void init() {
        INSTANCE = new WingRenderManager();
    }
    
    public WingRenderManager() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
        wingRenderQueue = new ArrayDeque();
        wingRendererMap = new HashMap<IWings, IWingRenderer>();
        wingsRegistry = WingsRegistry.INSTANCE;
        loadWingRenderers();
    }
    
    private void loadWingRenderers() {
        for (int i = 0; i < wingsRegistry.getSize(); i++) {
            IWings wings = wingsRegistry.getWingsForId(i);
            if (wings.getRendererClass() != null) {
                registerRendererForWings(wings, wings.getRendererClass());
            }
        }
    }
    
    private void registerRendererForWings(IWings wings, Class<? extends IWingRenderer> renderClass) {
        try {
            ModLogger.log("Registering wing renderer " + renderClass.getSimpleName() + " for " + wings.getName());
            wingRendererMap.put(wings, renderClass.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
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
        
        WingsData wingsData = ClientWingsCache.INSTANCE.getPlayerWingsData(player.getUniqueID());
        if (wingsData==null) {
            return;
        }
        
        Minecraft mc = Minecraft.getMinecraft();
        boolean renderingInGui = mc.currentScreen != null && mc.currentScreen.getClass() != GuiChat.class;
        if (renderingInGui) {
            if (!player.equals(mc.thePlayer)) {
                renderingInGui = false;
            }
        }
        
        IWings wings = wingsRegistry.getWingsForId(wingsData.wingId);
        if (wings == null) {
            return;
        }
        
        if (!wingRendererMap.containsKey(wings)) {
            return;
        }
        IWingRenderer wingRenderer = wingRendererMap.get(wings);
        
        GL11.glPushMatrix();
        if (player.isSneaking()) {
            GL11.glRotatef(28.6F, 1, 0, 0);
        }
        GL11.glTranslatef(0, (wingsData.wingScale - 1F) * 0.10F, (1F - wingsData.wingScale) * 0.0625F);
        
        GL11.glTranslatef(0, 6 * 0.0625F, 0F);
        GL11.glTranslatef(0, -wingsData.heightOffset * 8 * 0.0625F, 0F);
        
        for (int layer = 0; layer < wings.getNumberOfRenderLayers(); layer++) {
            if (!wings.isNomalRender(layer)) {
                if (player.getUniqueID() != Minecraft.getMinecraft().thePlayer.getUniqueID()) {
                    wingRenderQueue.add(new WingRenderQueueItem(ev.entityPlayer, wingsData, wingRenderer, wings));
                } else {
                    if (!renderingInGui) {
                        wingRenderQueue.add(new WingRenderQueueItem(ev.entityPlayer, wingsData, wingRenderer, wings));
                    }
                }
            }
            
            if (wings.isGlowing(layer)) {
                LightingHelper.disableLighting();
            }
            if (!wings.canRecolour(layer)) {
                GL11.glColor3f(1F, 1F, 1F);
            }
            if (wings.isNomalRender(layer)) {
                wingRenderer.render(player, wingsData, layer, wings, ev.partialRenderTick);
            }
            
            if (!wings.isNomalRender(layer) && renderingInGui) {
                renderInGUI(player, layer, wingsData, wingRenderer, wings, ev.partialRenderTick);
            }
            if (wings.isGlowing(layer)) {
                LightingHelper.enableLighting();
            }
        }

        GL11.glPopMatrix();
    }
    
    private void renderInGUI(EntityPlayer player, int layer, WingsData wingsData, IWingRenderer wingRenderer, IWings wings, float partialRenderTick) {
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        wingRenderer.postRender(player, wingsData, layer, wings, partialRenderTick);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
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

        WingsData wingsData = ClientWingsCache.INSTANCE.getPlayerWingsData(player.getUniqueID());
        if (wingsData==null) {
            return;
        }
        if (wingsData.particleSpawnRate == 0F) {
            return;
        }
        
        IWings wings = wingsRegistry.getWingsForId(wingsData.wingId);
        if (wings == null) {
            return;
        }
        
        if (!wingRendererMap.containsKey(wings)) {
            return;
        }
        IWingRenderer wingRenderer = wingRendererMap.get(wings);
        
        wingRenderer.onTick(player, wingsData, wings);
    }
}
