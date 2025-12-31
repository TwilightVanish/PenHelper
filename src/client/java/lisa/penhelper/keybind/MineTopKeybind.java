package lisa.penhelper.keybind;

import net.minecraft.client.Minecraft;

public final class MineTopKeybind extends Keybind {
    public MineTopKeybind() {
        super("key.penhelper.minetop");
    }

    @Override
    protected void onPress(Minecraft client) {
        if (client.player == null) return;
        client.player.connection.sendCommand("minetop");
    }
}
