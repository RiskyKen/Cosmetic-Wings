package riskyken.cosmeticWings.client.render.wings;

import net.minecraft.entity.player.EntityPlayer;
import riskyken.cosmeticWings.common.wings.WingsData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IWingRenderer {
    
    public void render(EntityPlayer player, WingsData wingData, int layer, float partialRenderTick);
    
    public void postRender(EntityPlayer player, WingsData wingData, int layer, float partialRenderTick);
    
    public void onTick(EntityPlayer player, WingsData wingData);
}
