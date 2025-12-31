package lisa.penhelper.statistic;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public abstract class Statistic {
    protected final String unit;
    protected final int color;
    protected final List<StatisticEntry> entries = new ArrayList<>();

    protected Statistic(String unit, int color) {
        this.unit = unit;
        this.color = color;
    }

    protected void record(double amount) {
        entries.add(new StatisticEntry(Instant.now(), amount));
    }

    protected void prune() {
        Instant cutoff = Instant.now().minusSeconds(7200);
        entries.removeIf(e -> e.time.isBefore(cutoff));
    }

    public final double getValue() {
        prune();
        if (entries.size() < 2) return 0.0;

        long first = entries.getFirst().time.toEpochMilli();
        long last = entries.getLast().time.toEpochMilli();

        double sum = 0.0;
        for (int i = 1; i < entries.size(); i++) {
            sum += entries.get(i).amount;
        }

        double hours = Math.max((last - first) / 3_600_000.0, 1.0 / 3600.0);
        return sum / hours;
    }

    public final String getUnit() { return unit; }
    public final int getColor() { return color; }
    public final void reset() {
        entries.clear();
        record(0);
    }
}
