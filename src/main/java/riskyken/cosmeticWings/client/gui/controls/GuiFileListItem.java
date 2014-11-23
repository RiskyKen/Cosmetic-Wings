package riskyken.cosmeticWings.client.gui.controls;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import riskyken.cosmeticWings.utils.UtilColour;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiFileListItem extends Gui implements IGuiListItem {

    private final String displayText;
    
    public GuiFileListItem(String displayText) {
        this.displayText = displayText;
    }

    @Override
    public void drawListItem(FontRenderer fontRenderer, int x, int y, int mouseX, int mouseY, boolean selected) {
        int fontColour = UtilColour.getMinecraftColor(8);
        if (selected) {
            fontColour = UtilColour.getMinecraftColor(4);
        }
        if (isHovering(fontRenderer, x, y, mouseX, mouseY)) {
            fontColour = UtilColour.getMinecraftColor(0);
        }
        fontRenderer.drawString(displayText, x, y, fontColour);
    }

    @Override
    public boolean mousePressed(FontRenderer fontRenderer, int x, int y, int mouseX, int mouseY, int button) {
        return isHovering(fontRenderer, x, y, mouseX, mouseY);
    }

    @Override
    public void mouseReleased(FontRenderer fontRenderer, int x, int y, int mouseX, int mouseY, int button) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public boolean isHovering(FontRenderer fontRenderer, int x, int y, int mouseX, int mouseY) {
        return mouseX >= x & mouseY >= y & mouseX <= x + fontRenderer.getStringWidth(displayText) & mouseY <= y + 8;
    }

    @Override
    public String getDisplayName() {
        return displayText;
    }
}
