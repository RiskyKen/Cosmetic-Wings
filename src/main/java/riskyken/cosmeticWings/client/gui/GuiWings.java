package riskyken.cosmeticWings.client.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GL11;

import riskyken.cosmeticWings.client.gui.controls.GuiDropDownList;
import riskyken.cosmeticWings.client.gui.controls.GuiDropDownList.DropDownListItem;
import riskyken.cosmeticWings.client.gui.controls.GuiDropDownList.IDropDownListCallback;
import riskyken.cosmeticWings.client.gui.controls.GuiHSBSlider;
import riskyken.cosmeticWings.client.gui.controls.GuiHSBSlider.IHSBSliderCallback;
import riskyken.cosmeticWings.client.gui.controls.GuiHelper;
import riskyken.cosmeticWings.client.wings.ClientWingsCache;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.common.network.PacketHandler;
import riskyken.cosmeticWings.common.network.message.MessageClientUpdateWingsData;
import riskyken.cosmeticWings.common.wings.WingsData;
import riskyken.cosmeticWings.common.wings.WingsRegistry;
import riskyken.cosmeticWings.utils.ModLogger;
import riskyken.cosmeticWings.utils.UtilColour;
import net.minecraftforge.fml.client.config.GuiSlider;
import net.minecraftforge.fml.client.config.GuiSlider.ISlider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiWings extends GuiScreen implements ISlider, IHSBSliderCallback, IDropDownListCallback {

    private static final ResourceLocation wingsGuiTexture = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/gui/wings.png");

    private EntityPlayer player;

    private final int guiWidth;
    private final int guiHeight;
    private int guiLeft;
    private int guiTop;

    private boolean guiLoading;
    private ArrayList<GuiTabPage> tabs;
    private GuiTabWingSelect tab1;
    private GuiTabWingLocation tab2;
    private GuiTabWingColour tab3;
    private static int activeTab = 0;
    
    public WingsData wingsData;
    
    public GuiWings(EntityPlayer player) {
        this.player = player;
        guiWidth = 256;
        guiHeight = 116;
        tabs = new ArrayList<GuiTabPage>();
    }

    @Override
    public void initGui() {
        guiLoading = true;
        super.initGui();
        wingsData = ClientWingsCache.INSTANCE.getPlayerWingsData(player.getUniqueID());
        if (wingsData == null) {
            ModLogger.log(Level.WARN, String.format("Failed to get player %s wing data.", player.getGameProfile().getName()));
            wingsData = new WingsData();
        }
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
        
        tab3.colour = new Color(wingsData.colour);
        
        for (int i = 0; i < tabs.size(); i++) {
            tabs.get(i).initGui();
        }

        tab2.sliderparticlesSpawnRate.setValue(wingsData.particleSpawnRate * 100);
        tab2.sliderparticlesSpawnRate.precision = 0;
        tab2.sliderparticlesSpawnRate.updateSlider();
        
        tab2.sliderScale.setValue(wingsData.wingScale * 100);
        tab2.sliderScale.precision = 0;
        tab2.sliderScale.updateSlider();
        
        tab2.sliderOffset.setValue(wingsData.centreOffset);
        tab2.sliderOffset.precision = 2;
        tab2.sliderOffset.updateSlider();
        
        tab2.sliderHightOffset.setValue(wingsData.heightOffset);
        tab2.sliderHightOffset.precision = 2;
        tab2.sliderHightOffset.updateSlider();
        
        guiLoading = false;
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
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GlStateManager.resetColor();
        
        GL11.glPushMatrix();
        GuiInventory.drawEntityOnScreen(-boxX, 0, 28, lookX, lookY, this.mc.thePlayer);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
    
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) {
            for (int i = 0; i < tabs.size(); i++) {
                if (mouseX >= (this.guiLeft - 20) & mouseX <= this.guiLeft) {
                    int top = i * 22;
                    if (mouseY >= this.guiTop + 5 + top & mouseY <= (this.guiTop + 24 + top)) {
                        activeTab = i;
                    }
                }
            }
        }
        super.mouseReleased(mouseX, mouseY, state);
        for (int i = 0; i < tabs.size(); i++) {
            if (i == activeTab) {
                tabs.get(i).mouseReleased(mouseX, mouseY, state);
            }
        }
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (int i = 0; i < tabs.size(); i++) {
            if (i == activeTab) {
                tabs.get(i).mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }
    
    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long timeSinceMouseClick) {
        super.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceMouseClick);
        for (int i = 0; i < tabs.size(); i++) {
            if (i == activeTab) {
                tabs.get(i).mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceMouseClick);
            }
        }
    }
    
    public void sendWingDataToServer() {
        if (guiLoading) {
            return;
        }
        DropDownListItem listItem = tab1.dropDownList.getListSelectedItem();
        wingsData.wingType = WingsRegistry.INSTANCE.getWingsFormRegistryName(listItem.tag);
        wingsData.wingScale = (float) tab2.sliderScale.getValue() / 100;
        wingsData.particleSpawnRate = (float) tab2.sliderparticlesSpawnRate.getValue() / 100;
        wingsData.centreOffset = (float) tab2.sliderOffset.getValue();
        wingsData.heightOffset = (float) tab2.sliderHightOffset.getValue();
        wingsData.colour = tab3.colour.getRGB();
        PacketHandler.networkWrapper.sendToServer(new MessageClientUpdateWingsData(wingsData));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onChangeSliderValue(GuiSlider slider) { 
        if (slider.id == tab2.sliderparticlesSpawnRate.id) {
            if (slider.getValue() != wingsData.particleSpawnRate) {
                sendWingDataToServer();
            }
        }
        if (slider.id == tab2.sliderScale.id) {
            if (slider.getValue() != wingsData.wingScale) {
                sendWingDataToServer();
            }
        }
        if (slider.id == tab2.sliderOffset.id) {
            if (slider.getValue() != wingsData.centreOffset) {
                sendWingDataToServer();
            }
        }
        if (slider.id == tab2.sliderHightOffset.id) {
            if (slider.getValue() != wingsData.heightOffset) {
                sendWingDataToServer();
            }
        }
    }

    @Override
    public void valueUpdated(GuiHSBSlider source, double sliderValue) {
        sendWingDataToServer();
    }

    @Override
    public void onDropDownListChanged(GuiDropDownList dropDownList) {
        sendWingDataToServer();
    }
}
