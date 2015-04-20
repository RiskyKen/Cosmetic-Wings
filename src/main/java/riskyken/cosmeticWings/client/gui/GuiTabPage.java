package riskyken.cosmeticWings.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTabPage extends GuiScreen {
    
    protected final Gui parent; 
    protected final int x;
    protected final int y;
    
    public GuiTabPage(Gui parent, int x, int y) {
        mc = Minecraft.getMinecraft();
        fontRendererObj = mc.fontRenderer;
        this.parent = parent;
        this.x = x;
        this.y = y;
    }
    
    public void initGui() {
    }
    
    public void drawScreen(int mouseX, int mouseY, float tickTime) {
        super.drawScreen(mouseX, mouseY, tickTime);
    }
    
    public void mouseMovedOrUp(int mouseX, int mouseY, int button) {
        super.mouseMovedOrUp(mouseX, mouseY, button);
    }
    
    public void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
    }
    
    public void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long timeSinceMouseClick) {
        super.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceMouseClick);
    }
}
