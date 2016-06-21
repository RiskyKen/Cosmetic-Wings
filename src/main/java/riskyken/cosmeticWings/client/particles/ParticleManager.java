package riskyken.cosmeticWings.client.particles;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import riskyken.cosmeticWings.client.abstraction.IRenderBuffer;
import riskyken.cosmeticWings.client.abstraction.RenderBridge;

@SideOnly(Side.CLIENT)
public class ParticleManager {

	public static ParticleManager INSTANCE;
	private Minecraft mc;
	
    public static void init() {
        INSTANCE = new ParticleManager();
    }
	
	public ParticleManager() {
	    MinecraftForge.EVENT_BUS.register(this);
	    mc = Minecraft.getMinecraft();
    }
	
	public void spawnParticle(World world, Particle particle) {
		spawnParticle(world, particle, false);
	}
	
	public void spawnParticle(World world, Particle particle, boolean must) {
		if (mc != null && mc.getRenderViewEntity() != null && mc.effectRenderer != null) {

			if (!must) {
				int particleSetting = mc.gameSettings.particleSetting;
				
				if (particleSetting == 2 || (particleSetting == 1 && world.rand.nextInt(3) == 0)) {
					return;
				}
				/*
				double distanceX = mc.getRenderViewEntity().posX - particle.posX;
				double distanceY = mc.getRenderViewEntity().posY - particle.posY;
				double distanceZ = mc.getRenderViewEntity().posZ - particle.posZ;
				int maxDistance = 16;
				
				if (distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ > maxDistance * maxDistance) {
					return;
				}
				*/
			}
			
			if (particle != null) {
				mc.effectRenderer.addEffect(particle);
			}
		}
	}
	
    @SubscribeEvent
    public void onRenderWorldLastEvent(RenderWorldLastEvent event) {
        mc.entityRenderer.enableLightmap();
        RenderHelper.disableStandardItemLighting();
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
        IRenderBuffer renderBuffer = RenderBridge.INSTANCE;
        mc.mcProfiler.startSection("wingParticles");
        //EntityButterflyFx.renderQueue(renderBuffer);
        //EntityFeatherFx.renderQueue(renderBuffer);
        mc.mcProfiler.endSection();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        
        mc.entityRenderer.disableLightmap();
    }
}
