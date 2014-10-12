package riskyken.cosmeticWings.client.model.wings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import riskyken.cosmeticWings.common.lib.LibModInfo;

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

        wing.render(mult);
        GL11.glRotatef(-90, 1, 0, 0);
        wing.render(mult);
        //leftWing.render(mult);

        GL11.glPopMatrix();
    }
}
