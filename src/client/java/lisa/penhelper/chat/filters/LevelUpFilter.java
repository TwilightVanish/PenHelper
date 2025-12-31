package lisa.penhelper.chat.filters;

import lisa.penhelper.chat.ChatFilter;
import lisa.penhelper.config.ConfigProvider;

public class LevelUpFilter implements ChatFilter {
    @Override
    public boolean isEnabled() {
        return ConfigProvider.filters().levelUp;
    }

    @Override
    public boolean matches(String message) {
        return message.startsWith("§lMining increased to");
    }
}
