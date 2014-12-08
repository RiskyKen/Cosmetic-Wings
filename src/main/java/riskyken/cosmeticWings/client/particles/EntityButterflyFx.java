package riskyken.cosmeticWings.client.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
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
        
        this.motionX = (rand.nextFloat() - 0.5) * 0.05;
        this.motionY = (rand.nextFloat() - 0.5) * 0.02;
        this.motionZ = (rand.nextFloat() - 0.5) * 0.05;
        this.particleScale = (rand.nextFloat() + 0.5F);
        
        this.target = target;
    }
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }
        
        this.motionY -= 0.04D * (double)this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.990D;
        this.motionY *= 0.990D;
        this.motionZ *= 0.990D;
        
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
        
        int i = worldObj.getLightBrightnessForSkyBlocks(
                MathHelper.floor_double(posX),
                MathHelper.floor_double(posY),
                MathHelper.floor_double(posZ),
                0);
        int j = i % 65536;
        int k = i / 65536;
        
        tessellator.startDrawingQuads();

        tessellator.setBrightness(getBrightnessForRender(0));
        
        int textureNumber = this.particleAge % 4;
        
        if (textureNumber < 2) {
            Minecraft.getMinecraft().renderEngine.bindTexture(bufferflyTexture[0]);
        } else {
            Minecraft.getMinecraft().renderEngine.bindTexture(bufferflyTexture[1]);
        }
        
        

        
        float f6 = 0;
        float f7 = 1;
        float f8 = 0;
        float f9 = 1;
        
        float f10 = 0.1F * this.particleScale;

        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)f0 - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)f0 - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)f0 - interpPosZ);
        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        tessellator.addVertexWithUV((double)(f11 - f1 * f10 - f4 * f10), (double)(f12 - f2 * f10), (double)(f13 - f3 * f10 - f5 * f10), (double)f7, (double)f9);
        tessellator.addVertexWithUV((double)(f11 - f1 * f10 + f4 * f10), (double)(f12 + f2 * f10), (double)(f13 - f3 * f10 + f5 * f10), (double)f7, (double)f8);
        tessellator.addVertexWithUV((double)(f11 + f1 * f10 + f4 * f10), (double)(f12 + f2 * f10), (double)(f13 + f3 * f10 + f5 * f10), (double)f6, (double)f8);
        tessellator.addVertexWithUV((double)(f11 + f1 * f10 - f4 * f10), (double)(f12 - f2 * f10), (double)(f13 + f3 * f10 - f5 * f10), (double)f6, (double)f9);
        
        tessellator.draw();
    }
}
