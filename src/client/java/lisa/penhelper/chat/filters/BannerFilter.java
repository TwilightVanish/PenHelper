package lisa.penhelper.chat.filters;

import lisa.penhelper.chat.ChatFilter;
import lisa.penhelper.config.ConfigProvider;

public class BannerFilter implements ChatFilter
{
    @Override
    public boolean isEnabled() {
        return ConfigProvider.filters().banner;
    }

    @Override
    public boolean matches(String message) {
        return message.startsWith("     ") || message.isBlank();
    }
}
