package lisa.penhelper.mixin;

import lisa.penhelper.api.InventoryEvents;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class LocalPlayerDropMixin {
    @Inject(method = "drop", at = @At("HEAD"), cancellable = true)
    private void drop(boolean bl, CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer player = (LocalPlayer)(Object)this;
        ItemStack stack = player.getMainHandItem();

        boolean allowed = InventoryEvents.ALLOW_DROP.invoker().onDrop(stack);

        if (!allowed) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
