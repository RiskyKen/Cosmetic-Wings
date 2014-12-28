package riskyken.cosmeticWings.client.particles;

import java.util.ArrayDeque;
import java.util.Queue;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import riskyken.cosmeticWings.client.abstraction.IRenderBuffer;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.utils.UtilPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityButterflyFx extends EntityFX {

    private static final ResourceLocation bufferflyTexture = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/particles/butterflyParticles.png");
    private static Queue<EntityButterflyFx> butterflyRenderQueue = new ArrayDeque();;
    
    private EntityPlayer target;
    float f0;
    float f1;
    float f2;
    float f3;
    float f4;
    float f5;
    
    public EntityButterflyFx(World world, double x, double y, double z, float wingScale, EntityPlayer target) {
        super(world, x, y, z);
        this.particleMaxAge = 400;
        
        this.particleRed = 1F;
        this.particleGreen = 1F;
        this.particleBlue = 1F;
        this.particleAlpha = 0.75F;
        
        this.particleScale = (rand.nextFloat() + 0.5F);
        //this.particleGravity = 0;
        this.target = target;
        
        //this.motionX = (rand.nextFloat() - 0.5) * 0.05;
        //this.motionY = (rand.nextFloat() - 0.5) * 0.02;
        //this.motionZ = (rand.nextFloat() - 0.5) * 0.05;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        double targetX = target.posX;
        double targetY = target.posY;
        double targetZ = target.posZ;
        
        if (!UtilPlayer.isLocalPlayer(target)) {
            targetY += 1.6D;
        }
        
        double maxSpeed = 0.2;
        double minRange = 3;
        
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        
        if (posX + minRange < targetX) {
            this.motionX += 0.01F;
            this.motionX += (rand.nextFloat() - 0.5) * 0.05;
        } else if (posX - minRange > targetX) {
            this.motionX -= 0.01F;
            this.motionX -= (rand.nextFloat() - 0.5) * 0.05;
        } else {
            motionX *= 0.9D;
        }
        
        if (posY + 1 < targetY) {
            this.motionY += 0.01F;
            this.motionY += (rand.nextFloat() - 0.5) * 0.05;
        } else if (posY  - 1> targetY) {
            this.motionY -= 0.01F;
            this.motionY -= (rand.nextFloat() - 0.5) * 0.05;
        } else {
            motionY *= 0.9D;
        }
        
        if (posZ + minRange < targetZ) {
            this.motionZ += 0.01F;
            this.motionZ += (rand.nextFloat() - 0.5) * 0.05;
        } else if (posZ - minRange > targetZ) {
            this.motionZ -= 0.01F;
            this.motionZ -= (rand.nextFloat() - 0.5) * 0.05;
        } else {
            motionZ *= 0.9D;
        }
        
        this.motionX += (rand.nextFloat() - 0.5) * 0.02;
        this.motionY += (rand.nextFloat() - 0.5) * 0.02;
        this.motionZ += (rand.nextFloat() - 0.5) * 0.02;
        
        if (motionX > maxSpeed) {
            motionX = maxSpeed; 
        }
        
        if (motionX < -maxSpeed) {
            motionX = -maxSpeed; 
        }
        
        if (motionY > maxSpeed) {
            motionY = maxSpeed; 
        }
        
        if (motionY < -maxSpeed) {
            motionY = -maxSpeed; 
        }
        
        if (motionZ > maxSpeed) {
            motionZ = maxSpeed; 
        }
        
        if (motionZ < -maxSpeed) {
            motionZ = -maxSpeed; 
        }
        
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        
        if (this.onGround) {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }

    @Override
    public void renderParticle(Tessellator tessellator, float f0, float f1, float f2, float f3, float f4, float f5) {
        this.f0 = f0;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
        this.f5 = f5;
        butterflyRenderQueue.add(this);
    }
    
    public static void renderQueue(IRenderBuffer renderBuffer) {
        renderBuffer.startDrawingQuads();
        Minecraft.getMinecraft().renderEngine.bindTexture(bufferflyTexture);
        for(EntityButterflyFx butterflyFx : butterflyRenderQueue) {
            butterflyFx.postRender(renderBuffer);
        }
        renderBuffer.draw();
        butterflyRenderQueue.clear();
    }
    
    public void postRender(IRenderBuffer renderBuffer) {
        renderBuffer.setBrightness(getBrightnessForRender(0));
        int textureNumber = this.particleAge % 4;
        if (textureNumber < 2) {
            this.particleTextureIndexX = 1;
        } else {
            this.particleTextureIndexX = 0;
        }
        double x1 = (float)this.particleTextureIndexX / 2.0F;
        double y1 = 0D;
        double x2 = x1 + 0.5D;
        double y2 = 0.5D;
        
        float scale = 0.1F * this.particleScale;

        float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)f0 - interpPosX);
        float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)f0 - interpPosY);
        float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)f0 - interpPosZ);
        renderBuffer.setColourRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        renderBuffer.addVertexWithUV((double)(x - f1 * scale - f4 * scale), (double)(y - f2 * scale), (double)(z - f3 * scale - f5 * scale), x2, y2);
        renderBuffer.addVertexWithUV((double)(x - f1 * scale + f4 * scale), (double)(y + f2 * scale), (double)(z - f3 * scale + f5 * scale), x2, y1);
        renderBuffer.addVertexWithUV((double)(x + f1 * scale + f4 * scale), (double)(y + f2 * scale), (double)(z + f3 * scale + f5 * scale), x1, y1);
        renderBuffer.addVertexWithUV((double)(x + f1 * scale - f4 * scale), (double)(y - f2 * scale), (double)(z + f3 * scale - f5 * scale), x1, y2);
    }
}
