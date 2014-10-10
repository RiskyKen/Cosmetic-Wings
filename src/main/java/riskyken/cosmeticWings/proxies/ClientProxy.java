package riskyken.cosmeticWings.proxies;

import java.util.UUID;

import riskyken.cosmeticWings.client.handler.KeyboardHandler;
import riskyken.cosmeticWings.client.settings.Keybindings;
import riskyken.cosmeticWings.client.wings.WingRenderManager;
import riskyken.cosmeticWings.common.wings.WingData;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    
    private WingRenderManager wingRenderManager;
    
    @Override
    public void init() {
    }

    @Override
    public void initRenderers() {
    }

    @Override
    public void postInit() {
        wingRenderManager = new WingRenderManager();
        new KeyboardHandler();
    }

    @Override
    public void registerKeyBindings() {
        ClientRegistry.registerKeyBinding(Keybindings.openWingGui);
    }
    
    @Override
    public void receivedWingData(UUID playerId, WingData wingData) {
        wingRenderManager.setWingData(playerId, wingData);
    }
}
