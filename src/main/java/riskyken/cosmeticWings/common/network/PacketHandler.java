package riskyken.cosmeticWings.common.network;

import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.common.network.message.MessageClientUpdateWingData;
import riskyken.cosmeticWings.common.network.message.MessageServerWingData;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {

    public static final SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(LibModInfo.CHANNEL);

    public static void init() {
        networkWrapper.registerMessage(MessageClientUpdateWingData.class, MessageClientUpdateWingData.class, 0, Side.SERVER);
        networkWrapper.registerMessage(MessageServerWingData.class, MessageServerWingData.class, 1, Side.CLIENT);
    }
}
