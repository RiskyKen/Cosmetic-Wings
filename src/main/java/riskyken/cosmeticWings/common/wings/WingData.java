package riskyken.cosmeticWings.common.wings;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import riskyken.cosmeticWings.utils.UtilColour;
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
    private static final String TAG_PARTICLE_SPAWN_RATE = "particleSpawnRate";
    private static final String TAG_CENTRE_OFFSET = "centreOffset";
    private static final String TAG_HEIGHT_OFFSET = "heightOffset";
    private static final String TAG_COLOUR = "colour";

    public WingType wingType;
    public float wingScale;
    public float particleSpawnRate;
    public float centreOffset;
    public float heightOffset;
    public int colour;
    
    public WingData() {
        //Default options that will be used for new players.
        this.wingType = WingType.NONE;
        this.wingScale = 0.75F;
        this.particleSpawnRate = 1F;
        this.centreOffset = 0F;
        this.heightOffset = 0.7F;
        this.colour = UtilColour.getMinecraftColor(0);
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
        compound.setFloat(TAG_PARTICLE_SPAWN_RATE, this.particleSpawnRate);
        compound.setFloat(TAG_CENTRE_OFFSET, this.centreOffset);
        compound.setFloat(TAG_HEIGHT_OFFSET, this.heightOffset);
        compound.setInteger(TAG_COLOUR, this.colour);
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
        if (compound.hasKey(TAG_PARTICLE_SPAWN_RATE)) {
            this.particleSpawnRate = compound.getFloat(TAG_PARTICLE_SPAWN_RATE);
        }
        if (compound.hasKey(TAG_CENTRE_OFFSET)) {
            this.centreOffset = compound.getFloat(TAG_CENTRE_OFFSET);
        }
        if (compound.hasKey(TAG_HEIGHT_OFFSET)) {
            this.heightOffset = compound.getFloat(TAG_HEIGHT_OFFSET);
        }
        if (compound.hasKey(TAG_COLOUR)) {
            this.colour = compound.getInteger(TAG_COLOUR);
        }
    }

    /**
     * Writes the wing data to a ByteBuf.
     * @param buf ByteBuf to write to.
     */
    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.wingType.ordinal());
        buf.writeFloat(this.wingScale);
        buf.writeFloat(this.particleSpawnRate);
        buf.writeFloat(this.centreOffset);
        buf.writeFloat(this.heightOffset);
        buf.writeInt(this.colour);
    }

    /**
     * Reads the wing data from a ByteBuf.
     * @param buf ByteBuf to read from.
     */
    private void fromBytes(ByteBuf buf) {
        this.wingType = WingType.values()[buf.readByte()];
        this.wingScale = buf.readFloat();
        this.particleSpawnRate = buf.readFloat();
        this.centreOffset = buf.readFloat();
        this.heightOffset = buf.readFloat();
        this.colour = buf.readInt();
    }
}
