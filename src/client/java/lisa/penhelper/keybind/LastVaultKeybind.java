package lisa.penhelper.keybind;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;

public final class LastVaultKeybind extends Keybind {
    private int lastVault = 1;

    public LastVaultKeybind() {
        super("key.penhelper.lastvault");
    }

    @Override
    protected void onRegister() {
        ScreenEvents.AFTER_INIT.register(this::onScreenInit);
    }

    @Override
    public void onPress(Minecraft client) {
        if (client.player == null) return;
        client.player.connection.sendCommand("v " + lastVault);
    }

    private void onScreenInit(Minecraft client, Screen screen, int w, int h) {
        if (!(screen instanceof ContainerScreen container)) return;

        Integer vault = parseVaultNumber(container.getTitle().getString());
        if (vault != null) {
            lastVault = vault;
        }
    }

    private static Integer parseVaultNumber(String title) {
        if (!title.startsWith("Vault #")) return null;
        try {
            return Integer.parseInt(title.substring(7));
        } catch (NumberFormatException ignored) {
            return null;
        }
    }
}

