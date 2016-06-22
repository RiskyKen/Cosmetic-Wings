package riskyken.cosmeticWings.client.render.texture;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import riskyken.cosmeticWings.common.lib.LibModInfo;

public class FeatherTexture extends TextureAtlasSprite {

    private static final ResourceLocation featherParticles = new ResourceLocation(LibModInfo.ID.toLowerCase(), "particles/featherParticles");
    
    public FeatherTexture() {
        super(featherParticles.toString());
        setIconWidth(8);
        setIconHeight(8);
    }
}
