package lisa.penhelper.mixin;


import lisa.penhelper.api.InventoryEvents;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class ContainerClickMixin {
    @Inject(method = "slotClicked", at = @At("HEAD"), cancellable = true)
    private void slotClicked(Slot slot, int i, int j, ClickType clickType, CallbackInfo ci) {
        if (clickType != ClickType.THROW) return;

        boolean allowed = InventoryEvents.ALLOW_DROP.invoker().onDrop(slot.getItem());

        if (!allowed) ci.cancel();
    }
}
