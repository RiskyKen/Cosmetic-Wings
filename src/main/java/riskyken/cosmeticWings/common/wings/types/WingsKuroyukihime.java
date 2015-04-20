package riskyken.cosmeticWings.common.wings.types;

import riskyken.cosmeticWings.client.render.wings.IWingRenderer;
import riskyken.cosmeticWings.client.render.wings.RenderWingsKuroyukihime;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WingsKuroyukihime extends AbstractWings {
    
    @Override
    public String getName() {
        return "kuroyukihime";
    }
    
    @Override
    public String getAuthorName() {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Class<? extends IWingRenderer> getRendererClass() {
        return RenderWingsKuroyukihime.class;
    }
}
