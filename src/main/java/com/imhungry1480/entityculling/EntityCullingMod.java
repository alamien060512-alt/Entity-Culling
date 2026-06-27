package com.imhungry1480.entityculling;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class EntityCullingMod implements ClientModInitializer {

    private static KeyBinding toggleKey;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.entityculling.toggle",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_F7,
            "category.entityculling"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                CullConfig.enabled = !CullConfig.enabled;
                if (client.player != null) {
                    client.player.sendMessage(
                        Text.literal("§6[EntityCulling] §r" + (CullConfig.enabled ? "§aEnabled" : "§cDisabled")),
                        true
                    );
                }
            }
        });
    }
}
