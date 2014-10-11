package riskyken.cosmeticWings.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import riskyken.cosmeticWings.client.gui.controls.GuiCheckBox;
import riskyken.cosmeticWings.client.gui.controls.GuiFileListItem;
import riskyken.cosmeticWings.client.gui.controls.GuiHelper;
import riskyken.cosmeticWings.client.gui.controls.GuiList;
import riskyken.cosmeticWings.client.gui.controls.GuiScrollbar;
import riskyken.cosmeticWings.client.wings.WingRenderManager;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.common.network.PacketHandler;
import riskyken.cosmeticWings.common.network.message.MessageClientUpdateWingData;
import riskyken.cosmeticWings.common.wings.WingData;
import riskyken.cosmeticWings.common.wings.WingType;
import cpw.mods.fml.client.config.GuiSlider;
import cpw.mods.fml.client.config.GuiSlider.ISlider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiWings extends GuiScreen implements ISlider {

    private static final ResourceLocation wingsGuiTexture = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/gui/wings.png");

    private EntityPlayer player;

    private final int guiWidth;
    private final int guiHeight;
    private int guiLeft;
    private int guiTop;

    private GuiList fileList;
    private GuiScrollbar scrollbar;
    private GuiCheckBox checkSpawnParticles;
    private GuiSlider sliderScale;
    
    WingData wingData;
    
    public GuiWings(EntityPlayer player) {
        this.player = player;
        guiWidth = 256;
        guiHeight = 167;
    }

    @Override
    public void initGui() {
        super.initGui();
        wingData = WingRenderManager.INSTANCE.getPlayerWingData(player.getUniqueID());
        guiLeft = width / 2 - guiWidth / 2;
        guiTop = height / 2 - guiHeight / 2;

        buttonList.clear();
        fileList = new GuiList(this.guiLeft + 7, this.guiTop + 33, 80, 126, 12);
        for (int i = 0; i < WingType.values().length; i++) {
            fileList.addListItem(new GuiFileListItem(WingType.getOrdinal(i).getLocalizedName()));
        }
        
        scrollbar = new GuiScrollbar(0, this.guiLeft + 87, this.guiTop + 33, 10, 126, "", false);
        buttonList.add(scrollbar);
        
        checkSpawnParticles = new GuiCheckBox(1, this.guiLeft + 107, this.guiTop + 59, 14, 14, "Spawn particles?", false, false);
        buttonList.add(checkSpawnParticles);
        
        sliderScale = new GuiSlider(2, this.guiLeft + 107, this.guiTop + 33, 138, 20, "", "", 0.4D, 1D, 1D, true, true, this);
        buttonList.add(sliderScale);
        
        if (wingData != null) {
            fileList.setSelectedIndex(wingData.wingType.ordinal());
            checkSpawnParticles.setChecked(wingData.spawnParticles);
            sliderScale.setValue(wingData.wingScale);
            sliderScale.precision = 2;
            sliderScale.updateSlider();
        } else {
            wingData = new WingData();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float tickTime) {
        mc.renderEngine.bindTexture(wingsGuiTexture);
        drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.guiWidth, this.guiHeight);
        super.drawScreen(mouseX, mouseY, tickTime);
        fileList.setScrollPercentage(scrollbar.getPercentageValue());
        fileList.drawList(mouseX, mouseY, tickTime);

        GuiHelper.renderLocalizedGuiName(this.fontRendererObj, this.guiLeft, this.guiTop, this.guiWidth, "wings");
        
        String listLabel = GuiHelper.getLocalizedControlName("wings", "label.list");
        String scaleLabel = GuiHelper.getLocalizedControlName("wings", "label.scale");
        this.fontRendererObj.drawString(listLabel, this.guiLeft + 7, this.guiTop + 23, 4210752);
        this.fontRendererObj.drawString(scaleLabel, this.guiLeft + 107, this.guiTop + 23, 4210752);
        
        int boxX = this.guiLeft + 175;
        int boxY = this.guiTop + 150;
        
        float lookX = -boxX + mouseX;
        float lookY = boxY - 50 - mouseY;
        GL11.glPushMatrix();
        GL11.glTranslatef(0, boxY, boxX);
        GL11.glRotatef(180, 0, 1, 0);
        
        GL11.glPushMatrix();
        GuiInventory.func_147046_a(-boxX, 0, 29, lookX, lookY, this.mc.thePlayer);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int button) {
        super.mouseMovedOrUp(mouseX, mouseY, button);
        fileList.mouseMovedOrUp(mouseX, mouseY, button);
        scrollbar.mouseReleased(mouseX, mouseY);
    }
    
    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 1) {
            checkSpawnParticles.setChecked(!checkSpawnParticles.isChecked());
            sendWingDataToServer();
        }
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);

        if (fileList.mouseClicked(mouseX, mouseY, button)) {
            if (fileList.getSelectedIndex() >= 0 & fileList.getSelectedIndex() < WingType.values().length) {
                sendWingDataToServer();
            }
        }
        scrollbar.mousePressed(mc, mouseX, mouseY);
    }
    
    private void sendWingDataToServer() {
        wingData.wingType = WingType.getOrdinal(fileList.getSelectedIndex());
        wingData.wingScale = (float) sliderScale.getValue();
        wingData.spawnParticles = checkSpawnParticles.isChecked();
        PacketHandler.networkWrapper.sendToServer(new MessageClientUpdateWingData(wingData));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onChangeSliderValue(GuiSlider slider) {
        if (slider.getValue() != wingData.wingScale) {
            sendWingDataToServer();
        }
    }
}
