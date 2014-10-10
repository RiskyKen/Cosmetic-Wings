package riskyken.cosmeticWings.client.model.wings;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import riskyken.cosmeticWings.client.particles.EntityFeatherFx;
import riskyken.cosmeticWings.client.particles.ParticleManager;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.utils.PointD;
import riskyken.cosmeticWings.utils.Trig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBigWings extends ModelBiped {
    
    ModelRenderer rightWing;
    ModelRenderer leftWing;
    private final ResourceLocation[] wingsImage;

    public ModelBigWings() {
        textureWidth = 64;
        textureHeight = 32;

        rightWing = new ModelRenderer(this, 0, 0);
        // rightWing = new ModelRenderer(this, 0, 31);
        rightWing.addBox(-6F, 2F, -1F, 17, 30, 1);
        rightWing.setRotationPoint(0F, 0F, 0F);
        rightWing.setTextureSize(64, 32);
        rightWing.mirror = true;
        setRotation(rightWing, 1.047198F, 0F, 1.745329F);
        // setRotation(rightWing, 2.094395F, 0F, -1.396263F);

        leftWing = new ModelRenderer(this, 0, 0);
        leftWing.addBox(-6F, 2F, 0F, 17, 30, 1);
        leftWing.setRotationPoint(0F, 0F, 0F);
        leftWing.setTextureSize(64, 32);
        leftWing.mirror = true;
        setRotation(leftWing, 2.094395F, 0F, 1.396263F);

        wingsImage = new ResourceLocation[3];
        wingsImage[0] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/big-black-wings.png");
        wingsImage[1] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/big-white-wings.png");
        wingsImage[2] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/shana-wings.png");
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        rightWing.render(f5);
        leftWing.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void render(EntityPlayer player, RenderPlayer renderer, int wingId) {

        if (wingId >= 0 & wingId < wingsImage.length) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(wingsImage[wingId]);
        }
        float mult = 0.0625F;

        GL11.glPushMatrix();

        GL11.glColor3f(1F, 1F, 1F);

        float lastBrightnessX = OpenGlHelper.lastBrightnessX;
        float lastBrightnessY = OpenGlHelper.lastBrightnessY;
        if (wingId != 0) {
            GL11.glDisable(GL11.GL_LIGHTING);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
        }

        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(15728880);

        float scale = 1.0F;
        GL11.glScalef(scale, scale, scale);

        rightWing.render(mult);
        leftWing.render(mult);

        if (wingId != 0) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
            GL11.glEnable(GL11.GL_LIGHTING);
        }

        GL11.glPopMatrix();
    }

    public void onTick(EntityPlayer player, int wingId) {
        spawnParticales(player, wingId);
    }

    private void spawnParticales(EntityPlayer player, int type) {
        Random rnd = new Random();
        if (rnd.nextFloat() * 1000 > 960) {
            PointD offset;

            if (rnd.nextFloat() >= 0.5f) {
                offset = Trig.moveTo(new PointD(player.posX, player.posZ), 0.3f + rnd.nextFloat() * 1.5f, player.renderYawOffset + 56);
            } else {
                offset = Trig.moveTo(new PointD(player.posX, player.posZ), 0.3f + rnd.nextFloat() * 1.5f, player.renderYawOffset + 121);
            }

            float yOffset = 0f;

            double parX = offset.x;
            double parY = player.posY - 0.4D;
            double parZ = offset.y;

            EntityClientPlayerMP localPlayer = Minecraft.getMinecraft().thePlayer;

            if (!localPlayer.getDisplayName().equals(player.getDisplayName())) {
                parY += 1.6D;
            }

            EntityFeatherFx particle = new EntityFeatherFx(player.worldObj, parX, parY, parZ, type);
            ParticleManager.INSTANCE.spawnParticle(player.worldObj, particle);
        }
    }
}
