package riskyken.cosmeticWings.common.wings.types;

import riskyken.cosmeticWings.client.render.wings.IWingRenderer;
import riskyken.cosmeticWings.client.render.wings.RenderWingsMetal;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WingsMetal extends AbstractWings {

    @Override
    public String getName() {
        return "metal";
    }
    
    @Override
    public String getAuthorName() {
        return "@LittleChippie";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Class<? extends IWingRenderer> getRendererClass() {
        return RenderWingsMetal.class;
    }
}
