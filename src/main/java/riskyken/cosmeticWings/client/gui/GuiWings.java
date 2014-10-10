package riskyken.cosmeticWings.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import riskyken.cosmeticWings.client.gui.controls.GuiFileListItem;
import riskyken.cosmeticWings.client.gui.controls.GuiHelper;
import riskyken.cosmeticWings.client.gui.controls.GuiList;
import riskyken.cosmeticWings.client.gui.controls.GuiScrollbar;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.common.network.PacketHandler;
import riskyken.cosmeticWings.common.network.message.MessageClientUpdateWingData;
import riskyken.cosmeticWings.common.wings.WingData;
import riskyken.cosmeticWings.common.wings.WingType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiWings extends GuiScreen {

    private static final ResourceLocation wingsGuiTexture = new ResourceLocation(LibModInfo.ID.toLowerCase(), "textures/gui/wings.png");

    private EntityPlayer player;

    private final int guiWidth;
    private final int guiHeight;
    private int guiLeft;
    private int guiTop;

    private GuiList fileList;
    private GuiScrollbar scrollbar;

    public GuiWings(EntityPlayer player) {
        this.player = player;
        guiWidth = 256;
        guiHeight = 167;
    }

    @Override
    public void initGui() {
        super.initGui();
        guiLeft = width / 2 - guiWidth / 2;
        guiTop = height / 2 - guiHeight / 2;

        buttonList.clear();
        fileList = new GuiList(this.guiLeft + 7, this.guiTop + 23, 71, 96, 12);
        for (int i = 0; i < WingType.values().length; i++) {
            fileList.addListItem(new GuiFileListItem(WingType.getOrdinal(i).getLocalizedName()));
        }
        scrollbar = new GuiScrollbar(2, this.guiLeft + 78, this.guiTop + 23, 10, 96, "", false);
        buttonList.add(scrollbar);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float tickTime) {
        mc.renderEngine.bindTexture(wingsGuiTexture);
        drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.guiWidth, this.guiHeight);
        super.drawScreen(mouseX, mouseY, tickTime);
        fileList.setScrollPercentage(scrollbar.getPercentageValue());
        fileList.drawList(mouseX, mouseY, tickTime);

        GuiHelper.renderLocalizedGuiName(this.fontRendererObj, this.guiLeft, this.guiTop, this.guiWidth, "wings");
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int button) {
        super.mouseMovedOrUp(mouseX, mouseY, button);
        fileList.mouseMovedOrUp(mouseX, mouseY, button);
        scrollbar.mouseReleased(mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);

        if (fileList.mouseClicked(mouseX, mouseY, button)) {
            if (fileList.getSelectedIndex() >= 0 & fileList.getSelectedIndex() < WingType.values().length) {

                WingType wingType = WingType.getOrdinal(fileList.getSelectedIndex());
                WingData wingData = new WingData();
                wingData.wingType = wingType;
                PacketHandler.networkWrapper.sendToServer(new MessageClientUpdateWingData(wingData));
            }
        }
        scrollbar.mousePressed(mc, mouseX, mouseY);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
