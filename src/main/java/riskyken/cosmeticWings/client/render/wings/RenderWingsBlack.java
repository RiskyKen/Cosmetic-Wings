package riskyken.cosmeticWings.client.render.wings;

import net.minecraft.entity.player.EntityPlayer;
import riskyken.cosmeticWings.client.model.wings.ModelBigWings;
import riskyken.cosmeticWings.common.wings.WingData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWingsBlack implements IWingRenderer {
    
    public final ModelBigWings bigWings = new ModelBigWings("textures/wings/big-black-wings.png");
    
    @Override
    public void render(EntityPlayer player, WingData wingData, float partialRenderTick) {
        bigWings.render(player, wingData);
    }

    @Override
    public void postRender(EntityPlayer player, WingData wingData, float partialRenderTick) {
    }

    @Override
    public void onTick(EntityPlayer player, WingData wingData) {
        bigWings.onTick(player, 0, wingData);
    }
}
