package riskyken.cosmeticWings.client.render.wings;

import net.minecraft.entity.player.EntityPlayer;
import riskyken.cosmeticWings.client.model.wings.ModelWingsFlandre;
import riskyken.cosmeticWings.common.wings.WingData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWingsFlandre implements IWingRenderer {

    public final ModelWingsFlandre modelWingsFlandre = new ModelWingsFlandre();
    
    @Override
    public void render(EntityPlayer player, WingData wingData, float partialRenderTick) {
        modelWingsFlandre.render(player, wingData); 
    }

    @Override
    public void postRender(EntityPlayer player, WingData wingData, float partialRenderTick) {
    }

    @Override
    public void onTick(EntityPlayer player, WingData wingData) {
    }
}
