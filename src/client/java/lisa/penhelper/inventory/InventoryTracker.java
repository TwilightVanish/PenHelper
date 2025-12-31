package lisa.penhelper.inventory;

import lisa.penhelper.config.ConfigProvider;
import lisa.penhelper.config.ModConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class InventoryTracker {
    public static final InventoryTracker INSTANCE = new InventoryTracker();
    private static boolean inventoryWasFull = false;
    private static int emptySlots = 0;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(InventoryTracker::tick);
    }

    private static void tick(Minecraft client) {
        if (client.player == null) return;
        countEmptyInventorySlots(client.player);

        boolean inventoryIsFull = emptySlots == 0;

        if (!inventoryWasFull && inventoryIsFull && ConfigProvider.inventoryTracking().sound) {
            client.player.playSound(SoundEvents.NOTE_BLOCK_CHIME.value(), 1F, 0.6F);
        }

        inventoryWasFull = inventoryIsFull;
    }

    private static void countEmptyInventorySlots(Player player) {
        if (!ConfigProvider.inventoryTracking().enabled) {
            emptySlots = 1;
            return;
        }

        emptySlots = ConfigProvider.inventoryTracking().diamondsOnly ? countEmptyDiamondSlots(player) : countEmptySlots(player);
    }

    private static int countEmptyDiamondSlots(Player player) {
        int slots = 0;

        for (ItemStack stack : player.getInventory().getNonEquipmentItems()) {
            if (stack.getItem() == Items.DIAMOND && stack.getCount() < 64) {
                slots++;
            }
        }

        return slots;
    }

    private static int countEmptySlots(Player player) {
        int slots = 0;

        for (ItemStack stack : player.getInventory().getNonEquipmentItems()) {
            if (stack.isEmpty()) {
                slots++;
            }
        }

        return slots;
    }

    public int getEmptySlots() {
        return emptySlots;
    }
}
