package riskyken.cosmeticWings.client.gui;

import net.minecraft.client.gui.Gui;
import riskyken.cosmeticWings.client.gui.controls.GuiCustomSlider;
import riskyken.cosmeticWings.client.gui.controls.GuiHelper;
import cpw.mods.fml.client.config.GuiSlider;
import cpw.mods.fml.client.config.GuiSlider.ISlider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTabWingLocation extends GuiTabPage implements ISlider {

    public GuiCustomSlider sliderparticlesSpawnRate;
    public GuiCustomSlider sliderScale;
    public GuiCustomSlider sliderOffset;
    public GuiCustomSlider sliderHightOffset;
    
    public GuiTabWingLocation(Gui parent, int x, int y) {
        super(parent, x, y);
    }
    
    @Override
    public void initGui() {
        buttonList.clear();
        sliderparticlesSpawnRate = new GuiCustomSlider(1, this.x + 5, this.y + 30, 120, 10, "", "%", 0D, 800D, 100D, true, true, this);
        sliderScale = new GuiCustomSlider(2, this.x + 5, this.y + 50, 120, 10, "", "%", 40D, 100D, 75D, true, true, this);
        sliderOffset = new GuiCustomSlider(3, this.x + 5, this.y + 70, 120, 10, "", "", 0D, 1D, 1D, true, true, this);
        sliderHightOffset = new GuiCustomSlider(4, this.x + 5, this.y + 90, 120, 10, "", "", 0D, 1D, 1D, true, true, this);
        buttonList.add(sliderparticlesSpawnRate);
        buttonList.add(sliderScale);
        buttonList.add(sliderOffset);
        buttonList.add(sliderHightOffset);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float tickTime) {
        super.drawScreen(mouseX, mouseY, tickTime);
        String spawnRateLabel = GuiHelper.getLocalizedControlName("wings", "label.particlesSpawnRate");
        String scaleLabel = GuiHelper.getLocalizedControlName("wings", "label.scale");
        String centreLabel = GuiHelper.getLocalizedControlName("wings", "label.centre");
        String heightLabel = GuiHelper.getLocalizedControlName("wings", "label.height");
        
        this.fontRendererObj.drawString(spawnRateLabel, this.x + 5, this.y + 21, 4210752);
        this.fontRendererObj.drawString(scaleLabel, this.x + 5, this.y + 41, 4210752);
        this.fontRendererObj.drawString(centreLabel, this.x + 5, this.y + 61, 4210752);
        this.fontRendererObj.drawString(heightLabel, this.x + 5, this.y + 81, 4210752);
    }
    
    @Override
    public void onChangeSliderValue(GuiSlider slider) {
        ((ISlider)parent).onChangeSliderValue(slider);
    }
}
