package lisa.penhelper.config;

import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.world.InteractionResult;

public final class ConfigProvider {
    private static volatile ModConfig config;

    public static void register() {
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        AutoConfig.getConfigHolder(ModConfig.class)
                .registerLoadListener((holder, newConfig) -> {
                    ConfigProvider.config = newConfig;
                    return InteractionResult.PASS;
                });
    }

    public static ModConfig.InventoryTracking inventoryTracking() {
        return config.inventoryTracking;
    }

    public static ModConfig.Filters filters() {
        return config.filters;
    }
}
