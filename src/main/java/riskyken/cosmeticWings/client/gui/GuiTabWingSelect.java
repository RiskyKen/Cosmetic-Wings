package riskyken.cosmeticWings.client.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.StatCollector;
import riskyken.cosmeticWings.client.gui.controls.GuiDropDownList;
import riskyken.cosmeticWings.client.gui.controls.GuiDropDownList.IDropDownListCallback;
import riskyken.cosmeticWings.client.gui.controls.GuiHelper;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.common.wings.IWings;
import riskyken.cosmeticWings.common.wings.WingsRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTabWingSelect extends GuiTabPage implements IDropDownListCallback {

    public GuiDropDownList dropDownList;
    
    public GuiTabWingSelect(Gui parent, int x, int y) {
        super(parent, x, y);
    }
    
    @Override
    public void initGui() {
        buttonList.clear();
        dropDownList = new GuiDropDownList(0, this.x + 3, this.y + 28, 120, "", this);
        
        WingsRegistry wr = WingsRegistry.INSTANCE;
        String unlocalizedName = "wings." + LibModInfo.ID.toLowerCase() + ":none.name";
        String localizedName = StatCollector.translateToLocal(unlocalizedName);
        
        dropDownList.addListItem(localizedName, "", true);
        dropDownList.setListSelectedIndex(0);
        ArrayList<IWings> wingTypes = wr.getRegisteredWingTypes();
        for (int i = 0; i < wingTypes.size(); i++) {
            IWings wingType = wingTypes.get(i);
            dropDownList.addListItem(wingType.getLocalizedName(), wingType.getRegistryName(), true);
            if (wingType == ((GuiWings)parent).wingsData.wingType) {
                dropDownList.setListSelectedIndex(i + 1);
            }
        }
        buttonList.add(dropDownList);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float tickTime) {
        super.drawScreen(mouseX, mouseY, tickTime);
        String listLabel = GuiHelper.getLocalizedControlName("wings", "label.list");
        this.fontRendererObj.drawString(listLabel, this.x + 2, this.y + 18, 4210752);
    }

    @Override
    public void onDropDownListChanged(GuiDropDownList dropDownList) {
        ((IDropDownListCallback)parent).onDropDownListChanged(dropDownList);
    }
}
