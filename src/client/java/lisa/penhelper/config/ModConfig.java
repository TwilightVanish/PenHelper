package lisa.penhelper.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "penhelper")
@Config.Gui.Background("minecraft:textures/block/stone_bricks.png")
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public General general = new General();

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public InventoryTracking inventoryTracking = new InventoryTracking();

    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public Filters filters = new Filters();

    public static class General {
        public boolean dropProtection = true;
        public boolean autoSprint = false;
    }

    public static class InventoryTracking {
        public boolean enabled = false;
        public boolean sound = false;
        public boolean overlay = false;
        public boolean diamondsOnly = false;
    }

    public static class Filters {
        public boolean banner = false;
        public boolean keys = false;
        public boolean levelUp = false;
        public boolean miningHaste = false;
        public boolean vault = false;
        public boolean vote = false;
        public boolean worldGuard = false;
    }
}
