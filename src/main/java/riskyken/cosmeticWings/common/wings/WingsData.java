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
public class WingsData {

    private static final String TAG_WING_DATA = "wingData";
    private static final String TAG_WING_ID = "wingId";
    private static final String TAG_WING_SCALE = "wingScale";
    private static final String TAG_PARTICLE_SPAWN_RATE = "particleSpawnRate";
    private static final String TAG_CENTRE_OFFSET = "centreOffset";
    private static final String TAG_HEIGHT_OFFSET = "heightOffset";
    private static final String TAG_COLOUR = "colour";

    public int wingId;
    public float wingScale;
    public float particleSpawnRate;
    public float centreOffset;
    public float heightOffset;
    public int colour;
    
    public WingsData() {
        setWingDefaultValues();
    }
    
    /** Default options that will be used for new players. */
    private void setWingDefaultValues() {
        this.wingId = -1;
        this.wingScale = 0.75F;
        this.particleSpawnRate = 1F;
        this.centreOffset = 0F;
        this.heightOffset = 0.7F;
        this.colour = UtilColour.getMinecraftColor(0);
    }

    public WingsData(ByteBuf buf) {
        fromBytes(buf);
    }

    /**
     * Saves the wings data to an NBTTagCompound.
     * @param compound NBTTagCompound to save to.
     */
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound wingCompound = new NBTTagCompound();
        wingCompound.setInteger(TAG_WING_ID, this.wingId);
        wingCompound.setFloat(TAG_WING_SCALE, this.wingScale);
        wingCompound.setFloat(TAG_PARTICLE_SPAWN_RATE, this.particleSpawnRate);
        wingCompound.setFloat(TAG_CENTRE_OFFSET, this.centreOffset);
        wingCompound.setFloat(TAG_HEIGHT_OFFSET, this.heightOffset);
        wingCompound.setInteger(TAG_COLOUR, this.colour);
        compound.setTag(TAG_WING_DATA, wingCompound);
    }

    /**
     * Loads the wing data from an NBTTagCompound.
     * @param compound NBTTagCompound to load from.
     */
    public void loadNBTData(NBTTagCompound compound) {
        if (compound.hasKey(TAG_WING_DATA)) {
            NBTTagCompound wingCompound = compound.getCompoundTag(TAG_WING_DATA);
            if (wingCompound.hasKey(TAG_WING_ID)) {
                this.wingId = wingCompound.getInteger(TAG_WING_ID);
            }
            if (wingCompound.hasKey(TAG_WING_SCALE)) {
                this.wingScale = wingCompound.getFloat(TAG_WING_SCALE);
            }
            if (wingCompound.hasKey(TAG_PARTICLE_SPAWN_RATE)) {
                this.particleSpawnRate = wingCompound.getFloat(TAG_PARTICLE_SPAWN_RATE);
            }
            if (wingCompound.hasKey(TAG_CENTRE_OFFSET)) {
                this.centreOffset = wingCompound.getFloat(TAG_CENTRE_OFFSET);
            }
            if (wingCompound.hasKey(TAG_HEIGHT_OFFSET)) {
                this.heightOffset = wingCompound.getFloat(TAG_HEIGHT_OFFSET);
            }
            if (wingCompound.hasKey(TAG_COLOUR)) {
                this.colour = wingCompound.getInteger(TAG_COLOUR);
            }
        } else {
            setWingDefaultValues();
        }
    }

    /**
     * Writes the wing data to a ByteBuf.
     * @param buf ByteBuf to write to.
     */
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.wingId);
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
        this.wingId = buf.readInt();
        this.wingScale = buf.readFloat();
        this.particleSpawnRate = buf.readFloat();
        this.centreOffset = buf.readFloat();
        this.heightOffset = buf.readFloat();
        this.colour = buf.readInt();
    }
}
