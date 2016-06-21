package riskyken.cosmeticWings.proxies;

import java.util.UUID;

import riskyken.cosmeticWings.client.abstraction.RenderBridge;
import riskyken.cosmeticWings.client.handler.KeyboardHandler;
import riskyken.cosmeticWings.client.particles.ParticleManager;
import riskyken.cosmeticWings.client.render.WingRenderManager;
import riskyken.cosmeticWings.client.settings.Keybindings;
import riskyken.cosmeticWings.client.wings.ClientWingsCache;
import riskyken.cosmeticWings.common.wings.WingsData;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        RenderBridge.init();
    }
    
    @Override
    public void init() {
        new KeyboardHandler();
        registerKeyBindings();
        ClientWingsCache.init();
        initRenderers();
    }

    @Override
    public void postInit() {
    }
    
    @Override
    public void receivedWingsData(UUID playerId, WingsData wingsData) {
        ClientWingsCache.INSTANCE.setWingsData(playerId, wingsData);
    }
    
    private void initRenderers() {
        WingRenderManager.init();
        ParticleManager.init();
    }

    private void registerKeyBindings() {
        ClientRegistry.registerKeyBinding(Keybindings.openWingsGui);
    }
}
