package riskyken.cosmeticWings.client.render.wings;

import net.minecraft.entity.player.EntityPlayer;
import riskyken.cosmeticWings.client.model.wings.ModelSmallMechWings;
import riskyken.cosmeticWings.common.wings.WingData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWingsSmallMech implements IWingRenderer {

    public final ModelSmallMechWings smallMechWings  = new ModelSmallMechWings();
    
    @Override
    public void render(EntityPlayer player, WingData wingData, float partialRenderTick) {
        smallMechWings.render(player, false, wingData);
    }

    @Override
    public void postRender(EntityPlayer player, WingData wingData, float partialRenderTick) {
        smallMechWings.render(player, true, wingData);
    }

    @Override
    public void onTick(EntityPlayer player, WingData wingData) {
    }
}
