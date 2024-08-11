package com.forexdata.scraper.controller;

import com.forexdata.scraper.entity.ForexData;
import com.forexdata.scraper.repository.ForexDataRepository;
import com.forexdata.scraper.service.ScrapingService;
import com.forexdata.scraper.utility.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ForexDataController {

    @Autowired
    private ScrapingService scrapingService;
    @Autowired
    private ForexDataRepository forexDataRepository;

    @PostMapping("/forex-data")
    public List<ForexData> getForexData(@RequestParam String from, @RequestParam String to, @RequestParam String period) {
        // Convert period to Unix timestamps
        long[] timestamps = DateUtils.getTimestampsForPeriod(period);
        // Scrape data for given period.
        scrapingService.scrapeData(from + to + "=X", timestamps[0], timestamps[1]);

        // Get startDate and endDate using timestamp to query in DB
        String startDate = DateUtils.getDateFromUnixTimestamp(timestamps[0]);
        String endDate = DateUtils.getDateFromUnixTimestamp(timestamps[1]);
        // Fetch scraped data from DB and store in list to return.
        List<ForexData> queryData = forexDataRepository.
                findByDateBetweenAndFromCurrencyAndToCurrency(startDate, endDate, from, to);
        return queryData;
    }

    @GetMapping("/fetch-all-data")
    public List<ForexData> getAllForexData() {
        if(forexDataRepository.count() > 0) return forexDataRepository.findAll();
        return new ArrayList<ForexData>();
    }
}

