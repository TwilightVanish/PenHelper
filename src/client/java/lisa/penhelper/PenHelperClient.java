package lisa.penhelper;

import lisa.penhelper.chat.ChatFilterManager;
import lisa.penhelper.config.ConfigMenuButton;
import lisa.penhelper.config.ConfigProvider;
import lisa.penhelper.config.ModConfig;
import lisa.penhelper.inventory.DropProtector;
import lisa.penhelper.inventory.InventoryTracker;
import lisa.penhelper.keybind.*;
import lisa.penhelper.movement.AutoSprint;
import lisa.penhelper.overlay.HudRenderer;
import lisa.penhelper.overlay.InventoryDisplay;
import lisa.penhelper.overlay.ShardGoalDisplay;
import lisa.penhelper.overlay.StatisticDisplay;
import lisa.penhelper.statistic.KeyTracker;
import lisa.penhelper.statistic.MoneyTracker;
import lisa.penhelper.statistic.ShardTracker;
import lisa.penhelper.statistic.TrackerCommand;
import lisa.penhelper.vault.VaultPager;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PenHelperClient implements ClientModInitializer {
	public static final String MOD_ID = "penhelper";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("Hello! Registering features...");

		AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
		ConfigMenuButton.register();
		ConfigProvider.register();

		ChatFilterManager.register();
		VaultPager.register();
		DropProtector.register();
		KeybindRegistry.registerAll();
		AutoSprint.register();

		ShardGoalDisplay.register();
		InventoryTracker.register();
		MoneyTracker.register();
		ShardTracker.register();
		KeyTracker.register();
		TrackerCommand.register();


		HudRenderer.register();
		HudRenderer.add(new InventoryDisplay(InventoryTracker.INSTANCE));
		HudRenderer.add(new ShardGoalDisplay());
		HudRenderer.add(new StatisticDisplay(MoneyTracker.INSTANCE));
		HudRenderer.add(new StatisticDisplay(ShardTracker.INSTANCE));
		HudRenderer.add(new StatisticDisplay(KeyTracker.INSTANCE));

		LOGGER.info("Features registered!");
	}
}