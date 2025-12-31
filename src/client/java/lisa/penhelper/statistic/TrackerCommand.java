package lisa.penhelper.statistic;

import com.mojang.brigadier.Command;
import lisa.penhelper.overlay.HudRenderer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.network.chat.Component;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public final class TrackerCommand {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                    literal("tracker")
                            .then(literal("start")
                                    .executes(ctx -> {
                                        HudRenderer.startTracking();
                                        ctx.getSource().sendFeedback(
                                                Component.literal("Trackers started.")
                                        );
                                        return Command.SINGLE_SUCCESS;
                                    })
                            )
                            .then(literal("stop")
                                    .executes(ctx -> {
                                        HudRenderer.stopTracking();
                                        ctx.getSource().sendFeedback(
                                                Component.literal("Trackers stopped.")
                                        );
                                        return Command.SINGLE_SUCCESS;
                                    })
                            )
                            .then(literal("reset")
                                    .executes(ctx -> {
                                        HudRenderer.resetStatistics();
                                        ctx.getSource().sendFeedback(
                                                Component.literal("Trackers reset.")
                                        );
                                        return Command.SINGLE_SUCCESS;
                                    })
                            )
            );
        });
    }
}
