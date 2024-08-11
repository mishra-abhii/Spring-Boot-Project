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

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScrapingService {

    @Autowired
    private ForexDataRepository forexDataRepository;

    public void scrapeData(String quote, long fromDate, long toDate) {
        String encodedQuote = quote.substring(0,6) + "%3DX";
        String url = String.format("https://finance.yahoo.com/quote/%s/history/?period1=%d&period2=%d",
                encodedQuote, fromDate, toDate);
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
                        Optional<ForexData> existingData = forexDataRepository.findByDateAndFromCurrencyAndToCurrency(
                                date, fromCurrency, toCurrency);

                        if(existingData.isPresent()){
                            updateAndSaveForexData(existingData, cols);
                            continue;
                        }else{
                            ForexData data = new ForexData();
                            data.setFromCurrency(fromCurrency);
                            data.setToCurrency(toCurrency);
//                            data.setDate(cols.get(0).text());
                            data.setDate(date);
                            data.setOpen(cols.get(1).text());
                            data.setHigh(cols.get(2).text());
                            data.setLow(cols.get(3).text());
                            data.setClose(cols.get(4).text());
                            data.setAdjClose(cols.get(5).text());
                            data.setVolume(cols.get(6).text());
                            dataList.add(data);
                        }
                    }
                }

            }
            if(!dataList.isEmpty()) forexDataRepository.saveAll(dataList);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return;
    }

    public void updateAndSaveForexData(Optional<ForexData> existingData, Elements cols) {
        if(existingData.isPresent()) {
            ForexData existing = existingData.get();
            // Update existing record with new values
            existing.setOpen(cols.get(1).text());
            existing.setHigh(cols.get(2).text());
            existing.setLow(cols.get(3).text());
            existing.setClose(cols.get(4).text());
            existing.setAdjClose(cols.get(5).text());
            existing.setVolume(cols.get(6).text());

            forexDataRepository.save(existing);
        }
    }
}

