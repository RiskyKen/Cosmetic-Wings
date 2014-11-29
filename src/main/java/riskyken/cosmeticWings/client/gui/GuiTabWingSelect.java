package riskyken.cosmeticWings.client.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.Gui;
import riskyken.cosmeticWings.client.gui.controls.GuiFileListItem;
import riskyken.cosmeticWings.client.gui.controls.GuiHelper;
import riskyken.cosmeticWings.client.gui.controls.GuiList;
import riskyken.cosmeticWings.client.gui.controls.GuiScrollbar;
import riskyken.cosmeticWings.common.wings.WingType;

public class GuiTabWingSelect extends GuiTabPage {

    public GuiList fileList;
    public GuiScrollbar scrollbar;
    
    public GuiTabWingSelect(Gui parent, int x, int y) {
        super(parent, x, y);
    }
    
    @Override
    public void initGui() {
        fileList = new GuiList(this.x + 3, this.y + 28, 120, 76, 12);
        for (int i = 0; i < WingType.values().length; i++) {
            fileList.addListItem(new GuiFileListItem(WingType.getOrdinal(i).getLocalizedName()));
        }
        scrollbar = new GuiScrollbar(0, this.x + 123, this.y + 28, 10, 76, "", false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float tickTime) {
        String listLabel = GuiHelper.getLocalizedControlName("wings", "label.list");
        this.fontRendererObj.drawString(listLabel, this.x + 2, this.y + 18, 4210752);
        scrollbar.drawButton(mc, mouseX, mouseY);
        fileList.drawList(mouseX, mouseY, tickTime);
        fileList.setScrollPercentage(scrollbar.getPercentageValue());
        
        int hoverNumber = fileList.drawList(mouseX, mouseY, tickTime);
        if (hoverNumber != -1 & hoverNumber < WingType.values().length) {
            WingType wingType = WingType.getOrdinal(hoverNumber);
            String flavourText = wingType.getFlavourText();
            if (!flavourText.equals("")) {
                ArrayList<String> hoverText = new ArrayList<String>();
                hoverText.add(flavourText);
                drawHoveringText(hoverText, mouseX, mouseY, fontRendererObj);
            }
        }
    }
    
    @Override
    public void mouseMovedOrUp(int mouseX, int mouseY, int button) {
        fileList.mouseMovedOrUp(mouseX, mouseY, button);
        scrollbar.mouseReleased(mouseX, mouseY);
    }
    
    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (fileList.mouseClicked(mouseX, mouseY, button)) {
            if (fileList.getSelectedIndex() >= 0 & fileList.getSelectedIndex() < WingType.values().length) {
                ((GuiWings)parent).sendWingDataToServer();
            }
        }
        scrollbar.mousePressed(mc, mouseX, mouseY);
    }
}
