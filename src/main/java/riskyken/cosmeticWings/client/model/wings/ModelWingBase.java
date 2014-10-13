package riskyken.cosmeticWings.client.model.wings;

import net.minecraft.client.model.ModelBase;

public class ModelWingBase extends ModelBase {
    
    protected float getWingAngle(boolean isFlying, float maxAngle, int totalTime, int flyingTime) {
        float angle = 0F;
        
        int flapTime = totalTime;
        if (isFlying) {
            flapTime = flyingTime;
        }
        
        float deltaTime = getAnimationTime(totalTime);
        
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
    
    private float getAnimationTime(int totalTime) {
        float time = System.currentTimeMillis() % totalTime;
        return time / totalTime;
    }
}
