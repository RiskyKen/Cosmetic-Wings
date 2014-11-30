package riskyken.cosmeticWings.client.gui;

import java.awt.Color;

import net.minecraft.client.gui.Gui;
import riskyken.cosmeticWings.client.gui.controls.GuiHSBSlider;
import riskyken.cosmeticWings.client.gui.controls.GuiHSBSlider.HSBSliderType;
import riskyken.cosmeticWings.client.gui.controls.GuiHSBSlider.IHSBSliderCallback;
import riskyken.cosmeticWings.client.gui.controls.GuiHelper;

public class GuiTabWingColour extends GuiTabPage implements IHSBSliderCallback {

    public Color colour = new Color(0);
    private GuiHSBSlider[] slidersHSB;
    
    public GuiTabWingColour(Gui parent, int x, int y) {
        super(parent, x, y);
    }

    @Override
    public void initGui() {
        float[] hsbvals = Color.RGBtoHSB(colour.getRed(), colour.getGreen(), colour.getBlue(), null);
        slidersHSB = new GuiHSBSlider[3];
        slidersHSB[0] = new GuiHSBSlider(0, this.x + 5, this.y + 50, 128, 10, this, HSBSliderType.HUE, hsbvals[0], hsbvals[0], hsbvals[2]);
        slidersHSB[1] = new GuiHSBSlider(1, this.x + 5, this.y + 70, 128, 10, this, HSBSliderType.SATURATION, hsbvals[1], hsbvals[0], hsbvals[2]);
        slidersHSB[2] = new GuiHSBSlider(2, this.x + 5, this.y + 90, 128, 10, this, HSBSliderType.BRIGHTNESS, hsbvals[2], hsbvals[0], hsbvals[2]);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float tickTime) {
        String hueLabel = GuiHelper.getLocalizedControlName("wings", "label.hue");
        String saturationLabel = GuiHelper.getLocalizedControlName("wings", "label.saturation");
        String brightnessLabel = GuiHelper.getLocalizedControlName("wings", "label.brightness");
        
        this.fontRendererObj.drawString(hueLabel, this.x + 5, this.y + 41, 4210752);
        this.fontRendererObj.drawString(saturationLabel, this.x + 5, this.y + 61, 4210752);
        this.fontRendererObj.drawString(brightnessLabel, this.x + 5, this.y + 81, 4210752);
        
        slidersHSB[0].drawButton(mc, mouseX, mouseY);
        slidersHSB[1].drawButton(mc, mouseX, mouseY);
        slidersHSB[2].drawButton(mc, mouseX, mouseY);
    }
    
    @Override
    public void mouseMovedOrUp(int mouseX, int mouseY, int button) {
        slidersHSB[0].mouseReleased(mouseX, mouseY);
        slidersHSB[1].mouseReleased(mouseX, mouseY);
        slidersHSB[2].mouseReleased(mouseX, mouseY);
    }
    
    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        if (slidersHSB[0].mousePressed(mc, mouseX, mouseY)) {
            slidersHSB[0].func_146113_a(mc.getSoundHandler());
            this.actionPerformed(slidersHSB[0]);
        }
        if (slidersHSB[1].mousePressed(mc, mouseX, mouseY)) {
            slidersHSB[1].func_146113_a(mc.getSoundHandler());
            this.actionPerformed(slidersHSB[1]);
        }
        if (slidersHSB[2].mousePressed(mc, mouseX, mouseY)) {
            slidersHSB[2].func_146113_a(mc.getSoundHandler());
            this.actionPerformed(slidersHSB[2]);
        }
    }
    
    @Override
    public void valueUpdated(GuiHSBSlider source, double sliderValue) {
        float[] hsbvals = { (float)slidersHSB[0].getValue(), (float)slidersHSB[1].getValue(), (float)slidersHSB[2].getValue() };
        hsbvals[source.getType().ordinal()] = (float)sliderValue;
        this.colour = Color.getHSBColor(hsbvals[0], hsbvals[1], hsbvals[2]);
        if (source.getType() == HSBSliderType.HUE) {
            slidersHSB[1].setHue((float) source.getValue());
        }
        if (source.getType() == HSBSliderType.BRIGHTNESS) {
            slidersHSB[1].setBrightness((float) source.getValue());
        }
        ((IHSBSliderCallback)parent).valueUpdated(source, sliderValue);
    }
}
