package riskyken.cosmeticWings.client.model.wings;

import net.minecraft.client.model.ModelBase;

public class ModelWingBase extends ModelBase {
    
    protected static float SCALE = 0.0625F;
    
    protected float getWingAngle(boolean isFlying, float maxAngle, int totalTime, int flyingTime, int offset) {
        float angle = 0F;
        
        int flapTime = totalTime;
        if (isFlying) {
            flapTime = flyingTime;
        }
        
        float deltaTime = getAnimationTime(flapTime, offset);
        
        if (deltaTime <= 0.5F) {
            angle = Sigmoid(-4 + ((deltaTime * 2) * 8));
        } else {
            angle = 1 - Sigmoid(-4 + (((deltaTime * 2) - (1)) * 8));
        }
        angle *= maxAngle;
        
        return angle;
    }
    
    private static float Sigmoid(double value) {
        return 1.0f / (1.0f + (float) Math.exp(-value));
    }
    
    private float getAnimationTime(int totalTime, int offset) {
        float time = (System.currentTimeMillis() + offset) % totalTime;
        return time / totalTime;
    }
}
