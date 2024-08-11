package com.forexdata.scraper.utility;

import java.time.LocalDate;
import java.time.ZoneId;

public class DateUtils {

    // This method returns the start and end timestamps for a given period.
    // Period can be {1W, 1M, 3M, 6M, 1Y} where W=week, M=month, Y=year
    public static long[] getTimestampsForPeriod(String period) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusWeeks(1); // Default to 1 week

        switch (period) {
            case "1W":
                startDate = endDate.minusWeeks(1);
                break;
            case "1M":
                startDate = endDate.minusMonths(1);
                break;
            case "3M":
                startDate = endDate.minusMonths(3);
                break;
            case "6M":
                startDate = endDate.minusMonths(6);
                break;
            case "1Y":
                startDate = endDate.minusYears(1);
                break;
        }

        return new long[]{
                startDate.atStartOfDay(ZoneId.systemDefault()).toInstant().getEpochSecond(),
                endDate.atStartOfDay(ZoneId.systemDefault()).toInstant().getEpochSecond()
        };
    }
}
