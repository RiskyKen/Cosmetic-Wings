package riskyken.cosmeticWings.common.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import riskyken.cosmeticWings.CosmeticWings;
import riskyken.cosmeticWings.client.gui.GuiWings;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler {

    public GuiHandler() {
        NetworkRegistry.INSTANCE.registerGuiHandler(CosmeticWings.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
        case 0:
            return new GuiWings(player);
        }
        return null;
    }
}
