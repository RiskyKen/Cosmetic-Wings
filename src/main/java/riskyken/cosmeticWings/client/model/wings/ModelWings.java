package riskyken.cosmeticWings.client.model.wings;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.utils.PointD;
import riskyken.cosmeticWings.utils.Trig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelWings extends ModelBiped {
    
    private static final ResourceLocation wingsImage = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/wings.png");
    
    ModelRenderer rightWingSmall;
    ModelRenderer leftWingSmall;
    ModelRenderer rightWing;
    ModelRenderer leftWing;

    public ModelWings() {
        textureWidth = 64;
        textureHeight = 64;

        rightWingSmall = new ModelRenderer(this, 38, 10);
        rightWingSmall.addBox(4F, 4F, -2.5F, 8, 8, 1);
        rightWingSmall.setRotationPoint(0F, 0F, 0F);
        rightWingSmall.setTextureSize(64, 32);
        rightWingSmall.mirror = true;
        setRotation(rightWingSmall, 0.7853982F, 0F, 1.134464F);

        leftWingSmall = new ModelRenderer(this, 38, 0);
        leftWingSmall.addBox(-12F, 4F, -2.5F, 8, 8, 1);
        leftWingSmall.setRotationPoint(0F, 0F, 0F);
        leftWingSmall.setTextureSize(64, 32);
        leftWingSmall.mirror = true;
        setRotation(leftWingSmall, 0.7853982F, 0F, -1.134464F);

        rightWing = new ModelRenderer(this, 19, 0);
        rightWing.addBox(-7F, 2F, 0F, 8, 21, 1);
        rightWing.setRotationPoint(0F, 0F, 0F);
        rightWing.setTextureSize(64, 32);
        rightWing.mirror = true;
        setRotation(rightWing, 2.094395F, 0F, -1.396263F);

        leftWing = new ModelRenderer(this, 0, 0);
        leftWing.addBox(-1F, 2F, 0F, 8, 21, 1);
        leftWing.setRotationPoint(0F, 0F, 0F);
        leftWing.setTextureSize(64, 32);
        leftWing.mirror = true;
        setRotation(leftWing, 2.094395F, 0F, 1.396263F);
    }
    
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        rightWingSmall.render(f5);
        leftWingSmall.render(f5);
        rightWing.render(f5);
        leftWing.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    private void setRotation(ModelRenderer targetModel, ModelRenderer sourceModel) {
        targetModel.rotateAngleX = sourceModel.rotateAngleX;
        targetModel.rotateAngleY = sourceModel.rotateAngleY;
        targetModel.rotateAngleZ = sourceModel.rotateAngleZ;
    }

    public void render(EntityPlayer player, RenderPlayer renderer, float red, float green, float blue) {

        Minecraft.getMinecraft().getTextureManager().bindTexture(wingsImage);
        GL11.glPushMatrix();
        GL11.glColor3f(red, green, blue);
        float mult = 0.0625F;
        
        rightWing.render(mult);
        leftWing.render(mult);
        GL11.glPopMatrix();

        Random rnd = new Random();
        if (rnd.nextFloat() * 1000 > 990) {
            PointD offset;

            if (rnd.nextFloat() > 0.5f) {
                offset = Trig.moveTo(new PointD(player.posX, player.posZ), 0.3f + rnd.nextFloat(), player.renderYawOffset + 56);
            } else {
                offset = Trig.moveTo(new PointD(player.posX, player.posZ), 0.3f + rnd.nextFloat(), player.renderYawOffset + 121);
            }

            float yOffset = 0f;

            double parX = offset.x;
            double parY = player.posY - 0.4D;
            double parZ = offset.y;
            // Particles.FEATHER.spawnParticle(player.worldObj, parX, parY,
            // parZ, red, green, blue, true);
        }
    }
}
