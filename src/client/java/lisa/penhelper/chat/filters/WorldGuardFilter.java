package lisa.penhelper.chat.filters;

import lisa.penhelper.chat.ChatFilter;
import lisa.penhelper.config.ConfigProvider;

public class WorldGuardFilter implements ChatFilter {
    @Override
    public boolean isEnabled() {
        return ConfigProvider.filters().worldGuard;
    }

    @Override
    public boolean matches(String message) {
        return message.startsWith("Hey! Sorry, but you can't break that block here");
    }
}
