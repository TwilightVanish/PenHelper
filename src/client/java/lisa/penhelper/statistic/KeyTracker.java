package lisa.penhelper.statistic;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.network.chat.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class KeyTracker extends Statistic {
    public static final KeyTracker INSTANCE = new KeyTracker();
    private static final Pattern KEY_PATTERN = Pattern.compile("Crates » You recieved x?(\\d+) of (.+?)!");

    private KeyTracker() {
        super("Rare Keys/h", 0xFFD700);
    }

    public static void register() {
        ClientReceiveMessageEvents.ALLOW_GAME.register((Component message, boolean overlay) -> {
            handleMessage(message);
            return true;
        });
    }

    private static void handleMessage(Component message) {
        String text = message.getString();
        Matcher matcher = KEY_PATTERN.matcher(text);
        if (!matcher.matches()) return;

        int amount = Integer.parseInt(matcher.group(1));
        String keyName = matcher.group(2);

        double points = switch (keyName.toLowerCase()) {
            case "rare key" -> 1.0;
            case "uncommon key" -> 1.0 / 9.0;
            case "common key" -> 1.0 / 81.0;
            case "basic key" -> 1.0 / 729.0;
            default -> 0.0;
        };

        if (points <= 0) return;
        points *= amount;

        INSTANCE.record(points);
    }
}
