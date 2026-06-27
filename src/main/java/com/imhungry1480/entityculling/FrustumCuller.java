package com.imhungry1480.entityculling;

import net.minecraft.client.render.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class FrustumCuller {

    public static boolean shouldCull(Entity entity, Frustum frustum, Vec3d cameraPos) {
        if (!CullConfig.enabled) return false;

        if (!CullConfig.cullPlayers && entity instanceof net.minecraft.entity.player.PlayerEntity) {
            return false;
        }

        if (CullConfig.maxEntityDistance > 0) {
            double distSq = entity.squaredDistanceTo(cameraPos.x, cameraPos.y, cameraPos.z);
            double maxDistSq = (double) CullConfig.maxEntityDistance * CullConfig.maxEntityDistance;
            if (distSq > maxDistSq) return true;
        }

        Box box = entity.getBoundingBox();
        if (!frustum.isVisible(box)) return true;

        return false;
    }
}
