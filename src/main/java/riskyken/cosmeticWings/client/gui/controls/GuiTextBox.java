package riskyken.cosmeticWings.client.gui.controls;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.client.config.GuiButtonExt;
import cpw.mods.fml.client.config.GuiUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTextBox extends GuiButtonExt {

    private ArrayList<String> displayLines;
    
    public GuiTextBox(int id, int xPos, int yPos, int width, int height) {
        super(id, xPos, yPos, width, height, "");
        this.displayLines = new ArrayList<String>();
    }
    
    public void clearDisplayLines() {
        displayLines.clear();
    }
    
    public void addDisplayLine(String lineText) {
        displayLines.add(lineText);
    }
    
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (!this.visible) {
            return;
        }
        GuiUtils.drawContinuousTexturedBox(buttonTextures, this.xPosition, this.yPosition, 0, 46, this.width, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
        
        int textColour = 16777215;
        int pad = 2;
        for (int i = 0; i < displayLines.size(); i++) {
            mc.fontRenderer.drawString(displayLines.get(i),
                    this.xPosition + pad, this.yPosition + pad + mc.fontRenderer.FONT_HEIGHT * i, textColour);
        }
    }
}
