package com.imhungry1480.entityculling.mixin;

import com.imhungry1480.entityculling.CullConfig;
import com.imhungry1480.entityculling.FrustumCuller;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityRenderDispatcher.class, remap = false)
public class EntityRendererMixin {

    @Inject(
        method = "shouldRender",
        at = @At("HEAD"),
        cancellable = true,
        remap = false
    )
    private <E extends Entity> void entityculling_shouldRender(
        E entity,
        Frustum frustum,
        double x, double y, double z,
        CallbackInfoReturnable<Boolean> cir
    ) {
        if (!CullConfig.enabled) return;
        Vec3d camPos = new Vec3d(x, y, z);
        if (FrustumCuller.shouldCull(entity, frustum, camPos)) {
            cir.setReturnValue(false);
        }
    }
}
