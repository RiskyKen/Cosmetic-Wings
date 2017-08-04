package riskyken.cosmeticWings.client.gui.controls;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDropDownList extends GuiButtonExt {

    private List<DropDownListItem> listItems;
    private int selectedIndex;
    private int hoverIndex;
    private boolean isMouseDownOver;
    private boolean isDroppedDown;
    private int dropButtonX;
    private int dropButtonY;
    private int dropButtonWidth;
    private int dropButtonHeight;
    private IDropDownListCallback callback;
    
    public GuiDropDownList(int id, int xPos, int yPos, int width, String displayString, IDropDownListCallback callback) {
        super(id, xPos, yPos, width, 14, displayString);
        this.callback = callback;
        this.listItems = new ArrayList<DropDownListItem>();
        this.selectedIndex = 0;
        this.hoverIndex = -1;
        this.isMouseDownOver = false;
        this.isDroppedDown = false;
        this.dropButtonHeight = this.height;
        this.dropButtonWidth = 14;
        this.dropButtonY = this.y;
        this.dropButtonX = this.x + this.width - this.dropButtonWidth;
    }
    
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partial) {
        if (this.visible) {
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int k = this.getHoverState(this.hovered);
            GuiUtils.drawContinuousTexturedBox(BUTTON_TEXTURES, this.x, this.y, 0, 46, this.width, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
            
            drawDropDownButton(mc, mouseX, mouseY);
            
            this.hoverIndex = -1;
            if (this.isDroppedDown) {
                int listSize = listItems.size();
                GuiUtils.drawContinuousTexturedBox(BUTTON_TEXTURES, this.x, this.y + this.height + 1, 0, 46, this.width, 10 * listSize + 4, 200, 20, 2, 3, 2, 2, this.zLevel);
                for (int i = 0; i < listSize; i++) {
                    DropDownListItem listItem = listItems.get(i);
                    int textX = this.x + 4;
                    int textY = this.y + this.height + 4 + (i * 10);
                    int textWidth = this.width - 8;
                    int textHeight = 8;
                    int textColour = 16777215;
                    if (!listItem.enabled) {
                        textColour = 0xFFCC0000;
                    } else {
                        if (mouseX >= textX && mouseY >= textY && mouseX < textX + textWidth && mouseY < textY + textHeight) {
                            if (listItem.enabled) {
                                textColour = 16777120;
                                this.hoverIndex = i;
                                drawRect(textX, textY, textX + textWidth, textY + textHeight, 0x44CCCCCC);
                            }
                        }
                    }
                    mc.fontRenderer.drawString(listItem.displayText, textX, textY, textColour);
                } 
            }
            
            mc.fontRenderer.drawString(this.displayString, this.x + 3, this.y + 3, 16777215);
        }
    }
    
    private boolean mouseOverDropDownButton(int mouseX, int mouseY) {
        return mouseX >= this.dropButtonX && mouseY >= this.dropButtonY && mouseX < this.dropButtonX + this.dropButtonWidth && mouseY < this.dropButtonY + this.dropButtonHeight;
    }
    
    private void drawDropDownButton(Minecraft mc, int mouseX, int mouseY) {
        int k = this.getHoverState(mouseOverDropDownButton(mouseX, mouseY));
        GuiUtils.drawContinuousTexturedBox(BUTTON_TEXTURES, this.dropButtonX, this.dropButtonY, 0, 46 + k * 20, this.dropButtonWidth, this.dropButtonHeight, 200, 20, 2, 3, 2, 2, this.zLevel);
        
        String dropDownArrow = "v";
        if (isDroppedDown) {
            dropDownArrow = "^";
        }
        int arrowWidth = mc.fontRenderer.getStringWidth(dropDownArrow);
        mc.fontRenderer.drawString(dropDownArrow, this.dropButtonX + (this.dropButtonWidth / 2) - (arrowWidth / 2), this.dropButtonY + 3, 16777215);
    }
    
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        int oldHeight = this.height;
        if (this.isDroppedDown) {
            this.height += 10 * listItems.size() + 4;
        }
        if (super.mousePressed(mc, mouseX, mouseY)) {
            if (this.isDroppedDown) {
                this.height = oldHeight;
            }
            this.isMouseDownOver = mouseOverDropDownButton(mouseX, mouseY);
            return true;
        } else {
            if (this.isDroppedDown) {
                this.height = oldHeight;
                this.isDroppedDown = false;
            }
            return false;
        }
    }
    
    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        if (this.isMouseDownOver && mouseOverDropDownButton(mouseX, mouseY)) {
            this.isDroppedDown = !this.isDroppedDown;
            this.isMouseDownOver = false;
        }
        if (hoverIndex != -1) {
            setListSelectedIndex(hoverIndex);
            this.isDroppedDown = false;
            if (this.callback != null) {
                this.callback.onDropDownListChanged(this);
            }
        }
    }
    
    public void clearList() {
        listItems.clear();
    }
    
    public void addListItem(DropDownListItem listItem) {
        listItems.add(listItem);
    }
    
    public void addListItem(String displayText) {
        addListItem(displayText, "", true);
    }
    
    public void addListItem(String displayText, String tag, boolean enabled) {
        listItems.add(new DropDownListItem(displayText, tag, enabled));
    }
    
    public DropDownListItem getListIndex(int index) {
        return this.listItems.get(index);
    }
    
    public int getListSelectedIndex() {
        return selectedIndex;
    }
    
    public DropDownListItem getListSelectedItem() {
        return this.listItems.get(this.selectedIndex);
    }
    
    public void setListSelectedIndex(int index) {
        this.selectedIndex = index;
        this.displayString = listItems.get(this.selectedIndex).displayText;
    }
    
    public int getListSize() {
        return this.listItems.size();
    }
    
    public interface IDropDownListCallback {
        public void onDropDownListChanged(GuiDropDownList dropDownList);
    }
    
    public class DropDownListItem {
        public String displayText;
        public String tag;
        public boolean enabled;
        
        public DropDownListItem(String displayText, String tag, boolean enabled) {
            this.displayText = displayText;
            this.tag = tag;
            this.enabled = enabled;
        }
    }
}
