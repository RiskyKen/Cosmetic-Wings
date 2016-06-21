package riskyken.cosmeticWings.client.gui.controls;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import riskyken.cosmeticWings.common.lib.LibModInfo;

@SideOnly(Side.CLIENT)
public class GuiHelper {
    
    public static void renderLocalizedGuiName(FontRenderer fontRenderer, int guiLeft, int guiTop, int xSize, String name) {
        String unlocalizedName = "inventory." + LibModInfo.ID.toLowerCase() + ":" + name + ".name";
        String localizedName = I18n.format(unlocalizedName);
        String renderText = unlocalizedName;
        if (!unlocalizedName.equals(localizedName)){
            renderText = localizedName;
        }
        int xPos = xSize / 2 - fontRenderer.getStringWidth(renderText) / 2;
        fontRenderer.drawString(renderText, guiLeft + xPos, guiTop + 6, 4210752);
    }
    
    public static String getLocalizedControlName(String guiName, String controlName) {
        String unlocalizedName = "inventory." + LibModInfo.ID.toLowerCase() + ":" + guiName + "." + controlName;
        String localizedName = I18n.format(unlocalizedName);
        if (!unlocalizedName.equals(localizedName)){
            return localizedName;
        }
        return unlocalizedName;
    }
}
