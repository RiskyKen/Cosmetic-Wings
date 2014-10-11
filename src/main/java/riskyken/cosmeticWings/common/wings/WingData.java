package riskyken.cosmeticWings.common.wings;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class WingData {

    private static final String TAG_WING_TYPE = "wingType";
    private static final String TAG_WING_SCALE = "wingScale";
    private static final String TAG_SPAWN_PARTICLES = "spawnParticles";

    public WingType wingType;
    public float wingScale;
    public boolean spawnParticles;

    public WingData() {
        this.wingType = WingType.NONE;
        this.wingScale = 0.75F;
        this.spawnParticles = true;
    }

    public WingData(ByteBuf buf) {
        fromBytes(buf);
    }

    public void saveNBTData(NBTTagCompound compound) {
        compound.setByte(TAG_WING_TYPE, (byte) this.wingType.ordinal());
        compound.setFloat(TAG_WING_SCALE, this.wingScale);
        compound.setBoolean(TAG_SPAWN_PARTICLES, this.spawnParticles);
    }

    public void loadNBTData(NBTTagCompound compound) {
        this.wingType = WingType.values()[compound.getByte(TAG_WING_TYPE)];
        this.wingScale = compound.getFloat(TAG_WING_SCALE);
        this.spawnParticles = compound.getBoolean(TAG_SPAWN_PARTICLES);
    }

    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.wingType.ordinal());
        buf.writeFloat(this.wingScale);
        buf.writeBoolean(this.spawnParticles);
    }

    private void fromBytes(ByteBuf buf) {
        this.wingType = WingType.values()[buf.readByte()];
        this.wingScale = buf.readFloat();
        this.spawnParticles = buf.readBoolean();
    }
}
