package lisa.penhelper.chat.filters;

import lisa.penhelper.chat.ChatFilter;
import lisa.penhelper.config.ConfigProvider;

public class MiningHasteFilter implements ChatFilter {

    @Override
    public boolean isEnabled() {
        return ConfigProvider.filters().miningHaste;
    }

    @Override
    public boolean matches(String message) {
        return message.startsWith("Mining > You just got Haste");
    }
}
