package lisa.penhelper.keybind;

import net.minecraft.client.Minecraft;

public final class CondenseKeybind extends Keybind {
    public CondenseKeybind() {
        super("key.penhelper.condense");
    }

    @Override
    protected void onPress(Minecraft client) {
        if (client.player == null) return;
        client.player.connection.sendCommand("condense");
    }
}
