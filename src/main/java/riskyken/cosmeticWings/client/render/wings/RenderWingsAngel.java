package riskyken.cosmeticWings.client.render.wings;

import net.minecraft.entity.player.EntityPlayer;
import riskyken.cosmeticWings.client.model.wings.ModelBigWings;
import riskyken.cosmeticWings.common.wings.WingData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWingsAngel implements IWingRenderer {

    public final ModelBigWings bigWings = new ModelBigWings("textures/wings/big-white-wings.png");
    
    @Override
    public void render(EntityPlayer player, WingData wingData, float partialRenderTick) {
        bigWings.render(player, wingData);
    }

    @Override
    public void postRender(EntityPlayer player, WingData wingData, float partialRenderTick) {
    }

    @Override
    public void onTick(EntityPlayer player, WingData wingData) {
        bigWings.onTick(player, 1, wingData);
    }
}
