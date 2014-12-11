package riskyken.cosmeticWings.client.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.StatCollector;
import riskyken.cosmeticWings.client.gui.controls.GuiFileListItem;
import riskyken.cosmeticWings.client.gui.controls.GuiHelper;
import riskyken.cosmeticWings.client.gui.controls.GuiList;
import riskyken.cosmeticWings.client.gui.controls.GuiScrollbar;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.common.wings.IWings;
import riskyken.cosmeticWings.common.wings.WingsRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTabWingSelect extends GuiTabPage {

    public GuiList fileList;
    public GuiScrollbar scrollbar;
    
    public GuiTabWingSelect(Gui parent, int x, int y) {
        super(parent, x, y);
    }
    
    @Override
    public void initGui() {
        fileList = new GuiList(this.x + 3, this.y + 28, 120, 76, 12);
        WingsRegistry wr = WingsRegistry.INSTANCE;
        String unlocalizedName = "wings." + LibModInfo.ID.toLowerCase() + ":none.name";
        String localizedName = StatCollector.translateToLocal(unlocalizedName);
        fileList.addListItem(new GuiFileListItem(localizedName));
        for (int i = 0; i < wr.getSize(); i++) {
            IWings wings = wr.getWingsForId(i);
            fileList.addListItem(new GuiFileListItem(wings.getLocalizedName()));
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
        WingsRegistry wr = WingsRegistry.INSTANCE;
        
        int hoverNumber = fileList.drawList(mouseX, mouseY, tickTime);
        if (hoverNumber != -1 & hoverNumber < wr.getSize()) {
            IWings wings = wr.getWingsForId(hoverNumber);
            if (wings != null) {
                String flavourText = wings.getFlavourText();
                if (!flavourText.equals("")) {
                    ArrayList<String> hoverText = new ArrayList<String>();
                    hoverText.add(flavourText);
                    drawHoveringText(hoverText, mouseX, mouseY, fontRendererObj);
                }
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
        WingsRegistry wr = WingsRegistry.INSTANCE;
        if (fileList.mouseClicked(mouseX, mouseY, button)) {
            if (fileList.getSelectedIndex() >= 0 & fileList.getSelectedIndex() <= wr.getSize()) {
                ((GuiWings)parent).sendWingDataToServer();
            }
        }
        scrollbar.mousePressed(mc, mouseX, mouseY);
    }
}
