package lisa.penhelper.overlay;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.CommonColors;
import net.minecraft.world.scores.*;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

import java.time.Instant;

// Todo: This probably has too much responsibility, refactor
public class ShardGoalDisplay implements HudItem {
    private static int shardCount = 0;
    private static int shardGoal = -1;

    private static int startShardCount = 0;
    private static Instant startTime;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(ShardGoalDisplay::updateShardCount);
        RegisterCommand();
    }

    private static void updateShardCount(Minecraft client) {
        if (client.level == null) return;
        Scoreboard scoreboard = client.level.getScoreboard();
        for (var team : scoreboard.getPlayerTeams()) {
            var prefix = team.getPlayerPrefix().getString();
            if (prefix.contains("Shards")) {
                var stripped = prefix.substring(12).replace(",", "");
                try {
                    shardCount = Integer.parseInt(stripped);
                } catch (NumberFormatException ignored) {
                }
            }
        }
    }

    private static void RegisterCommand() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                    literal("shardgoal")
                            .then(literal("set")
                                    .then(argument("amount", IntegerArgumentType.integer())
                                            .executes(ctx -> {
                                                shardGoal = IntegerArgumentType.getInteger(ctx, "amount");
                                                startShardCount = shardCount;
                                                startTime = Instant.now();
                                                ctx.getSource().sendFeedback(Component.literal("Goal set."));
                                                return Command.SINGLE_SUCCESS;
                                            })
                                    )
                            )
                            .then(literal("clear")
                                    .executes(ctx -> {
                                        shardGoal = -1;
                                        startShardCount = 0;
                                        startTime = null;
                                        ctx.getSource().sendFeedback(Component.literal("Goal removed"));
                                        return Command.SINGLE_SUCCESS;
                                    })
                            )
            );
        });
    }

    @Override
    public Component getDisplayText() {
        if (shardGoal < 1) return null;

        MutableComponent countPart = Component.literal(shardCount + "/" + shardGoal).withColor(0x85C7D4);
        Component timePart = Component.empty();

        if (startTime != null && shardCount > startShardCount) {
            double progress = shardCount - startShardCount;
            double rate = progress / (Instant.now().getEpochSecond() - startTime.getEpochSecond());
            if (rate > 0) {
                long remaining = (long) ((shardGoal - shardCount) / rate);
                timePart = Component.literal(" [" + formatTime(remaining) + "]").withColor(CommonColors.WHITE);
            }
        }

        return countPart.append(timePart);
    }

    private static String formatTime(long seconds) {
        long h = seconds / 3600;
        long m = (seconds % 3600) / 60;
        long s = seconds % 60;
        if (h > 0) return h + "h " + m + "m";
        if (m > 0) return m + "m " + s + "s";
        return s + "s";
    }
}

