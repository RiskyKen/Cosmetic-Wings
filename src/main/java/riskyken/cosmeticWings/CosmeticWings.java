package riskyken.cosmeticWings;

import riskyken.cosmeticWings.common.handler.WingDataHandler;
import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.common.network.GuiHandler;
import riskyken.cosmeticWings.common.network.PacketHandler;
import riskyken.cosmeticWings.common.wings.WingsRegistry;
import riskyken.cosmeticWings.proxies.CommonProxy;
import riskyken.cosmeticWings.utils.ModLogger;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = LibModInfo.ID, name = LibModInfo.NAME, version = LibModInfo.VERSION)
public class CosmeticWings {

    @Mod.Instance(LibModInfo.ID)
    public static CosmeticWings instance;

    @SidedProxy(clientSide = LibModInfo.PROXY_CLIENT_CLASS, serverSide = LibModInfo.PROXY_COMMNON_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void perInit(FMLPreInitializationEvent event) {
        ModLogger.log("Loading " + LibModInfo.NAME + " " + LibModInfo.VERSION);
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
        new WingDataHandler();
    }
}
