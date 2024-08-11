package com.forexdata.scraper.service;

import com.forexdata.scraper.entity.ForexData;
import com.forexdata.scraper.repository.ForexDataRepository;
import com.forexdata.scraper.utility.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapingService {

    @Autowired
    private ForexDataRepository forexDataRepository;

    public void scrapeData(String quote, long fromDate, long toDate) {
        String encodedQuote = quote.substring(0,6) + "%3DX";
        String url = String.format("https://finance.yahoo.com/quote/%s/history/?period1=%d&period2=%d", encodedQuote, fromDate, toDate);
        List<ForexData> dataList = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
            Element table = doc.selectFirst("table.yf-ewueuo");

            if (table != null) {
                Elements rows = table.select("tr");
                String fromCurrency = quote.substring(0,3);
                String toCurrency = quote.substring(3,6);

                for (Element row : rows.subList(1, rows.size())) { // Skip header row
                    Elements cols = row.select("td");
                    if (cols.size() == 7) {
                        String date = DateUtils.convertDateFormat(cols.get(0).text());
                        // Check if the record already exist in DB or not
                        List<ForexData> existingData = forexDataRepository.findByDateAndFromCurrencyAndToCurrency(date, fromCurrency, toCurrency);

                        if(!existingData.isEmpty()){
                            // To update the data if multiple records with same date is present
                            for (ForexData data : existingData) updateAndSaveForexData(data, cols);
                            continue;
                        }else{
                            ForexData newData = createNewForexData(fromCurrency, toCurrency, date, cols);
                            dataList.add(newData);
                        }
                    }
                }

            }
            if(!dataList.isEmpty()) forexDataRepository.saveAll(dataList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public ForexData createNewForexData(String fromCurrency, String toCurrency, String date, Elements cols){
        // Create new Forex Record and return
        ForexData newData = new ForexData();
        newData.setFromCurrency(fromCurrency);
        newData.setToCurrency(toCurrency);
        newData.setDate(date);
        newData.setOpen(cols.get(1).text());
        newData.setHigh(cols.get(2).text());
        newData.setLow(cols.get(3).text());
        newData.setClose(cols.get(4).text());
        newData.setAdjClose(cols.get(5).text());
        newData.setVolume(cols.get(6).text());
        return newData;
    }

    public void updateAndSaveForexData(ForexData existingData, Elements cols) {
        // Update existing record with new values
        existingData.setOpen(cols.get(1).text());
        existingData.setHigh(cols.get(2).text());
        existingData.setLow(cols.get(3).text());
        existingData.setClose(cols.get(4).text());
        existingData.setAdjClose(cols.get(5).text());
        existingData.setVolume(cols.get(6).text());
        forexDataRepository.save(existingData);
    }
}

