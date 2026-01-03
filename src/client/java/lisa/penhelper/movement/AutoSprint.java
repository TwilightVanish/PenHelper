package lisa.penhelper.movement;

import lisa.penhelper.config.ConfigProvider;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class AutoSprint {
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(AutoSprint::onTick);
    }

    private static void onTick(Minecraft client) {
        if (!ConfigProvider.general().autoSprint) return;
        if (client.player != null && client.screen == null) {
            KeyMapping.set(client.options.keySprint.getDefaultKey(), true);
        }
    }
}
