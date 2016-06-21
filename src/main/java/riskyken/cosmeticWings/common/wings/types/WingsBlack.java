package riskyken.cosmeticWings.common.wings.types;

import riskyken.cosmeticWings.client.render.wings.IWingRenderer;
import riskyken.cosmeticWings.client.render.wings.RenderWingsBlack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WingsBlack extends AbstractWings {
    
    @Override
    public String getName() {
        return "black";
    }
    
    @Override
    public String getAuthorName() {
        return "@LittleChippie";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Class<? extends IWingRenderer> getRendererClass() {
        return RenderWingsBlack.class;
    }
}
