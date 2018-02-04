package com.mimecast.exercise.users;

import static java.lang.String.format;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;

import java.time.LocalDateTime;

public final class CompareLocalDateTime {
    private CompareLocalDateTime() { }

    public static String describeTimeBetween(LocalDateTime former, LocalDateTime latter) {
        if(former.isAfter(latter)) {
            throw new IllegalArgumentException("former was after latter");
        }

        long days = DAYS.between(former, latter);
        long hours = HOURS.between(former, latter);
        long minutes = MINUTES.between(former, latter);
        long seconds = SECONDS.between(former, latter);

        if(days > 0) {
            return describe(days, "day");
        }
        else if (hours > 0) {
            return describe(hours, "hour");
        }
        else if (minutes > 0) {
            return describe(minutes, "minute");
        }
        else {
            return describe(seconds, "second");
        }
    }

    private static String describe(long amount, String unit) {
        return format("%d %s%s ago",
                amount,
                unit,
                amount == 1 ? "" : "s");
    }
}
