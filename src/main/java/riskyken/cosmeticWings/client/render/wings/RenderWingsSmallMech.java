package riskyken.cosmeticWings.client.render.wings;

import net.minecraft.entity.player.EntityPlayer;
import riskyken.cosmeticWings.client.model.wings.ModelSmallMechWings;
import riskyken.cosmeticWings.common.wings.WingsData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWingsSmallMech implements IWingRenderer {

    public final ModelSmallMechWings smallMechWings  = new ModelSmallMechWings();
    
    @Override
    public void render(EntityPlayer player, WingsData wingData, int layer, float partialRenderTick) {
        smallMechWings.render(player, layer, wingData);
    }

    @Override
    public void postRender(EntityPlayer player, WingsData wingData, int layer, float partialRenderTick) {
        smallMechWings.render(player, layer, wingData);
    }

    @Override
    public void onTick(EntityPlayer player, WingsData wingData) {
    }
}
