package lisa.penhelper.chat.filters;

import lisa.penhelper.chat.ChatFilter;
import lisa.penhelper.config.ConfigProvider;

public class VaultFilter implements ChatFilter {
    @Override
    public boolean isEnabled() {
        return ConfigProvider.filters().vault;
    }

    @Override
    public boolean matches(String message) {
        return message.startsWith("Vaults » Vault");
    }
}
