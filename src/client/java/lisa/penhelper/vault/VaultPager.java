package lisa.penhelper.vault;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.network.chat.Component;

public class VaultPager {
    private static final int guiWidth = 176;
    private static final int guiHeight = 166;

    public static void register() {
        ScreenEvents.AFTER_INIT.register(VaultPager::handleScreenChange);
    }

    private static void handleScreenChange(Minecraft client, Screen screen, int w, int h) {
        if (!(screen instanceof ContainerScreen containerScreen)) return;

        Integer vaultNumber = parseVaultNumber(containerScreen.getTitle().getString());
        if (vaultNumber == null) return;

        int originX = (screen.width - guiWidth) / 2;
        int originY = (screen.height - guiHeight) / 2;

        var buttons = Screens.getButtons(containerScreen);

        Button next = Button.builder(Component.literal(">"), b -> sendVaultCommand(vaultNumber + 1))
                .bounds(originX + guiWidth + 2, originY + 5, 16, 16)
                .build();
        buttons.add(next);

        if (vaultNumber == 1) return;
        Button prev = Button.builder(Component.literal("<"), b -> sendVaultCommand(vaultNumber - 1))
                .bounds(originX - 18, originY + 5, 16, 16)
                .build();
        buttons.add(prev);
    }

    private static Integer parseVaultNumber(String title) {
        if (!title.startsWith("Vault #")) return null;
        try {
            return Integer.parseInt(title.substring(7));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static void sendVaultCommand(int num) {
        if (num < 1) return;

        var player = Minecraft.getInstance().player;
        if (player == null) return;

        player.connection.sendCommand("v " + num);
    }
}
