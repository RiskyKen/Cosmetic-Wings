package riskyken.cosmeticWings.client.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class EntityButterflyFx extends EntityFX {

    private static final ResourceLocation particleTextures;
    private static final ResourceLocation[] bufferflyTexture;
    
    static {
        particleTextures = ReflectionHelper.getPrivateValue(EffectRenderer.class, null, "particleTextures", "field_110737_b", "b");
        bufferflyTexture = new ResourceLocation[2];
        bufferflyTexture[0] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/particles/kuroyukihime-butterfly.png");
        bufferflyTexture[1] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/particles/kuroyukihime-butterfly-flap.png");
    }
    
    private Entity target;
    
    public EntityButterflyFx(World world, double x, double y, double z, float wingScale, Entity target) {
        super(world, x, y, z);
        this.particleMaxAge = 400;
        
        this.particleRed = 1F;
        this.particleGreen = 1F;
        this.particleBlue = 1F;
        this.particleAlpha = 1F;
        
        this.motionX = (rand.nextFloat() - 0.5) * 0.05;
        this.motionZ = (rand.nextFloat() - 0.5) * 0.05;
        
        this.target = target;
    }

    @Override
    public void renderParticle(Tessellator tessellator, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
        
        tessellator.draw();
        //ModLogger.log(this.particleAge % 10);
        int textureNumber = this.particleAge % 6;
        
        if (textureNumber < 3) {
            Minecraft.getMinecraft().renderEngine.bindTexture(bufferflyTexture[0]);
        } else {
            Minecraft.getMinecraft().renderEngine.bindTexture(bufferflyTexture[1]);
        }
        tessellator.startDrawingQuads();
        tessellator.setBrightness(getBrightnessForRender(0));
        
        float f6 = 0;
        float f7 = 1;
        float f8 = 0;
        float f9 = 1;
        
        float f10 = 0.1F * this.particleScale;

        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)p_70539_2_ - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)p_70539_2_ - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)p_70539_2_ - interpPosZ);
        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        tessellator.addVertexWithUV((double)(f11 - p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - p_70539_4_ * f10), (double)(f13 - p_70539_5_ * f10 - p_70539_7_ * f10), (double)f7, (double)f9);
        tessellator.addVertexWithUV((double)(f11 - p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + p_70539_4_ * f10), (double)(f13 - p_70539_5_ * f10 + p_70539_7_ * f10), (double)f7, (double)f8);
        tessellator.addVertexWithUV((double)(f11 + p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + p_70539_4_ * f10), (double)(f13 + p_70539_5_ * f10 + p_70539_7_ * f10), (double)f6, (double)f8);
        tessellator.addVertexWithUV((double)(f11 + p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - p_70539_4_ * f10), (double)(f13 + p_70539_5_ * f10 - p_70539_7_ * f10), (double)f6, (double)f9);
        
        tessellator.draw();
        
        Minecraft.getMinecraft().renderEngine.bindTexture(particleTextures);
        tessellator.startDrawingQuads();
    }
    
    @Override
    public int getFXLayer() {
        return 2;
    }
}
