package riskyken.cosmeticWings.client.model.wings;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
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
import riskyken.cosmeticWings.common.wings.WingData;
import riskyken.cosmeticWings.utils.PointD;
import riskyken.cosmeticWings.utils.Trig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBigWings extends ModelWingBase {

    ModelRenderer rightWing;
    ModelRenderer leftWing;
    private final ResourceLocation[] wingsImage;

    public ModelBigWings() {
        textureWidth = 64;
        textureHeight = 32;

        leftWing = new ModelRenderer(this, 0, 0);
        leftWing.setTextureSize(64, 32);
        leftWing.addBox(-8.5F, 0F, -0.5F, 17, 30, 1);

        rightWing = new ModelRenderer(this, 0, 0);
        rightWing.setTextureSize(64, 32);
        rightWing.addBox(-8.5F, 0F, -0.5F, 17, 30, 1);
        rightWing.mirror = true;

        wingsImage = new ResourceLocation[3];
        wingsImage[0] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/wings/big-black-wings.png");
        wingsImage[1] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/wings/big-white-wings.png");
        wingsImage[2] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/wings/shana-wings.png");
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        rightWing.render(f5);
        leftWing.render(f5);
    }

    public void render(EntityPlayer player, RenderPlayer renderer, int wingId, WingData wingData) {
        if (wingId >= 0 & wingId < wingsImage.length) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(wingsImage[wingId]);
        }

        float angle = getWingAngle(player.capabilities.isFlying & player.isAirBorne, 30, 5000, 400, player.getEntityId());

        setRotation(leftWing, (float) Math.toRadians(angle + 20), (float) Math.toRadians(-4), (float) Math.toRadians(6));
        setRotation(rightWing, (float) Math.toRadians(-angle - 20), (float) Math.toRadians(4), (float) Math.toRadians(6));

        GL11.glPushMatrix();
        GL11.glTranslatef(0, 4 * SCALE, 1.5F * SCALE);
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glRotatef(90, 0, 0, 1);

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

        GL11.glPushMatrix();
        GL11.glTranslatef(0, 0, wingData.centreOffset * 3 * SCALE);
        GL11.glScalef(wingData.wingScale, wingData.wingScale, wingData.wingScale);
        leftWing.render(SCALE);
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glTranslatef(0, 0, -wingData.centreOffset * 3 * SCALE);
        GL11.glScalef(wingData.wingScale, wingData.wingScale, wingData.wingScale);
        rightWing.render(SCALE);
        GL11.glPopMatrix();

        if (wingId != 0) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
            GL11.glEnable(GL11.GL_LIGHTING);
        }

        GL11.glPopMatrix();
    }

    public void onTick(EntityPlayer player, int wingId, float wingScale) {
        spawnParticales(player, wingId, wingScale);
    }

    private void spawnParticales(EntityPlayer player, int type, float wingScale) {
        float angle = getWingAngle(player.capabilities.isFlying & player.isAirBorne, 30, 5000, 400, player.getEntityId());
        float scale = (1F - wingScale) * 0.2F;
        float spawnChance = 960;
        if (player.capabilities.isFlying & player.isAirBorne) {
            spawnChance = 900;
        }
        Random rnd = new Random();
        if (rnd.nextFloat() * 1000 > spawnChance) {
            PointD offset;

            if (rnd.nextFloat() >= 0.5f) {
                offset = Trig.moveTo(new PointD(player.posX, player.posZ), 0.3f + rnd.nextFloat() * 1.4f * wingScale, player.renderYawOffset + 90 - 22 - angle + 10);
            } else {
                offset = Trig.moveTo(new PointD(player.posX, player.posZ), 0.3f + rnd.nextFloat() * 1.4f * wingScale, player.renderYawOffset + 90 + 32 + angle - 10);
            }

            float yOffset = 0f;

            double parX = offset.x;
            double parY = player.posY - 0.4D + scale;
            double parZ = offset.y;

            EntityClientPlayerMP localPlayer = Minecraft.getMinecraft().thePlayer;

            if (!localPlayer.getDisplayName().equals(player.getDisplayName())) {
                parY += 1.6D;
            }

            EntityFeatherFx particle = new EntityFeatherFx(player.worldObj, parX, parY, parZ, type, wingScale);
            ParticleManager.INSTANCE.spawnParticle(player.worldObj, particle);
        }
    }
}
