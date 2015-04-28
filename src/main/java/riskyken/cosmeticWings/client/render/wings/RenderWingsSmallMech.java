package riskyken.cosmeticWings.client.render.wings;

import net.minecraft.entity.player.EntityPlayer;
import riskyken.cosmeticWings.client.lib.LibModels;
import riskyken.cosmeticWings.common.wings.WingsData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWingsSmallMech implements IWingRenderer {
    
    @Override
    public void render(EntityPlayer player, WingsData wingData, int layer, float partialRenderTick) {
        LibModels.SMALL_MECH_WINGS.render(player, layer, wingData);
    }

    @Override
    public void postRender(EntityPlayer player, WingsData wingData, int layer, float partialRenderTick) {
        LibModels.SMALL_MECH_WINGS.render(player, layer, wingData);
    }

    @Override
    public void onTick(EntityPlayer player, WingsData wingData) {
    }
}
