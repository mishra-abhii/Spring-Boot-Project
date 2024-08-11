package com.forexdata.scraper.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

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

    public static String getDateFromUnixTimestamp(long unixTimestamp){
        // convert unix timestamp in Date.
        Instant instant = Instant.ofEpochSecond(unixTimestamp);
        ZonedDateTime dateTime = instant.atZone(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = dateTime.format(formatter);

        return formattedDate;
    }

    public static String convertDateFormat(String dateStr) throws ParseException {
        // change the format of the date to standard DB format.
        SimpleDateFormat inputFormatter = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH); // Note: single 'd' for day
        SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = inputFormatter.parse(dateStr);
        return outputFormatter.format(date);
    }
}
