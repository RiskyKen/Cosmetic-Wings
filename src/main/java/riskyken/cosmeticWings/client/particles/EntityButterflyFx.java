package riskyken.cosmeticWings.client.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import riskyken.cosmeticWings.common.lib.LibModInfo;

public class EntityButterflyFx extends EntityFX {

    private static final ResourceLocation[] bufferflyTexture;
    
    static {
        bufferflyTexture = new ResourceLocation[2];
        bufferflyTexture[0] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/particles/kuroyukihime-butterfly.png");
        bufferflyTexture[1] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/particles/kuroyukihime-butterfly-flap.png");
    }
    
    private Entity target;
    float f0;
    float f1;
    float f2;
    float f3;
    float f4;
    float f5;
    
    public EntityButterflyFx(World world, double x, double y, double z, float wingScale, Entity target) {
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
        
        double maxSpeed = 0.2;
        double minRange = 3;
        
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        
        if (posX + minRange < target.posX) {
            this.motionX += 0.01F;
            this.motionX += (rand.nextFloat() - 0.5) * 0.05;
        } else if (posX - minRange > target.posX) {
            this.motionX -= 0.01F;
            this.motionX -= (rand.nextFloat() - 0.5) * 0.05;
        } else {
            motionX *= 0.9D;
        }
        
        if (posY + 1 < target.posY) {
            this.motionY += 0.01F;
            this.motionY += (rand.nextFloat() - 0.5) * 0.05;
        } else if (posY  - 1> target.posY) {
            this.motionY -= 0.01F;
            this.motionY -= (rand.nextFloat() - 0.5) * 0.05;
        } else {
            motionY *= 0.9D;
        }
        
        if (posZ + minRange < target.posZ) {
            this.motionZ += 0.01F;
            this.motionZ += (rand.nextFloat() - 0.5) * 0.05;
        } else if (posZ - minRange > target.posZ) {
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
        ParticleManager.INSTANCE.butterflyRenderQueue.add(this);
    }
    
    public void postRender(Tessellator tessellator) {
        tessellator.startDrawingQuads();
        tessellator.setBrightness(getBrightnessForRender(0));
        
        int textureNumber = this.particleAge % 4;
        if (textureNumber < 2) {
            Minecraft.getMinecraft().renderEngine.bindTexture(bufferflyTexture[0]);
        } else {
            Minecraft.getMinecraft().renderEngine.bindTexture(bufferflyTexture[1]);
        }
        
        float scale = 0.1F * this.particleScale;

        float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)f0 - interpPosX);
        float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)f0 - interpPosY);
        float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)f0 - interpPosZ);
        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        tessellator.addVertexWithUV((double)(x - f1 * scale - f4 * scale), (double)(y - f2 * scale), (double)(z - f3 * scale - f5 * scale), 1D, 1D);
        tessellator.addVertexWithUV((double)(x - f1 * scale + f4 * scale), (double)(y + f2 * scale), (double)(z - f3 * scale + f5 * scale), 1D, 0D);
        tessellator.addVertexWithUV((double)(x + f1 * scale + f4 * scale), (double)(y + f2 * scale), (double)(z + f3 * scale + f5 * scale), 0D, 0D);
        tessellator.addVertexWithUV((double)(x + f1 * scale - f4 * scale), (double)(y - f2 * scale), (double)(z + f3 * scale - f5 * scale), 0D, 1D);
        tessellator.draw();
    }
}
