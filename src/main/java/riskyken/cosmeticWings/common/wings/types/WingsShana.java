package riskyken.cosmeticWings.common.wings.types;

import riskyken.cosmeticWings.client.render.wings.IWingRenderer;
import riskyken.cosmeticWings.client.render.wings.RenderWingsShana;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WingsShana extends AbstractWings {
    
    @Override
    public String getName() {
        return "shana";
    }
    
    @Override
    public String getAuthorName() {
        return "@LittleChippie";
    }
    
    @Override
    public boolean isGlowing(int layer) {
        return true;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Class<? extends IWingRenderer> getRendererClass() {
        return RenderWingsShana.class;
    }
}
