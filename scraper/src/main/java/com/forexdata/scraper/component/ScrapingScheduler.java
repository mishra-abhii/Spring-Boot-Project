package com.forexdata.scraper.component;

import com.forexdata.scraper.service.ScrapingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
public class ScrapingScheduler {

    private final ScrapingService scrapingService;

    public ScrapingScheduler(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    @Scheduled(cron = "0 0 * * * *") // Runs every hour
    public void scrapeForexData() {
        List<String> periods = List.of("1W", "1M", "3M", "6M", "1Y");
        List<String> currencyPairs = List.of("GBPINR=X", "AEDINR=X");

        for (String pair : currencyPairs) {
            for (String period : periods) {
                long[] timestamps = getTimestampsForPeriod(period);
                scrapingService.scrapeData(pair, timestamps[0], timestamps[1]);
            }
        }
    }

    private long[] getTimestampsForPeriod(String period) {
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

