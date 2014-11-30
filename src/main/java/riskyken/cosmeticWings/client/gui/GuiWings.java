package riskyken.cosmeticWings.client.gui;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import riskyken.cosmeticWings.client.gui.controls.GuiHSBSlider;
import riskyken.cosmeticWings.client.gui.controls.GuiHSBSlider.IHSBSliderCallback;
import riskyken.cosmeticWings.client.gui.controls.GuiHelper;
import riskyken.cosmeticWings.client.render.WingRenderManager;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.common.network.PacketHandler;
import riskyken.cosmeticWings.common.network.message.MessageClientUpdateWingData;
import riskyken.cosmeticWings.common.wings.WingData;
import riskyken.cosmeticWings.common.wings.WingType;
import riskyken.cosmeticWings.utils.UtilColour;
import cpw.mods.fml.client.config.GuiSlider;
import cpw.mods.fml.client.config.GuiSlider.ISlider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiWings extends GuiScreen implements ISlider, IHSBSliderCallback {

    private static final ResourceLocation wingsGuiTexture = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/gui/wings.png");

    private EntityPlayer player;

    private final int guiWidth;
    private final int guiHeight;
    private int guiLeft;
    private int guiTop;

    private ArrayList<GuiTabPage> tabs;
    private GuiTabWingSelect tab1;
    private GuiTabWingLocation tab2;
    private GuiTabWingColour tab3;
    private int activeTab;
    
    WingData wingData;
    
    public GuiWings(EntityPlayer player) {
        this.player = player;
        guiWidth = 256;
        guiHeight = 116;
        tabs = new ArrayList<GuiTabPage>();

        activeTab = 0;
    }

    @Override
    public void initGui() {
        super.initGui();
        wingData = WingRenderManager.INSTANCE.getPlayerWingData(player.getUniqueID());
        guiLeft = width / 2 - guiWidth / 2;
        guiTop = height / 2 - guiHeight / 2;

        buttonList.clear();
        tabs.clear();
        
        tab1 = new GuiTabWingSelect(this, this.guiLeft + 5, this.guiTop + 5);
        tab2 = new GuiTabWingLocation(this, this.guiLeft + 5, this.guiTop + 5);
        tab3 = new GuiTabWingColour(this, this.guiLeft + 5, this.guiTop + 5);
        tabs.add(tab1);
        tabs.add(tab2);
        tabs.add(tab3);
        
        if (wingData != null) {
            tab3.colour = new Color(wingData.colour);
        } else {
            tab3.colour = new Color(UtilColour.getMinecraftColor(0));
        }
        
        for (int i = 0; i < tabs.size(); i++) {
            tabs.get(i).initGui();
        }

        if (wingData != null) {
            tab1.fileList.setSelectedIndex(wingData.wingType.ordinal());
            
            tab2.checkSpawnParticles.setChecked(wingData.spawnParticles);
            tab2.sliderScale.setValue(wingData.wingScale);
            tab2.sliderScale.precision = 2;
            tab2.sliderScale.updateSlider();
            
            tab2.sliderOffset.setValue(wingData.centreOffset);
            tab2.sliderOffset.precision = 2;
            tab2.sliderOffset.updateSlider();
            
            tab2.sliderHightOffset.setValue(wingData.heightOffset);
            tab2.sliderHightOffset.precision = 2;
            tab2.sliderHightOffset.updateSlider();
        } else {
            wingData = new WingData();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float tickTime) {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        mc.renderEngine.bindTexture(wingsGuiTexture);
        drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.guiWidth, this.guiHeight);
        
        //Draw the tabs
        for (int i = 0; i < tabs.size(); i++) {
            if (i == activeTab) {
                drawTexturedModalRect(this.guiLeft - 20, this.guiTop + 5 + i * 22, 20, 136, 23, 23);
            } else {
                drawTexturedModalRect(this.guiLeft - 20, this.guiTop + 5 + i * 22, 0, 136, 20, 20);
            }
            drawTexturedModalRect(this.guiLeft - 16, this.guiTop + 8 + i * 22, 1, 165 + 15 * i, 14, 14);
        }
        
        super.drawScreen(mouseX, mouseY, tickTime);
        GuiHelper.renderLocalizedGuiName(this.fontRendererObj, this.guiLeft, this.guiTop, this.guiWidth, "wings");
        
        for (int i = 0; i < tabs.size(); i++) {
            if (i == activeTab) {
                tabs.get(i).drawScreen(mouseX, mouseY, tickTime);
            }
        }
        
        int boxX = this.guiLeft + 195;
        int boxY = this.guiTop + 100;
        
        float lookX = -boxX + mouseX;
        float lookY = boxY - 50 - mouseY;
        GL11.glPushMatrix();
        GL11.glTranslatef(0, boxY, boxX);
        GL11.glRotatef(180, 0, 1, 0);
        
        GL11.glPushMatrix();
        GuiInventory.func_147046_a(-boxX, 0, 28, lookX, lookY, this.mc.thePlayer);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int button) {
        if (button == 0) {
            for (int i = 0; i < tabs.size(); i++) {
                if (mouseX >= (this.guiLeft - 20) & mouseX <= this.guiLeft) {
                    int top = i * 22;
                    if (mouseY >= this.guiTop + 5 + top & mouseY <= (this.guiTop + 24 + top)) {
                        activeTab = i;
                    }
                }
            }
        }
        
        for (int i = 0; i < tabs.size(); i++) {
            if (i == activeTab) {
                tabs.get(i).mouseMovedOrUp(mouseX, mouseY, button);
            }
        }
        
        super.mouseMovedOrUp(mouseX, mouseY, button);
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        
        for (int i = 0; i < tabs.size(); i++) {
            if (i == activeTab) {
                tabs.get(i).mouseClicked(mouseX, mouseY, button);
            }
        }
    }
    
    public void sendWingDataToServer() {
        wingData.wingType = WingType.getOrdinal(tab1.fileList.getSelectedIndex());
        wingData.wingScale = (float) tab2.sliderScale.getValue();
        wingData.spawnParticles = tab2.checkSpawnParticles.isChecked();
        wingData.centreOffset = (float) tab2.sliderOffset.getValue();
        wingData.heightOffset = (float) tab2.sliderHightOffset.getValue();
        wingData.colour = tab3.colour.getRGB();
        PacketHandler.networkWrapper.sendToServer(new MessageClientUpdateWingData(wingData));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onChangeSliderValue(GuiSlider slider) { 
        if (slider.id == tab2.sliderScale.id) {
            if (slider.getValue() != wingData.wingScale) {
                sendWingDataToServer();
            }
        }
        if (slider.id == tab2.sliderOffset.id) {
            if (slider.getValue() != wingData.centreOffset) {
                sendWingDataToServer();
            }
        }
        if (slider.id == tab2.sliderHightOffset.id) {
            if (slider.getValue() != wingData.heightOffset) {
                sendWingDataToServer();
            }
        }
    }

    @Override
    public void valueUpdated(GuiHSBSlider source, double sliderValue) {
        sendWingDataToServer();
    }
}
