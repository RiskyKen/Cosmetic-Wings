package riskyken.cosmeticWings.client.gui;

import net.minecraft.client.gui.Gui;
import riskyken.cosmeticWings.client.gui.controls.GuiCheckBox;
import riskyken.cosmeticWings.client.gui.controls.GuiCustomSlider;
import riskyken.cosmeticWings.client.gui.controls.GuiHelper;
import cpw.mods.fml.client.config.GuiSlider;
import cpw.mods.fml.client.config.GuiSlider.ISlider;

public class GuiTabWingLocation extends GuiTabPage implements ISlider {

    public GuiCheckBox checkSpawnParticles;
    public GuiCustomSlider sliderScale;
    public GuiCustomSlider sliderOffset;
    public GuiCustomSlider sliderHightOffset;
    
    public GuiTabWingLocation(Gui parent, int x, int y) {
        super(parent, x, y);
    }
    
    @Override
    public void initGui() {
        checkSpawnParticles = new GuiCheckBox(1, this.x + 5, this.y + 20, 14, 14, GuiHelper.getLocalizedControlName("wings", "spawnParticles.name"), false, false);
        sliderScale = new GuiCustomSlider(2, this.x + 5, this.y + 50, 120, 10, "", "", 0.4D, 1D, 1D, true, true, this);
        sliderOffset = new GuiCustomSlider(3, this.x + 5, this.y + 70, 120, 10, "", "", 0D, 1D, 1D, true, true, this);
        sliderHightOffset = new GuiCustomSlider(4, this.x + 5, this.y + 90, 120, 10, "", "", 0D, 1D, 1D, true, true, this);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float tickTime) {
        String scaleLabel = GuiHelper.getLocalizedControlName("wings", "label.scale");
        String centreLabel = GuiHelper.getLocalizedControlName("wings", "label.centre");
        String heightLabel = GuiHelper.getLocalizedControlName("wings", "label.height");
        
        this.fontRendererObj.drawString(scaleLabel, this.x + 5, this.y + 41, 4210752);
        this.fontRendererObj.drawString(centreLabel, this.x + 5, this.y + 61, 4210752);
        this.fontRendererObj.drawString(heightLabel, this.x + 5, this.y + 81, 4210752);
        
        checkSpawnParticles.drawButton(mc, mouseX, mouseY);
        sliderScale.drawButton(mc, mouseX, mouseY);
        sliderOffset.drawButton(mc, mouseX, mouseY);
        sliderHightOffset.drawButton(mc, mouseX, mouseY);
    }
    
    @Override
    public void mouseMovedOrUp(int mouseX, int mouseY, int button) {
        checkSpawnParticles.mouseReleased(mouseX, mouseY);
        sliderScale.mouseReleased(mouseX, mouseY);
        sliderOffset.mouseReleased(mouseX, mouseY);
        sliderHightOffset.mouseReleased(mouseX, mouseY);
    }
    
    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (checkSpawnParticles.mousePressed(mc, mouseX, mouseY)) {
            checkSpawnParticles.func_146113_a(mc.getSoundHandler());
            checkSpawnParticles.setChecked(!checkSpawnParticles.isChecked());
            this.actionPerformed(checkSpawnParticles);
            ((GuiWings)parent).sendWingDataToServer();
        }
        if (sliderScale.mousePressed(mc, mouseX, mouseY)) {
            sliderScale.func_146113_a(mc.getSoundHandler());
            this.actionPerformed(sliderScale);
        }
        if (sliderOffset.mousePressed(mc, mouseX, mouseY)) {
            sliderOffset.func_146113_a(mc.getSoundHandler());
            this.actionPerformed(sliderOffset);
        }
        if (sliderHightOffset.mousePressed(mc, mouseX, mouseY)) {
            sliderHightOffset.func_146113_a(mc.getSoundHandler());
            this.actionPerformed(sliderHightOffset);
        }
    }
    
    
    @Override
    public void onChangeSliderValue(GuiSlider slider) {
        ((ISlider)parent).onChangeSliderValue(slider);
    }
}
