package lisa.penhelper.statistic;

import java.time.Instant;

public class StatisticEntry {
    Instant time;
    double amount;

    StatisticEntry(Instant time, double amount) {
        this.time = time;
        this.amount = amount;
    }
}
