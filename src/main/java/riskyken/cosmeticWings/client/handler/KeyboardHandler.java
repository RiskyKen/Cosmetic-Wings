package riskyken.cosmeticWings.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import riskyken.cosmeticWings.CosmeticWings;
import riskyken.cosmeticWings.client.settings.Keybindings;

@SideOnly(Side.CLIENT)
public class KeyboardHandler {

    public KeyboardHandler() {
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onKeyInputEvent(InputEvent.KeyInputEvent event) {
        if (Keybindings.openWingsGui.isPressed()) {
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            player.openGui(CosmeticWings.instance, 0, player.worldObj, 0, 0, 0);
        }
    }
}
