package lisa.penhelper.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import static lisa.penhelper.PenHelperClient.MOD_ID;

public abstract class Keybind {
    protected static final KeyMapping.Category CATEGORY =
            KeyMapping.Category.register(
                    ResourceLocation.fromNamespaceAndPath(MOD_ID, "utility")
            );

    protected final KeyMapping key;

    protected Keybind(String translationKey) {
        this.key = KeyBindingHelper.registerKeyBinding(
                new KeyMapping(
                        translationKey,
                        InputConstants.Type.KEYSYM,
                        InputConstants.UNKNOWN.getValue(),
                        CATEGORY
                )
        );
    }

    public final void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (key.consumeClick()) {
                onPress(client);
            }
        });

        onRegister();
    }

    protected void onRegister() {}

    protected abstract void onPress(Minecraft client);
}


