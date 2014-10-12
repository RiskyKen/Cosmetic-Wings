package riskyken.cosmeticWings.client.model.wings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import riskyken.cosmeticWings.common.lib.LibModInfo;

public class ModelKuroyukihimeWings extends ModelBiped {
    
    ModelRenderer rightWing;
    ModelRenderer leftWing;
    private final ResourceLocation[] wingsImage;

    public ModelKuroyukihimeWings() {
        textureWidth = 256;
        textureHeight = 64;

        rightWing = new ModelRenderer(this, 0, 0);
        // rightWing = new ModelRenderer(this, 0, 31);
        rightWing.addBox(0F, 0F, 0F, 125, 59, 1);
        rightWing.setRotationPoint(0F, 0F, 0F);
        rightWing.setTextureSize(256, 64);
        rightWing.mirror = true;
        //setRotation(rightWing, 1.047198F, 0F, 1.745329F);
        // setRotation(rightWing, 2.094395F, 0F, -1.396263F);

        leftWing = new ModelRenderer(this, 0, 0);
        leftWing.addBox(-6F, 2F, 0F, 125, 59, 1);
        leftWing.setRotationPoint(0F, 0F, 0F);
        leftWing.setTextureSize(64, 32);
        leftWing.mirror = true;
        setRotation(leftWing, 2.094395F, 0F, 1.396263F);

        wingsImage = new ResourceLocation[1];
        wingsImage[0] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/kuroyukihime-wings.png");
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

    public void render(EntityPlayer player, RenderPlayer renderer) {


        Minecraft.getMinecraft().getTextureManager().bindTexture(wingsImage[0]);
        
        float mult = 0.0625F;

        GL11.glPushMatrix();

        GL11.glColor3f(1F, 1F, 1F);
        GL11.glTranslatef(0F, -1.0F, 0.12F);
        
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glRotatef(90, 0, 0, 1);
        
        GL11.glRotatef(45, 1, 0, 0);
        
        float scale = 0.32F;
        GL11.glScalef(scale, scale, scale);

        rightWing.render(mult);
        GL11.glRotatef(-90, 1, 0, 0);
        rightWing.render(mult);
        //leftWing.render(mult);

        GL11.glPopMatrix();
    }
}
