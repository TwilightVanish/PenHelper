package lisa.penhelper.keybind;

import net.minecraft.client.Minecraft;

public final class BundleKeybind extends Keybind {
    public BundleKeybind() {
        super("key.penhelper.bundle");
    }

    @Override
    protected void onPress(Minecraft client) {
        if (client.player == null) return;
        client.player.connection.sendCommand("ah sell 1000000000 -b");
    }
}
