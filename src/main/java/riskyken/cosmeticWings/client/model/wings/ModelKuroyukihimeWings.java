package riskyken.cosmeticWings.client.model.wings;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import riskyken.cosmeticWings.client.particles.EntityButterflyFx;
import riskyken.cosmeticWings.client.particles.ParticleManager;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.utils.PointD;
import riskyken.cosmeticWings.utils.Trig;

public class ModelKuroyukihimeWings extends ModelBase {
    
    ModelRenderer wing;
    private final ResourceLocation[] wingsImage;

    public ModelKuroyukihimeWings() {
        textureWidth = 256;
        textureHeight = 64;

        wing = new ModelRenderer(this, 0, 0);
        wing.addBox(0F, 0F, 0F, 125, 59, 1);
        wing.setRotationPoint(0F, 0F, 0F);
        wing.setTextureSize(256, 64);
        wing.mirror = true;

        wingsImage = new ResourceLocation[1];
        wingsImage[0] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/wings/kuroyukihime-wings.png");
    }

    public void render(EntityPlayer player, RenderPlayer renderer) {
        GL11.glColor3f(1F, 1F, 1F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(wingsImage[0]);
        RenderWing(player.capabilities.isFlying & player.isAirBorne);
    }
    
    private void RenderWing(boolean flying) {
        float mult = 0.0625F;
        GL11.glPushMatrix();
        
        GL11.glTranslatef(0F, -1.0F, 0.12F);
        
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glRotatef(90, 0, 0, 1);
        
        float scale = 0.32F;
        GL11.glScalef(scale, scale, scale);
        
        
        int flapTime = 4000;
        if (flying) {
            flapTime = 500;
        }
        
        float time = System.currentTimeMillis() % flapTime;
        float deltaTime = time / flapTime;
        
        
        double movment = deltaTime * 80;
        
        if (movment > 40) {
            movment = 40 - (movment - 40);
            //movment = 40;
        }
        
        double disToCenter = movment - 20;
        if (disToCenter > 0) {
            disToCenter = -disToCenter;
        }
        disToCenter += 20;
        disToCenter = disToCenter / 20;
        //disToCenter = -disToCenter;
        
        
        //movment *= ((disToCenter / 10) * 10.0F);
        
        //ModLogger.log(disToCenter);
        
        GL11.glRotated(55 - movment, 1, 0, 0);
        wing.render(mult);
        
        GL11.glRotated(-105 + movment * 2, 1, 0, 0);
        wing.render(mult);
        
        GL11.glPopMatrix();
    }
    
    public void onTick(EntityPlayer player, float wingScale) {
        spawnParticales(player, wingScale);
    }

    private void spawnParticales(EntityPlayer player, float wingScale) {
        float scale = (1F - wingScale) * 0.2F;
        Random rnd = new Random();
        if (rnd.nextFloat() * 1000 > 980) {
            PointD offset;

            if (rnd.nextFloat() >= 0.5f) {
                offset = Trig.moveTo(new PointD(player.posX, player.posZ), 0.3f + rnd.nextFloat() * 1.4f * wingScale, player.renderYawOffset + 60);
            } else {
                offset = Trig.moveTo(new PointD(player.posX, player.posZ), 0.3f + rnd.nextFloat() * 1.4f * wingScale, player.renderYawOffset + 127);
            }

            float yOffset = 0f;

            double parX = offset.x;
            double parY = player.posY - 0.4D + scale + rnd.nextFloat() * 0.5F;
            double parZ = offset.y;

            EntityClientPlayerMP localPlayer = Minecraft.getMinecraft().thePlayer;

            if (!localPlayer.getDisplayName().equals(player.getDisplayName())) {
                parY += 1.6D;
            }

            EntityButterflyFx particle = new EntityButterflyFx(player.worldObj, parX, parY, parZ, wingScale, player);
            ParticleManager.INSTANCE.spawnParticle(player.worldObj, particle);
        }
    }
}