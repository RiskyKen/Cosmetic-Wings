package riskyken.cosmeticWings.common.network.message;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

import riskyken.cosmeticWings.CosmeticWings;
import riskyken.cosmeticWings.common.network.ByteBufHelper;
import riskyken.cosmeticWings.common.wings.WingsData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/**
 * Send from the server to a client. Contains a players UUID and their wing data.
 * @author RiskyKen
 *
 */
public class MessageServerWingsData implements IMessage, IMessageHandler<MessageServerWingsData, IMessage> {

    UUID playerId;
    WingsData wingsData;

    public MessageServerWingsData() {
    }

    public MessageServerWingsData(UUID playerId, WingsData wingsData) {
        this.playerId = playerId;
        this.wingsData = wingsData;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufHelper.writeUUID(buf, this.playerId);
        this.wingsData.toBytes(buf);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerId = ByteBufHelper.readUUID(buf);
        this.wingsData = new WingsData(buf);
    }

    @Override
    public IMessage onMessage(MessageServerWingsData message, MessageContext ctx) {
        CosmeticWings.proxy.receivedWingsData(message.playerId, message.wingsData);
        return null;
    }
}
