package riskyken.cosmeticWings.client.lib;

import riskyken.cosmeticWings.client.model.wings.ModelBigWings;
import riskyken.cosmeticWings.client.model.wings.ModelKuroyukihimeWings;
import riskyken.cosmeticWings.client.model.wings.ModelMechWings;
import riskyken.cosmeticWings.client.model.wings.ModelMetalWings;
import riskyken.cosmeticWings.client.model.wings.ModelSmallMechWings;
import riskyken.cosmeticWings.client.model.wings.ModelWingsFlandre;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class LibModels {
    
    public static final ModelBigWings BIG_WHITE_WINGS = new ModelBigWings("textures/wings/big-white-wings.png");
    public static final ModelBigWings BIG_BLACK_WINGS = new ModelBigWings("textures/wings/big-black-wings.png");
    public static final ModelBigWings BIG_SHANA_WINGS = new ModelBigWings("textures/wings/shana-wings.png");
    public static final ModelWingsFlandre FLANDRE_WINGS = new ModelWingsFlandre();
    public static final ModelKuroyukihimeWings KUROYUKIHIME_WINGS = new ModelKuroyukihimeWings();
    public static final ModelMechWings MECH_WINGS  = new ModelMechWings();
    public static final ModelMetalWings METAL_WINGS = new ModelMetalWings();
    public static final ModelSmallMechWings SMALL_MECH_WINGS  = new ModelSmallMechWings();
}
