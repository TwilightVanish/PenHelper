package lisa.penhelper.inventory;

import lisa.penhelper.api.InventoryEvents;
import lisa.penhelper.config.ConfigProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class DropProtector {
    private static final List<Item> PROTECTED_ITEMS = new ArrayList<>();
    private static final long DOUBLE_PRESS_WINDOW_MS = 300;
    private static final long WARNING_COOLDOWN_MS = 1500;
    private static long lastAttempt = 0;
    private static Item lastItem = null;
    private static long lastWarning = 0;

    public static void register() {
        InventoryEvents.ALLOW_DROP.register(DropProtector::allowDrop);

        // Future: Let people configure any item
        PROTECTED_ITEMS.add(Items.FISHING_ROD);
    }

    private static boolean allowDrop(ItemStack stack) {
        if (!ConfigProvider.general().dropProtection) return true;
        if (!PROTECTED_ITEMS.contains(stack.getItem())) return true;

        long now = Util.getEpochMillis();

        if (stack.is(lastItem) && now < lastAttempt + DOUBLE_PRESS_WINDOW_MS) {
            return true;
        }

        lastItem = stack.getItem();
        lastAttempt = now;
        sendDropWarning(stack.getItemName().getString());

        return false;
    }

    private static void sendDropWarning(String itemName) {
        long now = System.currentTimeMillis();
        if (now < lastWarning + WARNING_COOLDOWN_MS) return;

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        lastWarning = now;

        var prefix = Component.literal("!")
                .withStyle(style -> style.withColor(CommonColors.SOFT_RED).withBold(true));

        var message = Component.literal(" " + itemName + " Protected. Double-tap to override.")
                .withStyle(style -> style.withColor(CommonColors.WHITE).withBold(false));

        player.displayClientMessage(prefix.append(message), false);
    }
}
