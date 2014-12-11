package riskyken.cosmeticWings.client.render.wings;

import net.minecraft.entity.player.EntityPlayer;
import riskyken.cosmeticWings.common.wings.WingData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IWingRenderer {
    
    public void render(EntityPlayer player, WingData wingData, float partialRenderTick);
    
    public void postRender(EntityPlayer player, WingData wingData, float partialRenderTick);
    
    public void onTick(EntityPlayer player, WingData wingData);
}
