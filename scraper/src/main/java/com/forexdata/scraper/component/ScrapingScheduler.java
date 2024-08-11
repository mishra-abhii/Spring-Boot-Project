package com.forexdata.scraper.component;

import com.forexdata.scraper.service.ScrapingService;
import com.forexdata.scraper.utility.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScrapingScheduler {

    @Autowired
    private ScrapingService scrapingService;

    @Scheduled(cron = "0 0 0 * * *") // Runs every day
    public void scrapeForexData() {
        List<String> periods = List.of("1W", "1M", "3M", "6M", "1Y");
        List<String> currencyPairs = List.of("GBPINR=X", "AEDINR=X");

        for (String pair : currencyPairs) {
            for (String period : periods) {
                long[] timestamps = DateUtils.getTimestampsForPeriod(period);
                scrapingService.scrapeData(pair, timestamps[0], timestamps[1]);
            }
        }
    }
}

