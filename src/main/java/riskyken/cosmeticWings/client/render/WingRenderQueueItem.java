package riskyken.cosmeticWings.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import riskyken.cosmeticWings.common.wings.WingData;

public class WingRenderQueueItem {
    
    private EntityPlayer player;
    private WingData wingData;
    private float lightX;
    private float lightY;
    
    public WingRenderQueueItem(EntityPlayer player, WingData wingData) {
        this.player = player;
        this.wingData = wingData;
        lightX = OpenGlHelper.lastBrightnessX;
        lightY = OpenGlHelper.lastBrightnessY;
    }
    
    public void Render(WingRenderManager wingRenderManager, float partialRenderTick) {
        GL11.glPushMatrix();
        
        float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialRenderTick;
        EntityPlayer localPlayer = Minecraft.getMinecraft().thePlayer;
        EntityPlayer renderTarget = player;
        
        double tarX1 = renderTarget.prevPosX + (renderTarget.posX - renderTarget.prevPosX) * partialRenderTick;
        double tarX2 = localPlayer.prevPosX + (localPlayer.posX - localPlayer.prevPosX) * partialRenderTick;
        
        double tarY1 = renderTarget.prevPosY + (renderTarget.posY - renderTarget.prevPosY) * partialRenderTick;
        double tarY2 = localPlayer.prevPosY + (localPlayer.posY - localPlayer.prevPosY) * partialRenderTick;
        
        double tarZ1 = renderTarget.prevPosZ + (renderTarget.posZ - renderTarget.prevPosZ) * partialRenderTick;
        double tarZ2 = localPlayer.prevPosZ + (localPlayer.posZ - localPlayer.prevPosZ) * partialRenderTick;
        
        double x = tarX1 - tarX2;
        double y = tarY1 - tarY2;
        double z = tarZ1 - tarZ2;
        
        if (localPlayer.getUniqueID() != renderTarget.getUniqueID()) {
            y += 26D * 0.0625F;
        }
        
        GL11.glTranslated(x, y, z);
        
        GL11.glScalef(1, -1, -1);
        GL11.glRotatef(yawOffset, 0, 1, 0);
        
        float scale = 15F * 0.0625F;
        GL11.glScalef(scale, scale, scale);
        
        GL11.glTranslatef(0, 3F * 0.0625F, 0F);
        
        if (player.isSneaking()) {
            GL11.glRotatef(28.6F, 1, 0, 0);
        }
        
        GL11.glTranslatef(0, 0.6F * 0.0625F, 0F);
        
        GL11.glTranslatef(0, (wingData.wingScale - 1F) * 0.10F, (1F - wingData.wingScale) * 0.0625F);
        GL11.glTranslatef(0, 6 * 0.0625F, 0F);
        GL11.glTranslatef(0, -wingData.heightOffset * 8 * 0.0625F, 0F);
        
        switch (wingData.wingType) {
        case MECH:
            RenderHelper.enableStandardItemLighting();
            wingRenderManager.mechWings.render(player, true, wingData);
            break;
        default:
            break; 
        }
        GL11.glPopMatrix();
    }
}