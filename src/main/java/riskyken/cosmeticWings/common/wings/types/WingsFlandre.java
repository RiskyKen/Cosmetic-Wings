package riskyken.cosmeticWings.common.wings.types;

import riskyken.cosmeticWings.client.render.wings.IWingRenderer;
import riskyken.cosmeticWings.client.render.wings.RenderWingsFlandre;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WingsFlandre extends AbstractWings {
    
    @Override
    public String getName() {
        return "flandre";
    }
    
    @Override
    public String getAuthorName() {
        return "@LittleChippie";
    }
    
    @Override
    public int getNumberOfRenderLayers() {
        return 2;
    }
    
    @Override
    public boolean isGlowing(int layer) {
        if (layer == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Class<? extends IWingRenderer> getRendererClass() {
        return RenderWingsFlandre.class;
    }
}
