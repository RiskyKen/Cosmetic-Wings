package riskyken.cosmeticWings.common.wings;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
/**
 * Holds all the options a player can select for their wings.
 * All the information in this class is send to players when they
 * enter tracking range.
 * @author RiskyKen
 *
 */
public class WingData {

    private static final String TAG_WING_TYPE = "wingType";
    private static final String TAG_WING_SCALE = "wingScale";
    private static final String TAG_SPAWN_PARTICLES = "spawnParticles";
    private static final String TAG_CENTRE_OFFSET = "centreOffset";
    private static final String TAG_HEIGHT_OFFSET = "heightOffset";

    public WingType wingType;
    public float wingScale;
    public boolean spawnParticles;
    public float centreOffset;
    public float heightOffset;
    
    public WingData() {
        //Default options that will be used for new players.
        this.wingType = WingType.NONE;
        this.wingScale = 0.75F;
        this.spawnParticles = true;
        centreOffset = 0F;
        heightOffset = 0.7F;
    }

    public WingData(ByteBuf buf) {
        fromBytes(buf);
    }

    /**
     * Saves the wings data to an NBTTagCompound.
     * @param compound NBTTagCompound to save to.
     */
    public void saveNBTData(NBTTagCompound compound) {
        compound.setByte(TAG_WING_TYPE, (byte) this.wingType.ordinal());
        compound.setFloat(TAG_WING_SCALE, this.wingScale);
        compound.setBoolean(TAG_SPAWN_PARTICLES, this.spawnParticles);
        compound.setFloat(TAG_CENTRE_OFFSET, this.centreOffset);
        compound.setFloat(TAG_HEIGHT_OFFSET, this.heightOffset);
    }

    /**
     * Loads the wing data from an NBTTagCompound.
     * @param compound NBTTagCompound to load from.
     */
    public void loadNBTData(NBTTagCompound compound) {
        if (compound.hasKey(TAG_WING_TYPE)) {
            this.wingType = WingType.values()[compound.getByte(TAG_WING_TYPE)];
        }
        if (compound.hasKey(TAG_WING_SCALE)) {
            this.wingScale = compound.getFloat(TAG_WING_SCALE);
        }
        if (compound.hasKey(TAG_SPAWN_PARTICLES)) {
            this.spawnParticles = compound.getBoolean(TAG_SPAWN_PARTICLES);
        }
        if (compound.hasKey(TAG_CENTRE_OFFSET)) {
            this.centreOffset = compound.getFloat(TAG_CENTRE_OFFSET);
        }
        if (compound.hasKey(TAG_HEIGHT_OFFSET)) {
            this.heightOffset = compound.getFloat(TAG_HEIGHT_OFFSET);
        }
    }

    /**
     * Writes the wing data to a ByteBuf.
     * @param buf ByteBuf to write to.
     */
    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.wingType.ordinal());
        buf.writeFloat(this.wingScale);
        buf.writeBoolean(this.spawnParticles);
        buf.writeFloat(this.centreOffset);
        buf.writeFloat(this.heightOffset);
    }

    /**
     * Reads the wing data from a ByteBuf.
     * @param buf ByteBuf to read from.
     */
    private void fromBytes(ByteBuf buf) {
        this.wingType = WingType.values()[buf.readByte()];
        this.wingScale = buf.readFloat();
        this.spawnParticles = buf.readBoolean();
        this.centreOffset = buf.readFloat();
        this.heightOffset = buf.readFloat();
    }
}
