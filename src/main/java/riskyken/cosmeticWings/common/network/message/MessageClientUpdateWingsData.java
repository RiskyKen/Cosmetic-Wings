package riskyken.cosmeticWings.common.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import riskyken.cosmeticWings.common.handler.WingDataHandler;
import riskyken.cosmeticWings.common.wings.WingsData;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
/**
 * Sent from a client to the server when they update their wing data.
 * @author RiskyKen
 *
 */
public class MessageClientUpdateWingsData implements IMessage, IMessageHandler<MessageClientUpdateWingsData, IMessage> {

    WingsData wingData;

    public MessageClientUpdateWingsData() {
    }

    public MessageClientUpdateWingsData(WingsData wingData) {
        this.wingData = wingData;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        this.wingData.toBytes(buf);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.wingData = new WingsData(buf);
    }

    @Override
    public IMessage onMessage(MessageClientUpdateWingsData message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        WingDataHandler.gotWingDataFromPlayer(player, message.wingData);
        return null;
    }
}
