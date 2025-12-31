package lisa.penhelper.statistic;

import lisa.penhelper.PenHelperClient;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;

import java.time.Instant;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MoneyTracker extends Statistic {
    public static final MoneyTracker INSTANCE = new MoneyTracker();
    private static final List<Pattern> SELL_PATTERNS = List.of(
            Pattern.compile("Commissary » You have successfully sold \\d+ items for \\$([\\d,]+(?:\\.\\d{1,2})?)"),
            Pattern.compile("\\[Shop\\] You sold \\d+ .* to .* for \\$([\\d,]+(?:\\.\\d{1,2})?)\\.")
    );

    private MoneyTracker() {
        super("Money/h", CommonColors.GREEN);
    }

    public static void register() {
        ClientReceiveMessageEvents.GAME.register(MoneyTracker::handleMessage);
    }

    private static void handleMessage(Component message, boolean overlay) {
        String text = message.getString();
        for (Pattern pattern : SELL_PATTERNS) {
            Matcher m = pattern.matcher(text);
            if (!m.matches()) continue;

            double amount = Double.parseDouble(m.group(1).replace(",", ""));
            INSTANCE.record(amount);
            return;
        }
    }
}
