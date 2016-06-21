package riskyken.cosmeticWings.common.network;

import riskyken.cosmeticWings.common.lib.LibModInfo;
import riskyken.cosmeticWings.common.network.message.MessageClientUpdateWingsData;
import riskyken.cosmeticWings.common.network.message.MessageServerWingsData;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    public static final SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(LibModInfo.CHANNEL);

    public static void init() {
        networkWrapper.registerMessage(MessageClientUpdateWingsData.class, MessageClientUpdateWingsData.class, 0, Side.SERVER);
        networkWrapper.registerMessage(MessageServerWingsData.class, MessageServerWingsData.class, 1, Side.CLIENT);
    }
}
