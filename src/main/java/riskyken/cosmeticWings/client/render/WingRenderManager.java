package riskyken.cosmeticWings.client.render;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;

import net.minecraft.client.Minecraft;
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
import riskyken.cosmeticWings.utils.UtilPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class WingRenderManager {

    public static WingRenderManager INSTANCE;
    
    /** Local reference to the wing registry. */
    private WingsRegistry wingsRegistry;
    
    /** Queue if items that need to be rendered after the world. */
    private Queue<WingRenderQueueItem> wingRenderQueue;
    
    /** Hash map that links wing types to their renderer classes. */
    private final HashMap<IWings, IWingRenderer> wingRendererMap;
    
    /** Is the shaders mod loaded? */
    private boolean shadersModLoaded;
    
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
        checkForShadersMod();
    }
    
    private void loadWingRenderers() {
        ModLogger.log("Registering wing renderers");
        ArrayList<IWings> wingTypes = wingsRegistry.getRegisteredWingTypes();
        for (int i = 0; i < wingTypes.size(); i++) {
            IWings wings = wingTypes.get(i);
            if (wings.getRendererClass() != null) {
                registerRendererForWings(wings, wings.getRendererClass());
            }
        }
    }
    
    private void registerRendererForWings(IWings wings, Class<? extends IWingRenderer> renderClass) {
        try {
            wingRendererMap.put(wings, renderClass.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    private void checkForShadersMod() {
        try {
            Class.forName("shadersmodcore.client.Shaders");
            ModLogger.log("Shaders mod support active");
            shadersModLoaded = true;
        } catch (Exception e) {
        }
    }
    
    @SubscribeEvent
    public void onRender(RenderPlayerEvent.Pre ev) {
        EntityPlayer player = ev.getEntityPlayer();
        
        if (player.isInvisible()) {
            //Don't render if they player is invisible.
            return;
        }
        
        //Get the wing data for the player we are rendering.
        WingsData wingsData = ClientWingsCache.INSTANCE.getPlayerWingsData(player.getUniqueID());
        wingsData = new WingsData();
        wingsData.wingType = WingsRegistry.INSTANCE.getWingsFormRegistryName("cosmeticWings:mech");
        if (wingsData==null || wingsData.wingType == null) {
            //This player has no wing data. :(
            return;
        }
        
        //Get the renderer for the players wings.
        IWingRenderer wingRenderer = wingRendererMap.get(wingsData.wingType);
        if (wingRenderer == null) {
            //The players wings have no renderer class. :O
            //This should never happen.
            return;
        }
        
        //This hacky mess is used to make post world renders show in GUI's.
        //Check id the local player has a GUI open.
        boolean renderingInGui = UtilPlayer.localPlayerHasGuiOpen();
        if (renderingInGui) {
            //Is the player we are rendering the local player?
            if (!UtilPlayer.isLocalPlayer(player)) {
                renderingInGui = false;
            }
        }
        
        GL11.glPushMatrix();
        

        
        float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * ev.getPartialRenderTick();
        
        GL11.glTranslatef(0, (wingsData.wingScale - 1F) * 0.10F, (1F - wingsData.wingScale) * 0.0625F);
        GL11.glTranslatef(0, 28 * 0.0625F, 0F);
        GL11.glTranslatef(0, -wingsData.heightOffset * 8 * 0.0625F, 0F);
        GL11.glScalef(1, -1, -1);
        GL11.glRotatef(yawOffset, 0, 1, 0);

        if (player.isSneaking()) {
            //Tilt the wings if the player is sneaking.
            GL11.glRotatef(28.6F, 1, 0, 0);
        }
        
        //Loop over each wing layer and render it.
        for (int layer = 0; layer < wingsData.wingType.getNumberOfRenderLayers(); layer++) {
            if (!wingsData.wingType.isNomalRender(layer)) {
                //This layer should be rendered post world.
                if (player.getUniqueID() != Minecraft.getMinecraft().thePlayer.getUniqueID()) {
                    //This is a remote player so just add the render to the queue.
                    wingRenderQueue.add(new WingRenderQueueItem(player, wingsData, wingRenderer));
                } else {
                    //If this is the local player make sure they don't have a GUI open.
                    if (!renderingInGui) {
                        wingRenderQueue.add(new WingRenderQueueItem(player, wingsData, wingRenderer));
                    }
                }
            }
            
            if (wingsData.wingType.isGlowing(layer)) {
                //If the layer should glow turn off the lighting.
                ModRenderHelper.disableLighting();
            }
            
            if (!wingsData.wingType.canRecolour(layer)) {
              //If the layer does not set the colour make sure it's reset for it.
                GL11.glColor3f(1F, 1F, 1F);
            }
            
            if (wingsData.wingType.isNomalRender(layer)) {
                //If it's a normal layer render it.
                wingRenderer.render(player, wingsData, layer, ev.getPartialRenderTick());
            }
            
            if (!wingsData.wingType.isNomalRender(layer) && renderingInGui) {
                //If this is a post world layer don't render it, unless this is the local
                //player and they have a GUI open.
                renderInGUI(player, layer, wingsData, wingRenderer, ev.getPartialRenderTick());
            }
            
            if (wingsData.wingType.isGlowing(layer)) {
                //Turn the lighting back on if it was a glowing layer.
                ModRenderHelper.enableLighting();
            }
        }

        GL11.glPopMatrix();
    }
    
    private void renderInGUI(EntityPlayer player, int layer, WingsData wingsData, IWingRenderer wingRenderer, float partialRenderTick) {
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        wingRenderer.postRender(player, wingsData, layer, partialRenderTick);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
    }
    
    @SubscribeEvent
    public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        //Tessellator tessellator = Tessellator.instance;
        //tessellator.setBrightness(15728880);
        ModRenderHelper.disableLighting();
        for(WingRenderQueueItem wingRenderItem : wingRenderQueue) {
            wingRenderItem.Render(event.getPartialTicks());
        }
        ModRenderHelper.enableLighting();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(true);
        wingRenderQueue.clear();
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
        
        if (wingsData.wingType == null) {
            return;
        }
        
        if (!wingRendererMap.containsKey(wingsData.wingType)) {
            return;
        }
        IWingRenderer wingRenderer = wingRendererMap.get(wingsData.wingType);
        
        wingRenderer.onTick(player, wingsData);
    }
}
