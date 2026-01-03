package lisa.penhelper.api;

import net.minecraft.world.item.ItemStack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface InventoryEvents {
    Event<InventoryEvents> ALLOW_DROP = EventFactory.createArrayBacked(
            InventoryEvents.class,
            listeners -> stack -> {
                for (InventoryEvents callback : listeners) {
                    if (!callback.onDrop(stack)) return false;
                }
                return true;
            }
    );

    /**
     * Called when the player attempts to drop an item.
     *
     * @param stack The item being dropped.
     * @return {@code true} to allow the drop, {@code false} to cancel it.
     */
    boolean onDrop(ItemStack stack);
}

