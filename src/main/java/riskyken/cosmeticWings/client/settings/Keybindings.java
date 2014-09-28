package riskyken.cosmeticWings.client.settings;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import riskyken.cosmeticWings.common.lib.LibKeyBindingNames;

public class Keybindings {
    
    public static KeyBinding openWingGui = new KeyBinding(LibKeyBindingNames.WING_GUI, Keyboard.KEY_Y, LibKeyBindingNames.CATEGORY);
}
