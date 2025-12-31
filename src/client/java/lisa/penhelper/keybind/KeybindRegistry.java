package lisa.penhelper.keybind;


public final class KeybindRegistry {
    private static final Keybind[] ALL = {
            new MineTopKeybind(),
            new CondenseKeybind(),
            new DisposeKeybind(),
            new LastVaultKeybind(),
            new BundleKeybind()
    };

    public static void registerAll() {
        for (Keybind kb : ALL) kb.register();
    }
}
