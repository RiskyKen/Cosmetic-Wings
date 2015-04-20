package riskyken.cosmeticWings.common.wings;

import riskyken.cosmeticWings.client.render.wings.IWingRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IWings {
    
    public String getName();
    
    public String getRegistryName();
    
    public String getAuthorName();
    
    public int getNumberOfRenderLayers();
    
    public boolean canRecolour(int layer);
    
    public boolean isNomalRender(int layer);
    
    public boolean isGlowing(int layer);
    
    @SideOnly(Side.CLIENT)
    public abstract Class<? extends IWingRenderer> getRendererClass();
    
    public String getLocalizedName();
    
    public String getFlavourText();
}
