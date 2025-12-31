package lisa.penhelper.chat;

import lisa.penhelper.chat.filters.*;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.network.chat.Component;

import java.util.List;

public class ChatFilterManager {
    private static final List<ChatFilter> FILTERS = List.of(
            new BannerFilter(),
            new KeyFilter(),
            new LevelUpFilter(),
            new MiningHasteFilter(),
            new VaultFilter(),
            new VoteFilter(),
            new WorldGuardFilter()
    );

    public static void register() {
        ClientReceiveMessageEvents.ALLOW_GAME.register(ChatFilterManager::isAllowed);
    }

    private static boolean isAllowed(Component message, boolean overlay) {
        if (overlay) return true;
        String messageContent = message.getString();
        for (ChatFilter filter : FILTERS) {
            if (!filter.isEnabled()) continue;
            if (filter.matches(messageContent)) return false;
        }

        return true;
    }
}
