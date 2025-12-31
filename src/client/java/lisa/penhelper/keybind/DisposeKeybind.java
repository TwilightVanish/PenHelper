package lisa.penhelper.keybind;

import net.minecraft.client.Minecraft;

public final class DisposeKeybind extends Keybind {
    public DisposeKeybind() {
        super("key.penhelper.dispose");
    }

    @Override
    protected void onPress(Minecraft client) {
        if (client.player == null) return;
        client.player.connection.sendCommand("dispose");
    }
}
