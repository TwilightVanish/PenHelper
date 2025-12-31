package lisa.penhelper.chat.filters;

import lisa.penhelper.chat.ChatFilter;
import lisa.penhelper.config.ConfigProvider;

public class VoteFilter implements ChatFilter {
    @Override
    public boolean isEnabled() {
        return ConfigProvider.filters().vote;
    }

    @Override
    public boolean matches(String message) {
        return message.startsWith("Vote »");
    }
}
