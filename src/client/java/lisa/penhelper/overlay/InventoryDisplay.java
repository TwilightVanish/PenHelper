package lisa.penhelper.overlay;

import lisa.penhelper.config.ConfigProvider;
import lisa.penhelper.config.ModConfig;
import lisa.penhelper.inventory.InventoryTracker;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;

public class InventoryDisplay implements HudItem {
    private final InventoryTracker tracker;

    public InventoryDisplay(InventoryTracker tracker) {
        this.tracker = tracker;
    }

    @Override
    public Component getDisplayText() {
        if (!enabled()) return null;
        var emptySlots = tracker.getEmptySlots();

        if (emptySlots == 0) {
            return Component.literal("Inventory Full").withColor(CommonColors.SOFT_RED);
        }

        String slot = (emptySlots == 1) ? "slot" : "slots";
        return Component.literal(emptySlots + " free " + slot).withColor(0xEDEDED);
    }

    private boolean enabled() {
        return ConfigProvider.inventoryTracking().enabled && ConfigProvider.inventoryTracking().overlay;
    }
}
