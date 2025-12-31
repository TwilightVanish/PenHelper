package lisa.penhelper.overlay;

import lisa.penhelper.statistic.Statistic;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;

import java.text.DecimalFormat;

public class StatisticDisplay implements HudItem {
    private static final DecimalFormat formatter = new DecimalFormat("#,##0.00");
    private final Statistic statistic;
    private double cachedValue = 0;
    private long lastUpdateMillis = 0;

    public StatisticDisplay(Statistic statistic) {
        this.statistic = statistic;
    }

    @Override
    public Component getDisplayText() {
        var rawValue = getCachedValue();
        var value = formatter.format(rawValue);

        var unit = Component
                .literal(statistic.getUnit() + ": ")
                .withColor(statistic.getColor());

        var amount = Component
                .literal(value)
                .withColor(CommonColors.WHITE);

        return unit.append(amount);
    }

    private double getCachedValue() {
        long now = System.currentTimeMillis();
        if (now - lastUpdateMillis >= 3000) {
            cachedValue = statistic.getValue();
            lastUpdateMillis = now;
        }
        return cachedValue;
    }

    public Statistic getStatistic() {
        return statistic;
    }
}
