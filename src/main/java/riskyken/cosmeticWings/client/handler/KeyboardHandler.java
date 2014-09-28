package riskyken.cosmeticWings.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import riskyken.cosmeticWings.CosmeticWings;
import riskyken.cosmeticWings.client.settings.Keybindings;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyboardHandler {
    
    public KeyboardHandler() {
        FMLCommonHandler.instance().bus().register(this);
    }
    
    @SubscribeEvent
    public void onKeyInputEvent(InputEvent.KeyInputEvent event) {
        if (Keybindings.openWingGui.isPressed()) {
            EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
            player.openGui(CosmeticWings.instance, 0, player.worldObj, 0, 0, 0);
        }
    }
}
