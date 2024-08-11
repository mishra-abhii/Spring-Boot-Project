package com.forexdata.scraper.controller;

import com.forexdata.scraper.entity.ForexData;
import com.forexdata.scraper.repository.ForexDataRepository;
import com.forexdata.scraper.service.ScrapingService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ForexDataController {

    private final ScrapingService scrapingService;
    private final ForexDataRepository forexDataRepository;

    public ForexDataController(ScrapingService scrapingService, ForexDataRepository forexDataRepository) {
        this.scrapingService = scrapingService;
        this.forexDataRepository = forexDataRepository;
    }

    @GetMapping("/forex-data")
    public List<ForexData> getForexData(@RequestParam String from, @RequestParam String to, @RequestParam String period) {
        // Convert period to timestamps
        long[] timestamps = getTimestampsForPeriod(period);
        List<ForexData> data = scrapingService.scrapeData(from + to + "=X", timestamps[0], timestamps[1]);
        forexDataRepository.saveAll(data);
        return forexDataRepository.findAll();
    }

    @GetMapping("/all")
    public List<ForexData> getAllForexData() {
        return forexDataRepository.findAll();
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

