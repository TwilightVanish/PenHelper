package lisa.penhelper.chat.filters;

import lisa.penhelper.chat.ChatFilter;
import lisa.penhelper.config.ConfigProvider;

public class KeyFilter implements ChatFilter {
    @Override
    public boolean isEnabled() {
        return ConfigProvider.filters().keys;
    }

    @Override
    public boolean matches(String message) {
        return message.startsWith("Mining > You found") || message.startsWith("Crates » You recieved");
    }
}
