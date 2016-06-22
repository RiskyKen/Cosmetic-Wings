package riskyken.cosmeticWings;

import riskyken.cosmeticWings.common.capability.DefaultWingCapability;
import riskyken.cosmeticWings.common.capability.IWingCapability;
import riskyken.cosmeticWings.common.capability.WingStorage;
import riskyken.cosmeticWings.common.handler.WingDataHandler;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.common.network.GuiHandler;
import riskyken.cosmeticWings.common.network.PacketHandler;
import riskyken.cosmeticWings.common.wings.WingsRegistry;
import riskyken.cosmeticWings.proxies.CommonProxy;
import riskyken.cosmeticWings.utils.ModLogger;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = LibModInfo.ID, name = LibModInfo.NAME, version = LibModInfo.VERSION)
public class CosmeticWings {

    @Mod.Instance(LibModInfo.ID)
    public static CosmeticWings instance;

    @SidedProxy(clientSide = LibModInfo.PROXY_CLIENT_CLASS, serverSide = LibModInfo.PROXY_COMMNON_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void perInit(FMLPreInitializationEvent event) {
        ModLogger.log("Loading " + LibModInfo.NAME + " " + LibModInfo.VERSION);
        CapabilityManager.INSTANCE.register(IWingCapability.class, new WingStorage(), DefaultWingCapability.class);
        proxy.preInit();
        WingsRegistry.init();
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
        PacketHandler.init();
        proxy.init();
        new GuiHandler();
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }
    
    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        WingDataHandler.init();
    }
}
