package riskyken.cosmeticWings.common.wings.types;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import riskyken.cosmeticWings.client.render.wings.IWingRenderer;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.common.wings.IWings;

public abstract class AbstractWings implements IWings {
    
    @Override
    public String getRegistryName() {
        return "cosmeticWings:" + getName();
    }
    
    @Override
    public int getNumberOfRenderLayers() {
        return 1;
    }
    
    @Override
    public boolean canRecolour(int layer) {
        return false;
    }
    
    @Override
    public boolean isNomalRender(int layer) {
        return true;
    }
    
    @Override
    public boolean isGlowing(int layer) {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public abstract Class<? extends IWingRenderer> getRendererClass();
    
    @Override
    public String getLocalizedName() {
        String unlocalizedName = "wings." + LibModInfo.ID.toLowerCase() + ":" + this.getName().toLowerCase() + ".name";
        String localizedName = I18n.format(unlocalizedName);
        if (!unlocalizedName.equals(localizedName)) {
            return localizedName;
        }
        return unlocalizedName;
    }
    
    @Override
    public String getFlavourText() {
        String unlocalizedName = "wings." + LibModInfo.ID.toLowerCase() + ":" + this.getName().toLowerCase() + ".flavour";
        String localizedName = I18n.format(unlocalizedName);
        if (!unlocalizedName.equals(localizedName)) {
            return localizedName;
        }
        return "";
    }
}
