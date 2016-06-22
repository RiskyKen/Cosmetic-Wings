package riskyken.cosmeticWings.client.model.wings;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import riskyken.cosmeticWings.client.particles.EntityFeatherFx;
import riskyken.cosmeticWings.client.particles.ParticleManager;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.common.wings.WingsData;
import riskyken.cosmeticWings.utils.PointD;
import riskyken.cosmeticWings.utils.Trig;
import riskyken.cosmeticWings.utils.UtilColour;
import riskyken.cosmeticWings.utils.UtilPlayer;

@SideOnly(Side.CLIENT)
public class ModelBigWings extends ModelWingBase {

    ModelRenderer rightWing;
    ModelRenderer leftWing;
    private final ResourceLocation wingsImage;

    public ModelBigWings(String imagePath) {
        textureWidth = 64;
        textureHeight = 32;

        leftWing = new ModelRenderer(this, 0, 0);
        leftWing.setTextureSize(64, 32);
        leftWing.addBox(-8.5F, 0F, -0.5F, 17, 30, 1);

        rightWing = new ModelRenderer(this, 0, 0);
        rightWing.setTextureSize(64, 32);
        rightWing.addBox(-8.5F, 0F, -0.5F, 17, 30, 1);
        rightWing.mirror = true;

        wingsImage = new ResourceLocation(LibModInfo.ID.toLowerCase(), imagePath);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        rightWing.render(f5);
        leftWing.render(f5);
    }

    public void render(EntityPlayer player, int layer, WingsData wingData) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(wingsImage);

        float angle = getWingAngle(player.capabilities.isFlying & player.isAirBorne, 30, 5000, 400, player.getEntityId());

        setRotation(leftWing, (float) Math.toRadians(angle + 20), (float) Math.toRadians(-4), (float) Math.toRadians(6));
        setRotation(rightWing, (float) Math.toRadians(-angle - 20), (float) Math.toRadians(4), (float) Math.toRadians(6));

        GL11.glPushMatrix();
        GL11.glTranslatef(0, 4 * SCALE, 1.5F * SCALE);
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glRotatef(90, 0, 0, 1);

        if (wingData.wingType.canRecolour(layer)) {
            Color c = new Color(wingData.colour);
            float red = (float) c.getRed() / 255;
            float green = (float) c.getGreen() / 255;
            float blue = (float) c.getBlue() / 255;
            GL11.glColor3f(red, green, blue);
        }
        
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
        
        
        GL11.glPopMatrix();
    }

    public void onTick(EntityPlayer player, int wingId, WingsData wingData) {
        spawnParticales(player, wingId, wingData);
    }

    private void spawnParticales(EntityPlayer player, int type, WingsData wingData) {
        float angle = getWingAngle(player.capabilities.isFlying & player.isAirBorne, 30, 5000, 400, player.getEntityId());
        float scale = (1F - wingData.wingScale) * 0.2F;
        float spawnChance = 250;
        if (player.capabilities.isFlying & player.isAirBorne) {
            spawnChance = 300;
        }
        
        Random rnd = new Random();
        if (rnd.nextFloat() * 1000 < spawnChance) {
            if (rnd.nextFloat() * 8 >= wingData.particleSpawnRate) {
                return;
            }
            
            PointD offset;

            if (rnd.nextFloat() >= 0.5f) {
                offset = Trig.moveTo(new PointD(player.posX, player.posZ), 0.3f + rnd.nextFloat() * 1.4f * wingData.wingScale, player.renderYawOffset + 90 - 22 - angle + 10);
            } else {
                offset = Trig.moveTo(new PointD(player.posX, player.posZ), 0.3f + rnd.nextFloat() * 1.4f * wingData.wingScale, player.renderYawOffset + 90 + 32 + angle - 10);
            }

            float yOffset = 0f;

            double parX = offset.x;
            double parY = player.posY - 0.4D + scale - (6 * SCALE) + (wingData.heightOffset * 6 * SCALE);
            double parZ = offset.y;

            EntityPlayerSP localPlayer = Minecraft.getMinecraft().thePlayer;
            parY += 1.6D;
            /*
            if (UtilPlayer.isLocalPlayer(player)) {
                parY += 1.6D;
            }
            */
            EntityFeatherFx particle;
            if (type == 1) {
                particle = new EntityFeatherFx(player.worldObj, parX, parY, parZ, type, wingData.wingScale, wingData.colour);
            } else {
                particle = new EntityFeatherFx(player.worldObj, parX, parY, parZ, type, wingData.wingScale, UtilColour.getMinecraftColor(0));
            }
            
            ParticleManager.INSTANCE.spawnParticle(player.worldObj, particle);
            
        }
    }
}
