package lisa.penhelper.config;

import lisa.penhelper.gui.IconButton;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;

public class ConfigMenuButton
{
    public static void register() {
        ScreenEvents.AFTER_INIT.register(ConfigMenuButton::handleScreenChange);
    }

    private static void handleScreenChange(Minecraft client, Screen screen, int width, int height) {
        if (!(screen instanceof PauseScreen)) return;

        var buttons = Screens.getButtons(screen);

        AbstractWidget options = buttons.stream()
                .filter(b -> b.getMessage().getString().equals(Component.translatable("menu.options").getString()))
                .findFirst().orElse(null);

        if (options == null) return;

        int size = 20;
        int x = options.getX() - size - 4;
        int y = options.getY();

        Button iconButton = new IconButton(
                x, y, size, size,
                () -> client.setScreen(AutoConfig.getConfigScreen(ModConfig.class, screen).get()),
                Items.IRON_BARS
        );

        buttons.add(iconButton);
    }
}
