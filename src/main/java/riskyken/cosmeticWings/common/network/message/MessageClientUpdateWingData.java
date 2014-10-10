package riskyken.cosmeticWings.common.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import riskyken.cosmeticWings.common.wings.WingData;
import riskyken.cosmeticWings.common.wings.WingDataManager;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageClientUpdateWingData implements IMessage, IMessageHandler<MessageClientUpdateWingData, IMessage> {

    WingData wingData;

    public MessageClientUpdateWingData() {
    }

    public MessageClientUpdateWingData(WingData wingData) {
        this.wingData = wingData;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        this.wingData.toBytes(buf);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.wingData = new WingData(buf);
    }

    @Override
    public IMessage onMessage(MessageClientUpdateWingData message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        WingDataManager.gotWingDataFromPlayer(player, message.wingData);
        return null;
    }
}
