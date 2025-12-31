package lisa.penhelper.overlay;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.DeltaTracker;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

import static lisa.penhelper.PenHelperClient.MOD_ID;

public final class HudRenderer {
    private static final List<HudItem> ITEMS = new ArrayList<>();
    private static boolean trackersEnabled = false;

    public static void register() {
        HudElementRegistry.addLast(
                ResourceLocation.fromNamespaceAndPath(MOD_ID, "hud"),
                HudRenderer::render
        );
    }

    public static void add(HudItem item) {
        ITEMS.add(item);
    }

    private static void render(GuiGraphics context, DeltaTracker tickCounter) {
        if (ITEMS.isEmpty()) return;

        var font = Minecraft.getInstance().font;
        int x = 2;
        int y = 2;
        int lineHeight = font.lineHeight;

        for (HudItem item : ITEMS) {
            if (item instanceof StatisticDisplay && !trackersEnabled) continue;

            Component text = item.getDisplayText();
            if (text == null || text.getString().isEmpty()) continue;

            context.drawString(font, text, x, y, 0xFFFFFFFF, true);
            y += lineHeight;
        }
    }

    public static void resetStatistics() {
        for (HudItem item : ITEMS) {
            if (item instanceof StatisticDisplay statDisplay) {
                statDisplay.getStatistic().reset();
            }
        }
    }

    public static void startTracking() {
        resetStatistics();
        trackersEnabled = true;
    }

    public static void stopTracking() {
        trackersEnabled = false;
    }
}
