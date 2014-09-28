package riskyken.cosmeticWings.client.gui.controls;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHelper {
    
    public static void renderLocalizedGuiName(FontRenderer fontRenderer, int guiLeft, int guiTop, int xSize, String name) {
        String unlocalizedName = "inventory." + LibModInfo.ID.toLowerCase() + ":" + name + ".name";
        String localizedName = StatCollector.translateToLocal(unlocalizedName);
        String renderText = unlocalizedName;
        if (!unlocalizedName.equals(localizedName)){
            renderText = localizedName;
        }
        int xPos = xSize / 2 - fontRenderer.getStringWidth(renderText) / 2;
        fontRenderer.drawString(renderText, guiLeft + xPos, guiTop + 6, 4210752);
    }
    
    public static String getLocalizedControlName(String guiName, String controlName) {
        String unlocalizedName = "inventory." + LibModInfo.ID.toLowerCase() + ":" + guiName + "." + controlName;
        String localizedName = StatCollector.translateToLocal(unlocalizedName);
        if (!unlocalizedName.equals(localizedName)){
            return localizedName;
        }
        return unlocalizedName;
    }
}
