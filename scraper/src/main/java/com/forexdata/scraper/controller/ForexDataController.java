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

    @GetMapping("/forex-data")
    public List<ForexData> getForexData(@RequestParam String from, @RequestParam String to, @RequestParam String period) {
        // Convert period to timestamps
        long[] timestamps = DateUtils.getTimestampsForPeriod(period);
        List<ForexData> data = scrapingService.scrapeData(from + to + "=X", timestamps[0], timestamps[1]);
        forexDataRepository.saveAll(data);
        return forexDataRepository.findAll();
    }

    @GetMapping("/scrape-data")
    public List<ForexData> scrapeForexData(@RequestParam String from, @RequestParam String to, @RequestParam String period) {
        long[] timestamps = DateUtils.getTimestampsForPeriod(period);
        return scrapingService.scrapeData(from + to + "=X", timestamps[0], timestamps[1]);
    }

    @GetMapping("/fetchAllDataFromDB")
    public List<ForexData> getAllForexData() {
        if(forexDataRepository.count() > 0) return forexDataRepository.findAll();
        return new ArrayList<ForexData>();
    }
}

