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
public class ModelExtraBigWings extends ModelBiped
{
	ModelRenderer rightWing;
	ModelRenderer leftWing;
	private final ResourceLocation[] wingsImage;

	public ModelExtraBigWings()
	{
		textureWidth = 64;
		textureHeight = 64;
		
		rightWing = new ModelRenderer(this, 0, 32);
		//rightWing.addBox(-13F, 2F, 0F, 20, 31, 1);
		rightWing.addBox(-7F, 2F, -1F, 20, 31, 1);
		rightWing.setRotationPoint(0F, 0F, 0F);
		rightWing.setTextureSize(64, 64);
		rightWing.mirror = false;
		setRotation(rightWing, 1.047198F, 0F, 1.745329F);
		//setRotation(rightWing, 2.094395F, 0F, -1.396263F);
		
		leftWing = new ModelRenderer(this, 0, 0);
		leftWing.addBox(-7F, 2F, 0F, 20, 31, 1);
		leftWing.setRotationPoint(0F, 0F, 0F);
		leftWing.setTextureSize(64, 64);
		leftWing.mirror = false;
		setRotation(leftWing, 2.094395F, 0F, 1.396263F);
		      
		wingsImage = new ResourceLocation[2];
		wingsImage[0] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/flandre-wings.png");
		wingsImage[1] = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/armor/flandre-wings-glow.png");
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		//super.render(entity, f, f1, f2, f3, f4, f5);
		//setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		rightWing.render(f5);
		leftWing.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
	
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

	public void render(EntityPlayer player, RenderPlayer renderer) {
		renderWingLayer(player, renderer, 0);
		renderWingLayer(player, renderer, 1);
	}

	private void renderWingLayer(EntityPlayer player, RenderPlayer renderer, int layerId) {	
		if (layerId >= 0 & layerId < wingsImage.length) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(wingsImage[layerId]);
		}
		float mult = 0.0625F;
		
		GL11.glPushMatrix();
		
		float lastBrightnessX = OpenGlHelper.lastBrightnessX;
		float lastBrightnessY = OpenGlHelper.lastBrightnessY;
		if (layerId != 0) {
			GL11.glDisable(GL11.GL_LIGHTING);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		}

		Tessellator tessellator = Tessellator.instance;
		//tessellator.setBrightness(15728880);
		float scale = 1.0F;
		GL11.glScalef(scale, scale, scale);
		
	    rightWing.render(mult);
	    leftWing.render(mult);
	    
	    if (layerId != 0) {
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
		if (rnd.nextFloat() * 100 > 85) {
			PointD offset;// = new PointD(player.posX, player.posZ);
			
			if (rnd.nextFloat() > 0.5f) {
				offset = Trig.moveTo(new PointD(player.posX, player.posZ), 0.3f  + rnd.nextFloat() * 1.5f, player.renderYawOffset + 56);
			} else {
				offset = Trig.moveTo(new PointD(player.posX, player.posZ), 0.3f + rnd.nextFloat() * 1.5f, player.renderYawOffset + 121);	
			}
			
			float yOffset = 0f;
			
			double parX = offset.x;
			double parY = player.posY - 0.4D;
			double parZ = offset.y;
			
			EntityClientPlayerMP localPlayer =  Minecraft.getMinecraft().thePlayer;
			
			if (!localPlayer.getDisplayName().equals(player.getDisplayName())) {
				parY += 1.6D;
			}
			
			EntityFeatherFx particle = new EntityFeatherFx(player.worldObj, parX, parY, parZ, type);
			ParticleManager.INSTANCE.spawnParticle(player.worldObj, particle);
		}
	}

}
