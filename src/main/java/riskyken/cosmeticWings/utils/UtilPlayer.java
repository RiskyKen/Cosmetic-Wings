package riskyken.cosmeticWings.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.player.EntityPlayer;

public final class UtilPlayer {

    public static boolean isLocalPlayer(EntityPlayer player) {
        EntityClientPlayerMP localPlayer = Minecraft.getMinecraft().thePlayer;
        return player.getUniqueID().equals(localPlayer.getUniqueID());
    }
    
    public static boolean localPlayerHasGuiOpen() {
        Minecraft mc = Minecraft.getMinecraft();
        return mc.currentScreen != null && mc.currentScreen.getClass() != GuiChat.class;
    }
}
