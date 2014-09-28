package riskyken.cosmeticWings.common.network.message;

import io.netty.buffer.ByteBuf;
import riskyken.cosmeticWings.CosmeticWings;
import riskyken.cosmeticWings.common.wings.WingData;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageServerWingData implements IMessage, IMessageHandler<MessageServerWingData, IMessage> {

    String playerName;
    WingData wingData;

    public MessageServerWingData() {}
    
    public MessageServerWingData(String playerName, WingData wingData) {
        this.playerName = playerName;
        this.wingData = wingData;
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.playerName);
        this.wingData.toBytes(buf);
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        this.playerName = ByteBufUtils.readUTF8String(buf);
        this.wingData = new WingData(buf);
    }
    
    @Override
    public IMessage onMessage(MessageServerWingData message, MessageContext ctx) {
        CosmeticWings.proxy.receivedWingData(message.playerName, message.wingData);
        return null;
    }
}
