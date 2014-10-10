package riskyken.cosmeticWings.client.model.wings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelMetalWings extends ModelBiped {

    ModelRenderer rightWing;
    ModelRenderer leftWing;
    private final ResourceLocation[] wingsImage;

    public ModelMetalWings() {
        textureWidth = 64;
        textureHeight = 32;

        rightWing = new ModelRenderer(this, 0, 0);
        // rightWing.addBox(-13F, 2F, 0F, 20, 29, 1);
        rightWing.addBox(-7F, 2F, -1F, 20, 29, 1);
        rightWing.setRotationPoint(0F, 0F, 0F);
        rightWing.setTextureSize(32, 32);
        rightWing.mirror = false;
        setRotation(rightWing, 1.047198F, 0F, 1.745329F);
        // setRotation(rightWing, 2.094395F, 0F, -1.396263F);

        leftWing = new ModelRenderer(this, 0, 0);
        leftWing.addBox(-7F, 2F, 0F, 20, 29, 1);
        leftWing.setRotationPoint(0F, 0F, 0F);
        leftWing.setTextureSize(32, 32);
        leftWing.mirror = true;
        setRotation(leftWing, 2.094395F, 0F, 1.396263F);

        wingsImage = new ResourceLocation[1];
        wingsImage[0] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/metal-wings.png");
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void render(EntityPlayer player, RenderPlayer renderer) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(wingsImage[0]);
        float mult = 0.0625F;
        rightWing.render(mult);
        leftWing.render(mult);
    }
}
