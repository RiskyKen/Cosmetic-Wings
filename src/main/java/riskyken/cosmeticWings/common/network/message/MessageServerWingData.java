package riskyken.cosmeticWings.common.network.message;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

import riskyken.cosmeticWings.CosmeticWings;
import riskyken.cosmeticWings.common.network.ByteBufHelper;
import riskyken.cosmeticWings.common.wings.WingData;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageServerWingData implements IMessage, IMessageHandler<MessageServerWingData, IMessage> {

    UUID playerId;
    WingData wingData;

    public MessageServerWingData() {}
    
    public MessageServerWingData(UUID playerId, WingData wingData) {
        this.playerId = playerId;
        this.wingData = wingData;
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufHelper.writeUUID(buf, this.playerId);
        this.wingData.toBytes(buf);
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerId = ByteBufHelper.readUUID(buf);
        this.wingData = new WingData(buf);
    }
    
    @Override
    public IMessage onMessage(MessageServerWingData message, MessageContext ctx) {
        CosmeticWings.proxy.receivedWingData(message.playerId, message.wingData);
        return null;
    }
}
