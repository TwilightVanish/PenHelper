package lisa.penhelper.statistic;

import lisa.penhelper.PenHelperClient;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShardTracker extends Statistic {
    public static final ShardTracker INSTANCE = new ShardTracker();
    private static final List<Pattern> SHARD_PATTERNS = List.of(
            Pattern.compile("You received (\\d+) shards?!"),
            Pattern.compile("\\+(\\d+) Shards \\(x\\d(?:\\.\\d{1,2})? Rate\\)")
    );

    private ShardTracker() {
        super("Shards/h", 0x00FFFF);
    }

    public static void register() {
        ClientReceiveMessageEvents.GAME.register(ShardTracker::handleMessage);
    }

    private static void handleMessage(Component message, boolean overlay) {
        String text = message.getString();
        for (Pattern pattern : SHARD_PATTERNS) {
            Matcher m = pattern.matcher(text);
            if (!m.matches()) continue;

            double amount = Double.parseDouble(m.group(1).replace(",", ""));
            INSTANCE.record(amount);
            return;
        }
    }
}
